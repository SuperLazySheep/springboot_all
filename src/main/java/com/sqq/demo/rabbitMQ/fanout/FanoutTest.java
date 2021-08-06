package com.sqq.demo.rabbitMQ.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/8
 * 1.单个发布确认模式
 * 2.批量发布确认模式
 * 3。异步发布确认模式
 */

public class FanoutTest {

    public static final Integer MESSAGE_COUNT = 100;

    public static void main(String[] args) throws Exception {

//        FanoutTest.publishMessageIndividually();
        FanoutTest.publishMessageAsync();
    }

    /**
     * 单个确认
     */
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++){
            String message = i + "";
            channel.basicPublish("",queueName,null, message.getBytes());
            //发布确认
            boolean wait = channel.waitForConfirms();
            if(wait){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("全部完成   耗时" + (end - begin) + "ms");
    }


    /**
     * 异步确认
     */
    public static void publishMessageAsync() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        ConcurrentSkipListMap<Long, String> confirmMap = new ConcurrentSkipListMap<>();

        //消息成功回调函数
        ConfirmCallback ackCallback = ((deliveryTag, multiple) ->{
            if(multiple){
                ConcurrentNavigableMap<Long, String> confirmed = confirmMap.headMap(deliveryTag);
                confirmed.clear();
            }else{
                confirmMap.remove(deliveryTag);
            }
            System.out.println("消息发送成功！" + deliveryTag);
        });
        //消息失败回调函数
        ConfirmCallback nackCallback = ((deliveryTag, multiple) ->{
            System.out.println("消息发送失败！");
        });

        //准备消息的监听器，监听消息的失败和成功
        channel.addConfirmListener(ackCallback, nackCallback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++){
            String message = i + "";
            channel.basicPublish("",queueName,null, message.getBytes());
            confirmMap.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("全部完成   耗时" + (end - begin) + "ms");
    }
}
