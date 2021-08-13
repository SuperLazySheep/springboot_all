package com.sqq.controller.rabbitmq;

import com.sqq.config.rabbitmq.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author sqq
 * @Date 2021/8/9
 */
@Component
@Slf4j
public class WarnConsumer {

    @RabbitListener(queues = ConfirmConfig.BACKUP_WARN_QUEUE)
    public void receiveWarn(Message message){
        String s = new String(message.getBody());
        log.info("备份交换机 警告队列 发现不可路由的消息 {}", s);
    }
}
