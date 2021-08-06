package com.sqq.demo.rabbitMQ.exchange;

import com.rabbitmq.client.Channel;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/7/29
 */
public class FanoutProducer {

    private static final String EXCHANGE_NAME = "exchange1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();
        //创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s = sc.nextLine();
            channel.basicPublish(EXCHANGE_NAME, "", null, s.getBytes("UTF-8"));
            System.out.println("生产者的消息-----" + s);
        }

    }
}
