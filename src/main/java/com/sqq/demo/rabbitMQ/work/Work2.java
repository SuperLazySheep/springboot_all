package com.sqq.demo.rabbitMQ.work;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/25
 */
public class Work2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            String receiveMessage = new String(message.getBody());
            System.out.println("c2 接受的消息 " + receiveMessage);
        };
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        };
        channel.basicConsume("workqueue", true, deliverCallback, cancelCallback);
    }
}
