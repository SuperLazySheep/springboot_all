package com.sqq.controller.rabbitMQ.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/6/7
 */
public class RabbitMQTest {

    /**
     * 创建工厂
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public Connection getChannel() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("sqq");
        factory.setPassword("sqq");
        factory.setVirtualHost("/api");

        //创建通道
        Connection connection = factory.newConnection();

        return connection;
    }

    /**
     * 关闭连接
     */
    public void close(Connection connection, Channel channel){
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直连模式  生产者
     * 一个生产者 P  发送消息的程序
     * 一个消费者 C  接受消息，等待消息的到来
     *
     *
     */
    public void producer() throws IOException, TimeoutException {
        Connection connection = getChannel();
        Channel channel = connection.createChannel();

        //参数1：队列的内容   参数2：是否持久化   参数3：是否独占队列  参数3：是否自动删除  参数4：其他属性
        channel.queueDeclare("queue", true, false, false, null);
        channel.basicPublish("", "queue",null, "hello RabbitMQ".getBytes());
        close(connection, channel);
    }

    /**
     * 直连模式  消费者
     */
    public void consumer() throws IOException, TimeoutException {
        Connection connection = getChannel();
        Channel channel = connection.createChannel();

        //创建通道
        channel.queueDeclare("queue", true, false, false, null);
        channel.basicConsume("queue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者：" + new String(body));
            }
        });
    }

//////////////////////////////////TODO 2

    /**
     *第二种模式
     * Work queues，也被称为（Task queues），任务模型。
     * 当消息处理比较耗时的时候，可能生产消息的速度会远远大于消息的消费速度。长此以往，消息就会堆积越来越多，无法及时处理。此时就可以使用work 模型：让多个消费者绑定到一个队列，共同消费队列中的消息。队列中的消息一旦消费，就会消失，因此任务是不会被重复执行的。
     *                             ----> c1
      *  p  ------->  queue ------>
     *                             ----> c2
     *
     *
     * 总结:默认情况下，RabbitMQ将按顺序将每个消息发送给下一个使用者。平均而言，每个消费者都会收到相同数量的消息。这种分发消息的方式称为循环。
     */
    public void  workProducer() throws IOException, TimeoutException {
        Connection connection = getChannel();
        Channel channel = connection.createChannel();

        //创建队列任务
        channel.queueDeclare("work queue", true, false, false, null);
        for (int i= 0; i < 10; i++){
            channel.basicPublish("", "work queue", null, ("==============>： 我是消息" + i).getBytes());
        }
        close(connection, channel);
    }

    /**
     * work Queue consumer one
     */
    public void workConsumer1() throws IOException, TimeoutException {
        Connection connection = getChannel();
        Channel channel = connection.createChannel();

        //创建任务队列
        channel.queueDeclare("work queue", true, false, false, null);
        channel.basicConsume("work queue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者 1 ：" + new String(body));
            }
        });
    }

    /**
     * work Queue consumer two
     */
    public void workConsumer2() throws IOException, TimeoutException {
        Connection connection = getChannel();
        Channel channel = connection.createChannel();

        //创建任务队列
        channel.queueDeclare("work queue", true, false, false, null);
        channel.basicConsume("work queue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者 2 ：" + new String(body));
            }
        });
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQTest rabbitMQTest = new RabbitMQTest();
        rabbitMQTest.workProducer();
        rabbitMQTest.workConsumer1();

    }
}
