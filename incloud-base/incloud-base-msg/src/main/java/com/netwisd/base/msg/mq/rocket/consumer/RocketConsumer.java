package com.netwisd.base.msg.mq.rocket.consumer;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.msg.mq.ConsumerFactory;
import com.netwisd.base.msg.mq.ConsumerPool;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

@Data
@Slf4j
@NoArgsConstructor
public class RocketConsumer extends ConsumerFactory {

    private ConsumerConfig consumerConfig;

    @Override
    public void start() throws Exception {
        String key = getKey();
        if (ConsumerPool.INSTANCE.get(key) == null) {
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
            String namesrvAddr = StrUtil.format("{}:{}", consumerConfig.getHost(), consumerConfig.getPort());
            defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
            defaultMQPushConsumer.setConsumerGroup(this.consumerConfig.getConsumerGroup());
            defaultMQPushConsumer.subscribe(this.consumerConfig.getTopic(), "*");
            defaultMQPushConsumer.setMessageModel(this.consumerConfig.getMessageModel());
            defaultMQPushConsumer.setConsumeThreadMax(this.consumerConfig.getConsumeThreadMax());
            defaultMQPushConsumer.setConsumeThreadMin(this.consumerConfig.getConsumeThreadMin());
            if (this.consumerConfig.isOrderlyMessage()) {
                defaultMQPushConsumer.registerMessageListener(new MessageOrderlyImpl());
            } else {
                defaultMQPushConsumer.registerMessageListener(new MessageConcurrentlyImpl());
            }
            defaultMQPushConsumer.start();
            ConsumerPool.INSTANCE.put(key, defaultMQPushConsumer);
        }
    }

    @Override
    public void shutdown() {
        String key = getKey();
        DefaultMQPushConsumer defaultMQPushConsumer = (DefaultMQPushConsumer) ConsumerPool.INSTANCE.get(key);
        defaultMQPushConsumer.shutdown();
        ConsumerPool.INSTANCE.remove(key);
    }

    @Override
    public String getKey() {
        return StrUtil.format("{}:{}@{}", consumerConfig.getHost(), consumerConfig.getPort(), consumerConfig.getConsumerGroup());
    }

    public RocketConsumer(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
