package com.netwisd.base.msg.mq.rocket.consumer;

import com.netwisd.base.msg.mq.MqConfig;
import lombok.Data;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;


@Data
public class ConsumerConfig extends MqConfig {

    private String topic;

    private String consumerGroup;

    //BROADCASTING广播模式
    // CLUSTERING集群消费模式（实例平均分摊消费消息）
    private MessageModel messageModel = MessageModel.CLUSTERING;

    private int consumeThreadMin = 20;

    private int consumeThreadMax = 20;
}
