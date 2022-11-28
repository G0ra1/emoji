package com.netwisd.common.task;

import com.netwisd.base.common.constants.TaskEnum;
import com.netwisd.common.task.consumer.JobConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TaskAutoConfiguration {

    @Bean
    public JobConsumer jobConsumer(Environment environment) {
        JobConsumer jobConsumer = new JobConsumer();
        jobConsumer.setAppName(environment.getProperty("spring.application.name"));
        jobConsumer.setNamesrvAddr(environment.getProperty("spring.rocketmq.namesrvAddr"));
        return jobConsumer;
    }

    @Bean
    public DefaultMQProducer defaultMQProducer(Environment environment) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(TaskEnum.TASK_CALLBACK_PRODUCER_GROUP.getCode());
        producer.setNamesrvAddr(environment.getProperty("spring.rocketmq.namesrvAddr"));
        producer.setSendMsgTimeout(10000);
        // 启动生产者
        producer.start();
        return producer;
    }
}
