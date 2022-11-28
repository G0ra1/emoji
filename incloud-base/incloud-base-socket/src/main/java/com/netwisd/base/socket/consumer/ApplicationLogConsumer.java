package com.netwisd.base.socket.consumer;

import com.netwisd.base.common.constants.LogEnum;
import com.netwisd.base.socket.service.ApplicationLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationLogConsumer implements ApplicationRunner {

    @Value("${spring.rocketmq.namesrvAddr}")
    private String rocketMqUrl;

    @Autowired
    private ApplicationLogService applicationLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(LogEnum.APPLICATION_LOG_SOCKET_CONSUMBER_GROUP.getCode());
        // 设置NameServer的地址
        consumer.setNamesrvAddr(rocketMqUrl);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(LogEnum.APPLICATION_LOG_SOCKET_TOPIC.getCode(), "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            context.setAutoCommit(true);
            try {
                for (MessageExt messageExt : msgs) {
                    byte[] body = messageExt.getBody();
                    //log.info("ApplicationLogSocketConsumer received！{}", new String(body));
                    applicationLogService.sendLogByCond(new String(body, RemotingHelper.DEFAULT_CHARSET));
                }
            } catch (Exception e) {
                log.error("ApplicationLogSocketConsumer接收失败{}", e);
            } finally {
                // 标记该消息已经被成功消费
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
    }


}
