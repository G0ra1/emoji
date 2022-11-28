package com.netwisd.base.socket.config;

import com.netwisd.base.common.constants.MessageEnum;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {

    @Value("${spring.rocketmq.namesrvAddr}")
    private String rocketMqUrl;

    @Bean
    public DefaultMQProducer rocketMqPruducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(MessageEnum.MESSAGE_SOCKET_PRODUCER_GROUP.getCode());
        producer.setNamesrvAddr(rocketMqUrl);
        // 启动生产者
        producer.start();
        return producer;
    }
}
