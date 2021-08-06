package com.sqq.demo.rabbitMQ.exchange_dead;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/7/30
 * 死信模式 --- 生产者
 */
public class Producer {

    private static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();
        System.out.println("生产者发送消息");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().expiration("10000").build();
        for (int i = 1; i <= 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());
        }
    }
}
