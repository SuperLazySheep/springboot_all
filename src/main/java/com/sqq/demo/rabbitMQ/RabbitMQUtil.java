package com.sqq.demo.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/25
 */
public class RabbitMQUtil {
    /**
     * 创建工厂
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("sqq");
        factory.setPassword("sqq");
        factory.setVirtualHost("/");

        //创建通道
        Connection connection = factory.newConnection();

        return connection.createChannel();
    }
}
