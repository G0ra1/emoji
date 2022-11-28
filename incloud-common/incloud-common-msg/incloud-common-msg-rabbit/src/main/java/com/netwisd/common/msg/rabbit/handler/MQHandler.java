package com.netwisd.common.msg.rabbit.handler;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @Description: 定义接口的目的是为了让部分公共方法可以返回MQHandler接口，进而达到链式方法调用的目的；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/7 12:45 下午
 */
public interface MQHandler {
    Queue createQueue(String queueName);
    Queue getQueue(String queueName);
    void deleteQueue(String queueName);
    void clearQueue(String queueName);
    void deleteExchange(String exchangeName);
    Exchange createExchange(String exchangeName, String exchangeTypes);
    MQHandler binding(Exchange exchange, Queue queue, String routingKey);
    MQHandler binding(String exchangeName, String exchangeTypes);
    MQHandler binding(String exchangeName, String queueName, String routingKey, String exchangeTypes);
    Binding getBinding(Exchange exchange, Queue queue, String routingKey);
    void deleteBinding(String exchangeName, String queueName, String routingKey, String exchangeTypes);
    void send(Object message, Map<String, Object> headers, String exchangeName, String routingKey, String msgId);
    void send(Message message, String exchangeName, String routingKey, String msgId);
    org.springframework.amqp.core.Message receive(String queueName);
    Object receiveAndConvert(String queueName);
}
