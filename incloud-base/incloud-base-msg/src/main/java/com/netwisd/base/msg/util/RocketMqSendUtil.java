package com.netwisd.base.msg.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netwisd.base.common.constants.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RocketMqSendUtil {

    private final DefaultMQProducer rocketMqPruducer;

    public void sendMsg(Object obj) {
        try {
            Message msg = new Message(
                    MessageEnum.MESSAGE_SOCKET_TOPIC.getCode(),
                    "*",
                    JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss SSS", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            rocketMqPruducer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("消息发送成功:{}", sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    log.error("消息发送异常:{}", e);
                }
            });
        } catch (Exception e) {
            log.error("消息推送失败:{}", e);
        }
    }

}
