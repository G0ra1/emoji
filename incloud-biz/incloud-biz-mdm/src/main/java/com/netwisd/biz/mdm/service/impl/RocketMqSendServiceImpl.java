package com.netwisd.biz.mdm.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯
 * @date 2021-10-19 15:24:00
 */
@Service
@Slf4j
public class RocketMqSendServiceImpl {

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @SneakyThrows
    public void sendMq(String json,String topicName,String tagsName){
        //String json = ResourceUtil.readUtf8Str("station.json");
        // 构建消息
        Message msg = new Message(topicName,tagsName,
                json.getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 同步发送
        //SendResult sendResult = defaultMQProducer.send(msg);
        SendResult sendResult = defaultMQProducer.send(msg,new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                //Long id = Long.valueOf((String)arg);
                //long index = id % mqs.size();
                return mqs.get(0);
            }
        }, msg);
        log.info("消息发送响应：" + sendResult.toString());
        // 打印发送结果
       /* log.info("全局测点配置发送成功：{}", sendResult);
        Thread.sleep(1000);
        defaultMQProducer.shutdown();*/
    }

}
