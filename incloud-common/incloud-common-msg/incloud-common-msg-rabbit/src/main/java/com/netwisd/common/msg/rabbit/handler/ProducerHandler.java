package com.netwisd.common.msg.rabbit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.UUID;

/**
 * @Description: mq的生产端的处理类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/3 9:31 下午
 */
@Slf4j
public class ProducerHandler extends AbstractMsgHandler {

    /**
     * 此处获取的这两个bean是config中已经设置完相应属性的bean，不是容器自动注入过来的bean
     */
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void send(Object message, Map<String, Object> headers, String exchangeName, String routingKey, String msgId){
        MessageHeaders properties = new MessageHeaders(headers);
        Message msg = MessageBuilder.createMessage(message, properties);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, correlationData);
        //log.info("--------消息已发送-------"+msgId);
    }

    @Async
    public void send(Object message , String exchangeName, String routingKey, String msgId){
        send(message,null,exchangeName,routingKey,msgId);
    }

    @Async
    public void send(Object message , String exchangeName, String routingKey){
        send(message,null,exchangeName,routingKey,"incloud:"+ UUID.randomUUID().toString());
    }

    @Async
    public void send(String routingKey,Object message){
        rabbitTemplate.convertAndSend(routingKey,message);
    }

    @Async
    public void send(Message message, String exchangeName, String routingKey, String msgId){
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, correlationData);
        //log.info("--------消息已发送-------"+msgId);
    }

    @Override
    protected RabbitAdmin rabbitAdmin() {
        return rabbitAdmin;
    }

    @Override
    protected RabbitTemplate rabbitTemplate() {
        return rabbitTemplate;
    }
}
