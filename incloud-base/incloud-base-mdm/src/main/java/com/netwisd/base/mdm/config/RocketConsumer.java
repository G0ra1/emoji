package com.netwisd.base.mdm.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RocketConsumer implements ApplicationRunner {
    @Value("${spring.rocketmq.namesrvAddr}")
    private String namesrvAddr;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("进入consumer 测试！");
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("OrgUserIncrementConsumer-xxxx");

        // 设置NameServer的地址
        consumer.setNamesrvAddr(namesrvAddr);
        //如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("XHL", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt messageExt : msgs){
                    byte[] body = messageExt.getBody();
                    String stationListStr = new String(body);
                    log.info("stationListStr received:" + stationListStr);
                }
                //log.info("{} Receive New Messages: -> {}", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        log.info("Consumer Started.%n");
    }
}
