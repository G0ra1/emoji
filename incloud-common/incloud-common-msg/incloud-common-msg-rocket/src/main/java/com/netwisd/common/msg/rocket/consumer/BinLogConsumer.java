package com.netwisd.common.msg.rocket.consumer;

import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.common.msg.rocket.handle.HandleBinLogData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Data
@Slf4j
public class BinLogConsumer implements ApplicationRunner {

    private String appName;

    private String namesrvAddr;

    private String topic;

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("开始初始化BinLogConsumer:地址:{},appName:{},topic:{}", namesrvAddr, appName, topic);
            // 实例化消费者
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(appName);
            //设置实例名称
            consumer.setInstanceName(IdGenerator.getIdGenerator().toString());
            // 设置NameServer的地址
            consumer.setNamesrvAddr(namesrvAddr);
            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe(topic, "*");
            //广播模式
            consumer.setMessageModel(MessageModel.BROADCASTING);
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                try {
                    for (MessageExt messageExt : list) {
                        //log.info("获取到消息内容:{}", new String(messageExt.getBody()));
                        new HandleBinLogData().handle(new String(messageExt.getBody()));
                    }
                } catch (Exception e) {
                    log.error("处理BinLog数据失败:{}", e);
                } finally {
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 启动消费者实例
            consumer.start();
        } catch (Exception e) {
            log.error("BinLogConsumer初始化失败:{}", e);
        }
    }
}
