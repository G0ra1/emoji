package com.netwisd.base.log.consumer;

import com.netwisd.base.common.constants.LogEnum;
import com.netwisd.base.log.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemLogConsumer implements ApplicationRunner {

    @Value("${spring.rocketmq.namesrvAddr}")
    private String rocketNamesrvAddr;

    @Autowired
    private SystemLogService systemLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(LogEnum.SYSTEM_LOG_CONSUMBER_GROUP.getCode());
        // 设置NameServer的地址
        defaultMQPushConsumer.setNamesrvAddr(rocketNamesrvAddr);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        defaultMQPushConsumer.subscribe(LogEnum.SYSTEM_LOG_TOPIC.getCode(), LogEnum.SYSTEM_LOG_Tag.getCode());
        // 注册回调实现类来处理从broker拉取回来的消息
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            try {
                for (MessageExt messageExt : msgs) {
                    byte[] body = messageExt.getBody();
                    String json = new String(body);
                    //log.info("SystemLogConsumer received！{}", json);
                    systemLogService.insertSystemLog(json);
                }
            } catch (Exception e) {
                log.error("SystemLogConsumer接收失败{}", e.getMessage());
            } finally {
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        defaultMQPushConsumer.start();
    }
}
