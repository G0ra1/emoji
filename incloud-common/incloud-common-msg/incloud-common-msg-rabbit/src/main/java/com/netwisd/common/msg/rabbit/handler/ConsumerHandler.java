package com.netwisd.common.msg.rabbit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description: 此类的作用是，用于业务系统手动去获取消息队列中的消息
 * 例如：某个生产者做完一个逻辑后，往队列发一条消息，而相应的消费者不需要通过监听的方式去获取消息而改变逻辑；
 * 而是消息端（或者相应业务端）在业务中处理完某一个业务场景的逻辑后，需要去队列中查看下消息，然后根据消息内容做进一步判断，这时就用到这个；
 * 或者可以自己做任务监听；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/3 9:39 下午
 */
@Slf4j
public class ConsumerHandler extends AbstractMsgHandler {

    public Message receive(String queueName){
        Message message =  rabbitTemplate.receive(queueName);
        return message;
    }

    public Object receiveAndConvert(String queueName){
        Object message =  rabbitTemplate.receiveAndConvert(queueName);
        return message;
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected RabbitAdmin rabbitAdmin() {
        return rabbitAdmin;
    }

    @Override
    protected RabbitTemplate rabbitTemplate() {
        return rabbitTemplate;
    }
}
