package com.netwisd.common.log.config;

import com.netwisd.base.common.constants.LogEnum;
import com.netwisd.common.log.aspect.SystemLogAspect;
import com.netwisd.common.log.constant.ApplicationProperty;
import com.netwisd.common.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener();
    }

    @Bean
    public SystemLogAspect sysLogAspect() {
        return new SystemLogAspect();
    }

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        ApplicationProperty instance = ApplicationProperty.getInstance();
        DefaultMQProducer producer = new DefaultMQProducer(LogEnum.SYSTEM_LOG_PRODUCER_GROUP.getCode());
        producer.setNamesrvAddr(instance.getRocketMqUrl());
        producer.setSendMsgTimeout(5000);
        // 启动生产者
        producer.start();
        return producer;
    }

}
