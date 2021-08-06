package com.sqq.controller.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sqq
 * @Date 2021/8/5
 */
@Component
@Slf4j
public class DelayedConsumer {



    @RabbitListener(queues = "delayed.queue")
    public void receiveDelay(Message message){
        String s = new String(message.getBody());
        log.info("当前时间： {}  收到延迟交换机的信息：{}", new Date().toString(), s);
    }
}
