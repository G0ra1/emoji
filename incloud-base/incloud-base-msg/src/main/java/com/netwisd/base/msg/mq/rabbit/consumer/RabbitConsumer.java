package com.netwisd.base.msg.mq.rabbit.consumer;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.msg.mq.ConsumerFactory;
import com.netwisd.base.msg.mq.ConsumerPool;
import com.netwisd.base.msg.mq.rocket.consumer.ConsumerConfig;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitConsumer extends ConsumerFactory {

    private ConsumerConfig consumerConfig;

    @Override
    public void start() throws Exception {
        String key = getKey();
        if (ConsumerPool.INSTANCE.get(key) == null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.consumerConfig.getHost());
            factory.setPort(this.consumerConfig.getPort());
            factory.setVirtualHost(this.consumerConfig.getVirtualHost());
            factory.setUsername(this.consumerConfig.getUsername());
            factory.setPassword(this.consumerConfig.getPassword());
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(consumerConfig.getQueueName(), false, false, false, null);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                //一旦有消息进入队列就会触发
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("receive msg :" + msg);
                }
            };
            //监听队列
            channel.basicConsume(consumerConfig.getQueueName(), true, consumer);
            ConsumerPool.INSTANCE.put(key, channel);
        }
    }

    @Override
    public void shutdown() throws Exception {
        String key = getKey();
        Channel channel = (Channel) ConsumerPool.INSTANCE.get(key);
        if (channel != null && channel.isOpen()) {
            //关闭有先后顺序
            channel.close();
            channel.getConnection().close();
        }
        ConsumerPool.INSTANCE.remove(key);
    }

    @Override
    public String getKey() {
        return StrUtil.format("{}:{}@{}", consumerConfig.getHost(), consumerConfig.getPort(), consumerConfig.getVirtualHost());
    }

    public RabbitConsumer(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
