package com.netwisd.common.log.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netwisd.base.common.constants.LogEnum;
import com.netwisd.common.log.entity.SystemLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Order
    @Async
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SystemLog sysLog = (SystemLog) event.getSource();
        try {
            Message msg = new Message(
                    LogEnum.SYSTEM_LOG_TOPIC.getCode(),
                    LogEnum.SYSTEM_LOG_Tag.getCode(),
                    JSON.toJSONStringWithDateFormat(sysLog, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            defaultMQProducer.sendOneway(msg);
            //异步发送消息
            /*defaultMQProducer.send(msg, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    //System.out.println("MQ:Producer生产者发送消息" + sendResult);
                }

                public void onException(Throwable throwable) {
                    log.error("发送失败:{}", throwable);
                }
            });*/
        } catch (Exception e) {
            log.error("发送失败:{}", e);
        }
    }

}
