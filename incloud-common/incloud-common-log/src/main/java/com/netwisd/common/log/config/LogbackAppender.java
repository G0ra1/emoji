package com.netwisd.common.log.config;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.*;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netwisd.base.common.constants.LogEnum;
import com.netwisd.common.log.constant.ApplicationProperty;
import com.netwisd.common.log.entity.ApplicationLog;
import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
public class LogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    final PatternLayout locationLayout = new PatternLayout();

    static ApplicationProperty instance = ApplicationProperty.getInstance();

    static DefaultMQProducer producer;

    Layout layout;

    Encoder encoder;

    static {
        try {
            //Boolean.valueOf. "" 转 false , 非true字符 转 false
            if (Boolean.valueOf(instance.getApplicationLogIsSendRocket())) {
                producer = new DefaultMQProducer(LogEnum.APPLICATION_LOG_PRODUCER_GROUP.getCode());
                producer.setNamesrvAddr(instance.getRocketMqUrl());
                producer.setSendMsgTimeout(5000);
                //启动生产者
                producer.start();
            } else {
                System.err.println(">>>>>>>>>>>>>>未配置rocketMQ链接地址或未开启实时发送，日志将不会实时记录>>>>>>>>>>>>>>>>");
            }
        } catch (Exception e) {
            printlnMqStartError(e);
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (Boolean.valueOf(instance.getApplicationLogIsSendRocket())) {
            //String logBody = this.layout.doLayout(event);
            String exceptionLogBody = fullDump(event);
            String logBody = StrUtil.isEmpty(exceptionLogBody) ? event.getFormattedMessage() : event.getFormattedMessage() + "\n" + exceptionLogBody;
            ApplicationLog loggerMessage = new ApplicationLog(
                    instance.getAppName(),
                    logBody,
                    getDateTimeOfTimestamp(event.getTimeStamp()),
                    event.getThreadName(),
                    event.getLoggerName(),
                    event.getLevel().levelStr);
            try {
                //推送到不同的mq里面的topic,而不用MQ的广播模式，是为了要保证消息的顺序消费，如果开启广播模式暂时用不了它的顺序消费;
                //推送到socket服务实时推送前端
                Message socketMsg = new Message(
                        LogEnum.APPLICATION_LOG_SOCKET_TOPIC.getCode(),
                        event.getLevel().levelStr,
                        JSON.toJSONStringWithDateFormat(loggerMessage, "yyyy-MM-dd HH:mm:ss SSS", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                producer.sendOneway(socketMsg, (List<MessageQueue> list, Message message, Object o) -> {
                    //推送根据操作日期的分钟数和队列的个数获取队列，保证同一分钟发送到同一个队列
                    return list.get(loggerMessage.getCreateTime().getMinute() % list.size());
                }, socketMsg);
                //推送到log服务保存一份历史数据
                Message logMsg = new Message(
                        LogEnum.APPLICATION_LOG_TOPIC.getCode(),
                        event.getLevel().levelStr,
                        JSON.toJSONStringWithDateFormat(loggerMessage, "yyyy-MM-dd HH:mm:ss SSS", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                producer.sendOneway(logMsg, (List<MessageQueue> list, Message message, Object o) -> {
                    //推送根据操作日期的分钟数和队列的个数获取队列，保证同一分钟发送到同一个队列
                    return list.get(loggerMessage.getCreateTime().getMinute() % list.size());
                }, logMsg);
            } catch (Exception e) {
                printlnMqSendError(e);
            }
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
    }

    private void printlnMqSendError(Throwable e) {
        instance.setApplicationLogIsSendRocket(Boolean.FALSE.toString());
        System.err.println(">>>>>>>>>>>>>>rocketMQ发送失败，日志将不会实时记录>>>>>>>>>>>>>>>>");
        e.printStackTrace();
        System.err.println(">>>>>>>>>>>>>>rocketMQ发送失败，日志将不会实时记录>>>>>>>>>>>>>>>>");
    }

    private static void printlnMqStartError(Exception e) {
        instance.setApplicationLogIsSendRocket(Boolean.FALSE.toString());
        System.err.println(">>>>>>>>>>>>>>rocketMQ启动失败，日志将不会实时记录>>>>>>>>>>>>>>>>");
        e.printStackTrace();
        System.err.println(">>>>>>>>>>>>>>rocketMQ启动失败，日志将不会实时记录>>>>>>>>>>>>>>>>");
    }

    public LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public String fullDump(ILoggingEvent evt) {
        try {
            IThrowableProxy proxy = evt.getThrowableProxy();
            if (proxy == null)
                return StrUtil.EMPTY;
            StringBuilder builder = new StringBuilder(StrUtil.EMPTY);
            ThrowableProxy iThrowableProxy = (ThrowableProxy) evt.getThrowableProxy();
            builder.append(iThrowableProxy.getThrowable() + "\n");
            for (StackTraceElementProxy step : proxy.getStackTraceElementProxyArray()) {
                String string = step.toString();
                builder.append(CoreConstants.TAB).append(string);
                ThrowableProxyUtil.subjoinPackagingData(builder, step);
                builder.append(CoreConstants.LINE_SEPARATOR);
            }
            return builder.toString();
        } catch (Exception e) {
            addError("exception trying to log exception", e);
            return "exception parsing exception";
        }
    }
}
