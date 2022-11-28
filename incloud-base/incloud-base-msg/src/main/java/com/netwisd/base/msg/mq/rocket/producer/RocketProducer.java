package com.netwisd.base.msg.mq.rocket.producer;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.msg.mq.ProducerConfig;
import com.netwisd.base.msg.mq.ProducerFactory;
import com.netwisd.base.msg.mq.ProducerPool;
import com.netwisd.common.core.exception.IncloudException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

@Data
@Slf4j
@NoArgsConstructor
public class RocketProducer extends ProducerFactory {

    private ProducerConfig producerConfig;

    @Override
    public void send(MessageProxy messageProxy) throws Exception {
        if (messageProxy.getMessage() == null) {
            throw new IncloudException("消息不能为空");
        }
        DefaultMQProducer defaultMQProducer = (DefaultMQProducer) ProducerPool.INSTANCE.get(getKey());
        if (defaultMQProducer == null) {
            throw new IncloudException("未获取到对应的生产者");
        }
        if (this.producerConfig.isOrderlyMessage()) {
            MessageQueueSelector selector = messageProxy.getMessageQueueSelector();
            if (selector == null) {
                throw new IncloudException("顺序消息必须配置MessageQueueSelector");
            }
            defaultMQProducer.send(messageProxy.getMessage(), selector, messageProxy.getSelectorArg(), new DefaultSendCallback());
        } else {
            defaultMQProducer.send(messageProxy.getMessage(), new DefaultSendCallback());
        }
    }

    @Override
    public void start() throws Exception {
        String key = getKey();
        if (ProducerPool.INSTANCE.get(key) == null) {
            DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
            defaultMQProducer.setProducerGroup(this.producerConfig.getProducerGroup());
            defaultMQProducer.setSendMsgTimeout(this.producerConfig.getSendMsgTimeout());
            String namesrvAddr = StrUtil.format("{}:{}", producerConfig.getHost(), producerConfig.getPort());
            defaultMQProducer.setNamesrvAddr(namesrvAddr);
            defaultMQProducer.start();
            ProducerPool.INSTANCE.put(key, defaultMQProducer);
        }
    }

    @Override
    public void shutdown() {
        String key = getKey();
        DefaultMQProducer defaultMQProducer = (DefaultMQProducer) ProducerPool.INSTANCE.get(key);
        if (defaultMQProducer == null) {
            return;
        }
        defaultMQProducer.shutdown();
        ProducerPool.INSTANCE.remove(key);
    }

    @Override
    public String getKey() {
        return StrUtil.format("{}:{}@{}", producerConfig.getHost(), producerConfig.getPort(), producerConfig.getProducerGroup());
    }

    public RocketProducer(ProducerConfig producerConfig) {
        this.producerConfig = producerConfig;
    }

    class DefaultSendCallback implements SendCallback {
        @Override
        public void onSuccess(SendResult sendResult) {
            log.info("消息发送成功:{}", sendResult);
        }

        @Override
        public void onException(Throwable e) {
            log.error("消息发送失败:{}", e);
        }
    }


}
