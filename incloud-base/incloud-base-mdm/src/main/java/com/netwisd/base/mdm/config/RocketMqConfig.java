package com.netwisd.base.mdm.config;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/11/20 4:58 下午
 */
@Data
@Configuration
public class RocketMqConfig {
    @Value("${spring.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @SneakyThrows
    @Bean
    public DefaultMQProducer defaultMQProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("OrgUserIncrementSender");
        producer.setNamesrvAddr(namesrvAddr);
        // 启动生产者
        producer.start();
        return producer;
    }
}
