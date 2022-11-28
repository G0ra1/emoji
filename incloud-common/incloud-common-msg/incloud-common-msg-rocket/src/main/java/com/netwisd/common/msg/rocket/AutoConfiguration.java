package com.netwisd.common.msg.rocket;

import cn.hutool.core.lang.UUID;
import com.netwisd.common.msg.rocket.consumer.BinLogConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AutoConfiguration {

    @Bean
    public DefaultMQProducer binLogProducer(Environment environment) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup(environment.getProperty("spring.application.name") + "BinLogProducer");
        producer.setNamesrvAddr(environment.getProperty("canal.rocketmq.namesrvAddr"));
        producer.setSendMsgTimeout(50000);
        producer.setInstanceName(UUID.randomUUID().toString());
        // 启动生产者
        producer.start();
        return producer;
    }

    @Bean
    public BinLogConsumer canalIncloudBinLog(Environment environment) {
        BinLogConsumer canalIncloudBinLog = new BinLogConsumer();
        canalIncloudBinLog.setAppName(environment.getProperty("spring.application.name"));
        canalIncloudBinLog.setNamesrvAddr(environment.getProperty("canal.rocketmq.namesrvAddr"));
        canalIncloudBinLog.setTopic(environment.getProperty("canal.rocketmq.topic"));
        return canalIncloudBinLog;
    }

}
