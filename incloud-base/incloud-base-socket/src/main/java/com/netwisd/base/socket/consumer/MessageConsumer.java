package com.netwisd.base.socket.consumer;

import com.netwisd.base.common.constants.MessageEnum;
import com.netwisd.base.socket.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer implements ApplicationRunner {

    @Value("${spring.rocketmq.namesrvAddr}")
    private String rocketMqUrl;

    @Autowired
    private MessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MessageEnum.MESSAGE_SOCKET_CONSUMBER_GROUP.getCode());
        // 设置NameServer的地址
        consumer.setNamesrvAddr(rocketMqUrl);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(MessageEnum.MESSAGE_SOCKET_TOPIC.getCode(), "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        // 标记该消息已经被成功消费
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            try {
                for (MessageExt messageExt : msgs) {
                    byte[] body = messageExt.getBody();
                    log.info("MessageSocketConsumer received！{}", new String(body, RemotingHelper.DEFAULT_CHARSET));
                    messageService.sendUserId(new String(body, RemotingHelper.DEFAULT_CHARSET));
                }
            } catch (Exception e) {
                log.error("MessageSocketConsumer接收失败！{}", e);
            } finally {
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
    }

}
