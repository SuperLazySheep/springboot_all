package com.sqq.demo.rabbitMQ.work;

import com.rabbitmq.client.Channel;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/25
 */
public class WorkProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare("workqueue", false, false, false , null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", "workqueue", null, message.getBytes());
            System.out.println("生产者发出消息 " + message );
        }
    }
}
