package com.sqq.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author sqq
 * @Date 2021/8/2
 *
 * 延迟队列(死信队列)  配置
 */
@Configuration
public class TtlQueueConfig {

    /**
     *声明交换机
     */
    private static final String X_EXCHANGE = "X";
    private static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    /**
     * 声明队列
     */
    private static final String QUEUE_A = "QA";
    private static final String QUEUE_B = "QB";
    private static final String QUEUE_C = "QC";
    private static final String QUEUE_D = "QD";

    /**
     * 声明routing Key
     */
    public static final String KEY_XA = "XA";
    public static final String KEY_XB = "XB";
    public static final String KEY_XC = "XC";
    public static final String KEY_YD = "YD";


    /**
     * 创建交换机
     */
    @Bean("XExchange")
    public DirectExchange exchangeX(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("YExchange")
    public DirectExchange exchangeY(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 创建队列
     */
    @Bean("queueA")
    public Queue queueQa(){
        HashMap<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        hashMap.put("x-dead-letter-routing-key", KEY_YD);
        hashMap.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(hashMap).build();
    }

    @Bean("queueB")
    public Queue queueQb(){
        HashMap<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        hashMap.put("x-dead-letter-routing-key", KEY_YD);
        hashMap.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(hashMap).build();
    }

    @Bean("queueC")
    public Queue queueQc(){
        HashMap<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        hashMap.put("x-dead-letter-routing-key", KEY_YD);
        return QueueBuilder.durable(QUEUE_C).withArguments(hashMap).build();
    }

    @Bean("queueD")
    public Queue queueQd(){
        return QueueBuilder.durable(QUEUE_D).build();
    }

    /**
     * 绑定交换机和队列
     */
    @Bean
    public Binding bindingXa(@Qualifier("queueA") Queue queue, @Qualifier("XExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(KEY_XA);
    }

    @Bean
    public Binding bindingXb(@Qualifier("queueB") Queue queue, @Qualifier("XExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(KEY_XB);
    }

    @Bean
    public Binding bindingXc(@Qualifier("queueC") Queue queue, @Qualifier("XExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(KEY_XC);
    }

    @Bean
    public Binding bindingYd(@Qualifier("queueD") Queue queue, @Qualifier("YExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(KEY_YD);
    }
}
