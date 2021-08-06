package com.sqq.demo.demo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.sun.org.glassfish.external.statistics.impl.StatisticImpl.START_TIME;

/**
 * @author sqq
 * @Date 2021/6/25
 */
public class DemoTest {

    public final static String START_TIME = "startTime";
    public final static String END_TIME = "endTime";


    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println(test());
//        System.out.println(4 >> 2);
//        System.out.println(-4 >> 2);
//        HashMap<Object, Object> hashMap = new HashMap<>();
//        HashMap<String, Object> timestamp = getMonthTimestamp();
//        Long startTime = (Long) timestamp.get(START_TIME);
//        excel();
    }

    private static int test(){
        int tmp = 1;
        try {
            System.out.println(tmp);
            return ++tmp;
        }catch (Exception e){
            e.printStackTrace();
            return ++tmp;
        }finally {
            ++tmp;
            System.out.println(tmp);
        }
    }

    public static long getTimestampByOffsetDay(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取本月时间戳区间
     * @return
     */
    public static HashMap<String, Object> getMonthTimestamp() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        hashMap.put(START_TIME, getTimestampByOffsetDay(0 - calendar.get(Calendar.DAY_OF_MONTH) + 1));
        hashMap.put(END_TIME, getTimestampByOffsetDay(calendar.getMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH)));
        return hashMap;
    }

    public static void excel(){
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);
        // 通过工具类创建writer
        File file = new File(System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = ExcelUtil.getWriter(file);
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(rows.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();

    }
}
