package com.sqq.controller.rabbitMQ.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/8
 * rabbitMQ consumer start
 */
public class StartTest {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQTest rabbitMQTest = new RabbitMQTest();
        rabbitMQTest.workConsumer2();
    }
}
