package com.sqq.demo.rabbitMQ.exchange;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/7/29
 */
public class FanoutExchangeConsumer2 {

    private static final String EXCHANGE_NAME = "exchange1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();
        //创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //创建队列
        String queue = channel.queueDeclare().getQueue();
        // "" 其实是默认的
        channel.queueBind(queue, EXCHANGE_NAME, "");
        System.out.println("交换机---发布确认模式----消费者1");
        DeliverCallback callback = (consumerTag,delivery) ->{
            String string = new String(delivery.getBody(), "utf-8");
            System.out.println(string);
        };
        channel.basicConsume(queue,true, callback, consumerTag -> {});
    }
}
