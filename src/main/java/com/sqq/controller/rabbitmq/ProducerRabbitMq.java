package com.sqq.controller.rabbitmq;

import com.sqq.config.rabbitmq.ConfirmConfig;
import com.sqq.config.rabbitmq.DelayedConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author sqq
 * @Date 2021/8/3
 *
 * 生产者
 */
@RestController
@Slf4j
@RequestMapping("rabbitmq/")
public class ProducerRabbitMq {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{message}")
    public void senMessage(@PathVariable String message){
        log.info("当前时间: {}, 发送一条消息给TTL队列  {} ", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息为TTL 10s  的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息为TTL 40s  的队列" + message);
    }


    @GetMapping("/sendExpiration/{message}/{ttlTime}")
    public void senMessage(@PathVariable String message, @PathVariable String ttlTime){
        rabbitTemplate.convertAndSend("X", "XC", message, msg ->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
        log.info("当前时间: {}, 发送ttl时间为 {} 消息给队列  {} ", new Date().toString(), ttlTime, message);
    }

    @GetMapping("/sendDelayed/{message}/{delayedTime}")
    public void sendMessage(@PathVariable String message, @PathVariable Integer delayedTime){
        rabbitTemplate.convertAndSend(DelayedConfig.DELAYED_EXCHANGE_NAME,DelayedConfig.DELAYED_ROUTING_KEY,msg -> {
            msg.getMessageProperties().setDelay(delayedTime);
            return msg;
        });
        log.info("当前时间: {}, 发送延迟时间为 {} 消息给队列  {} ", new Date().toString(), delayedTime , message);
    }

    @GetMapping("/sendConfirm/{message}")
    public void sendConfirmMessage(@PathVariable String message){
        log.info("当前时间: {}, 发送一条消息给发布确认队列  {} ", new Date().toString(), message);
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY, "消息为" + message, correlationData);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY + "123", "消息为" + message, correlationData2);
    }
}
