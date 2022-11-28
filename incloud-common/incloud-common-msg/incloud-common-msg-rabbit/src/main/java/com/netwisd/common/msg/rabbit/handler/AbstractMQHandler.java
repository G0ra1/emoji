package com.netwisd.common.msg.rabbit.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Description: 一个mq的上层处理工具抽象类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/3 9:47 下午
 */
@Slf4j
public abstract class AbstractMQHandler implements MQHandler {

    protected abstract RabbitAdmin rabbitAdmin();

    protected abstract RabbitTemplate rabbitTemplate();

    /**
     * 根据队列名创建队列或者获取队列
     * @param queueName
     * @return
     */
    @Override
    public Queue createQueue(String queueName){
        Queue queue = new Queue(queueName);
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    /**
     * 获取broker中已有的queue
     * @param queueName
     * @return
     */
    @Override
    public Queue getQueue(String queueName){
        if(StrUtil.isNotEmpty(queueName)){
            Queue queue = new Queue(queueName);
            return queue;
        }
        return null;
    }

    /**
     * 删除队列
     * @param queueName
     */
    @Override
    public void deleteQueue(String queueName){
        rabbitAdmin().deleteQueue(queueName);
    }

    /**
     * 立即清楚队列
     * @param queueName
     */
    @Override
    public void clearQueue(String queueName){
        rabbitAdmin().purgeQueue(queueName,true);
    }

    /**
     * 删除交换机
     * @param exchangeName
     */
    @Override
    public void deleteExchange(String exchangeName){
        rabbitAdmin().deleteExchange(exchangeName);
    }

    /**
     * 通过不同类型创建exchange,默认为直连
     * @param exchangeName
     * @param exchangeTypes
     * @see  ExchangeTypes
     * @return
     */
    @Override
    public Exchange createExchange(String exchangeName, String exchangeTypes){
        Exchange exchange = new DirectExchange(exchangeName);;

        if(exchangeTypes.equals(ExchangeTypes.DIRECT)){
            exchange = new DirectExchange(exchangeName);
        }else if(exchangeTypes.equals(ExchangeTypes.DIRECT)){
            exchange = new TopicExchange(exchangeName);
        }else if(exchangeTypes.equals(ExchangeTypes.DIRECT)){
            exchange = new FanoutExchange(exchangeName);
        }else if(exchangeTypes.equals(ExchangeTypes.DIRECT)){
            exchange = new HeadersExchange(exchangeName);
        }else {
            log.error("交换机类型错误！");
        }
        rabbitAdmin().declareExchange(exchange);
        return exchange;
    }

    /**
     * 创建一个绑定关系，注意是创建关系，并不意味着在rabbitmq中会一定创建新的ex,queure，根据名称对现有交换机和队列自动创建或忽略的；
     * @param exchange
     * @param queue
     * @param routingKey
     */
    @Override
    public MQHandler binding(Exchange exchange, Queue queue, String routingKey){
        Binding binding = getBinding(exchange,queue,routingKey);
        rabbitAdmin().declareBinding(binding);
        return this;
    }

    /**
     * 绑定一个交换机，其实就是创建一个exchange，就是用于消费者发送消息时声明或使用已有交换机并做个链式调用
     * @param exchangeName
     * @return
     */
    @Override
    public MQHandler binding(String exchangeName, String exchangeTypes) {
        Exchange exchange = createExchange(exchangeName,exchangeTypes);
        if(ObjectUtil.isNotNull(exchange)){
            return this;
        }
        return null;
    }

    /**
     * 创建一个绑定关系，注意是创建关系，并不意味着在rabbitmq中会一定创建新的ex,queure，根据名称对现有交换机和队列自动创建或忽略的；
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param exchangeTypes
     * @see  ExchangeTypes
     */
    @Override
    public MQHandler binding(String exchangeName, String queueName, String routingKey, String exchangeTypes){
        Queue queue = createQueue(queueName);
        Exchange exchange = createExchange(exchangeName,exchangeTypes);
        binding(exchange,queue,routingKey);
        return this;
    }

    /**
     * 获取绑定关系
     * @param exchange
     * @param queue
     * @param routingKey
     * @return
     */
    @Override
    public Binding getBinding(Exchange exchange, Queue queue, String routingKey){
        Binding binding = null;
        if(exchange instanceof DirectExchange){
            binding = BindingBuilder.bind(queue).to((DirectExchange)exchange).with(routingKey);
        }else if(exchange instanceof TopicExchange){
            binding = BindingBuilder.bind(queue).to((TopicExchange)exchange).with(routingKey);
        }else if(exchange instanceof FanoutExchange){
            binding = BindingBuilder.bind(queue).to((FanoutExchange)exchange);
        }
        return binding;
    }

    /**
     * 删除绑定关系
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param exchangeTypes
     */
    @Override
    public void deleteBinding(String exchangeName, String queueName, String routingKey, String exchangeTypes){
        Queue queue = getQueue(queueName);
        Exchange exchange = createExchange(exchangeName,exchangeTypes);
        Binding binding = getBinding(exchange,queue,routingKey);
        rabbitAdmin().removeBinding(binding);
    }

}
