package com.netwisd.base.msg.mq.rocket.producer;

import lombok.Data;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.common.message.Message;

@Data
public class MessageProxy<T> {

    private MessageQueueSelector messageQueueSelector;

    private SendCallback sendCallback;

    private Message message;

    private Object selectorArg;
}
