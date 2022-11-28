package com.netwisd.base.msg.mq.rocket.consumer;


import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
public class MessageHandler {

    public static ConsumeStatus handleMessage(final List<MessageExt> msgs, final MessageQueue messageQueue) {
        try {
            for (MessageExt msg : msgs) {
                byte[] body = msg.getBody();
                log.debug("开始消费，msg={}", new String(body, "UTF-8"));
            }
        } catch (Exception e) {
            return handleException(e);
        }
        return ConsumeStatus.SUCCESS;
    }

    private static ConsumeStatus handleException(final Exception e) {
        Class exceptionClass = e.getClass();
        if (exceptionClass.equals(UnsupportedEncodingException.class)) {
            log.error(e.getMessage());
        } else if (exceptionClass.equals(IncloudException.class)) {
            log.error(e.getMessage());
        }
        return ConsumeStatus.RETRY;
    }

}
