package com.sqq.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author sqq
 * @Date 2021/8/6
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机的确认回调方法
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(ack){
            log.info("交换机已经接受到  id为 {} 的消息", id);
        }else{
            log.info("交换机没有接收到 id 为 {}, 原因： {}", id, cause);
        }
    }

    /**
     *  当消息到达不了目的地，将消息返回给生产者
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息{}   被交换机{} 退回--退回原因{}  路由值为{}", returned.getMessage().getBody(), returned.getExchange(), returned.getReplyText(), returned.getRoutingKey());
    }
}
