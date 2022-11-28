package com.netwisd.base.msg.mq;


import com.netwisd.base.msg.mq.rabbit.consumer.RabbitConsumer;
import com.netwisd.base.msg.mq.rocket.consumer.ConsumerConfig;
import com.netwisd.base.msg.mq.rocket.consumer.RocketConsumer;
import com.netwisd.common.core.exception.IncloudException;

public abstract class ConsumerFactory {

    public abstract void start() throws Exception;

    public abstract void shutdown() throws Exception;

    public abstract String getKey();

    public static ConsumerFactory getConsumer(ConsumerConfig consumerConfig, String mq) {
        switch (mq) {
            case "rocket":
                return new RocketConsumer(consumerConfig);
            case "rabbit":
                return new RabbitConsumer(consumerConfig);
            default:
                throw new IncloudException("Unexpected value: " + mq);
        }
    }
}
