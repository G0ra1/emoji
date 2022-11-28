package com.netwisd.base.portal.config;

import com.netwisd.base.common.constants.MessageEnum;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {

    @Value("${spring.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQProducer rocketMqPruducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(MessageEnum.TASKS_SOCKET_PRODUCER_GROUP.getCode());
        producer.setNamesrvAddr(namesrvAddr);
        producer.setSendMsgTimeout(10000);
        // 启动生产者
        producer.start();
        return producer;
    }

}
