package com.netwisd.base.msg.mq.rabbit.producer;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.msg.mq.ConsumerPool;
import com.netwisd.base.msg.mq.ProducerConfig;
import com.netwisd.base.msg.mq.ProducerFactory;
import com.netwisd.base.msg.mq.ProducerPool;
import com.netwisd.base.msg.mq.rocket.producer.MessageProxy;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Data
@Slf4j
@NoArgsConstructor
public class RabbitProducer extends ProducerFactory {

    private ProducerConfig producerConfig;

    @Override
    public void send(MessageProxy messageProxy) throws Exception {
        Channel channel = (Channel) ProducerPool.INSTANCE.get(getKey());
        channel.basicPublish("", producerConfig.getQueueName(), null, "123".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void start() throws Exception {
        String key = getKey();
        if (ProducerPool.INSTANCE.get(key) == null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.producerConfig.getHost());
            factory.setPort(this.producerConfig.getPort());
            factory.setVirtualHost(this.producerConfig.getVirtualHost());
            factory.setUsername(this.producerConfig.getUsername());
            factory.setPassword(this.producerConfig.getPassword());
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(producerConfig.getQueueName(), false, false, false, null);
            ProducerPool.INSTANCE.put(key, channel);
        }
    }

    @Override
    public void shutdown() throws Exception {
        String key = getKey();
        Channel channel = (Channel) ProducerPool.INSTANCE.get(key);
        if (channel != null && channel.isOpen()) {
            //关闭有先后顺序
            channel.close();
            channel.getConnection().close();
        }
        ConsumerPool.INSTANCE.remove(key);
    }

    @Override
    public String getKey() {
        return StrUtil.format("{}:{}@{}", producerConfig.getHost(), producerConfig.getPort(), producerConfig.getVirtualHost());
    }

    public RabbitProducer(ProducerConfig producerConfig) {
        this.producerConfig = producerConfig;
    }
}
