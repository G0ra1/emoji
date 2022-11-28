package com.netwisd.base.msg.mq;

import com.netwisd.base.msg.mq.rabbit.producer.RabbitProducer;
import com.netwisd.base.msg.mq.rocket.producer.MessageProxy;
import com.netwisd.base.msg.mq.rocket.producer.RocketProducer;
import com.netwisd.common.core.exception.IncloudException;

public abstract class ProducerFactory {

    public abstract void send(MessageProxy messageProxy) throws Exception;

    public abstract void start() throws Exception;

    public abstract void shutdown() throws Exception;

    public abstract String getKey();

    public static ProducerFactory getProducer(ProducerConfig producerConfig, String mq) {
        switch (mq) {
            case "rocket":
                return new RocketProducer(producerConfig);
            case "rabbit":
                return new RabbitProducer(producerConfig);
            default:
                throw new IncloudException("Unexpected value: " + mq);
        }
    }

}
