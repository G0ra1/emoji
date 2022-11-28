package com.netwisd.common.msg.rabbit.handler;

import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @Description: 这类的作用就是让其他不同子类不出现不相关的方法
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/7 1:10 下午
 */
public abstract class AbstractMsgHandler extends AbstractMQHandler {
    @Override
    public void send(Object message, Map<String, Object> headers, String exchangeName, String routingKey, String msgId) {

    }

    @Override
    public void send(Message message, String exchangeName, String routingKey, String msgId) {

    }

    @Override
    public org.springframework.amqp.core.Message receive(String queueName) {
        return null;
    }

    @Override
    public Object receiveAndConvert(String queueName) {
        return null;
    }
}
