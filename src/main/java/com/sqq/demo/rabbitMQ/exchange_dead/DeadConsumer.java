package com.sqq.demo.rabbitMQ.exchange_dead;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sqq.demo.rabbitMQ.RabbitMQUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author sqq
 * @Date 2021/7/30
 * 死信队列 --- normal consumer
 * 报错：当队列已经声明 再次声明会报错。
 */
public class DeadConsumer {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String DEAD_EXCHANGE = "dead_exchange";
    private static final String NORMAL_QUEUE = "normal_queue";
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtil.getChannel();
        //声明交换机
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false,null);
        // 绑定死信队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 当普通队列中的消息 需要被绑定到死信队列中
        Map<String, Object> arguments = new HashMap<>(4);
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");
        arguments.put("x-max-length", 6);
        //声明 普通队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false,arguments);
        // 绑定队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");


        System.out.println("等待接受消息.....");
        //消息
        DeliverCallback callback = (consumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("正常队列接受消息--" + message);
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(NORMAL_QUEUE, true, callback, consumerTag -> {});

    }
}
