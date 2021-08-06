package com.sqq.test;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一共四种模式
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationMainRabbitMQTest {

    /**
     * 直连模式
     * 一个生产者 P  发送消息的程序
     * 一个消费者 C  接受消息，等待消息的到来
     *
     *
     */
    @Test
    public void one() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("sqq");
        factory.setPassword("sqq");
        factory.setVirtualHost("/api");

        //创建通道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    }

}
