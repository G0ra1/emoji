package com.netwisd.common.task.consumer;

import cn.hutool.json.JSONUtil;
import com.netwisd.base.common.constants.TaskEnum;
import com.netwisd.base.common.task.dto.SysJobDto;
import com.netwisd.base.common.util.ExceptionUtil;
import com.netwisd.base.common.util.JobInvokeUtil;
import com.netwisd.common.core.util.SpringUtils;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Date;

@Data
@Slf4j
public class JobConsumer implements ApplicationRunner {

    private String appName;

    private String namesrvAddr;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String newAppName = appName.replaceAll("-", "");
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TaskEnum.TASK_CONSUMBER_GROUP.getCode() + newAppName);
        // 设置NameServer的地址
        consumer.setNamesrvAddr(namesrvAddr);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(TaskEnum.TASK_TOPIC.getCode(), newAppName);
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            try {
                for (MessageExt messageExt : list) {
                    dealWithMsg(new String(messageExt.getBody()));
                }
            } finally {
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
    }

    @SneakyThrows
    private void dealWithMsg(String msgStr) {
        log.info("接收到的消息 received！{}", msgStr);
        SysJobDto sysJobDto = JSONUtil.toBean(msgStr, SysJobDto.class);
        try {
            JobInvokeUtil.invokeMethod(sysJobDto.getInvokeTarget());
        } catch (Exception e) {
            sysJobDto.setStatus(1);
            log.error("接受到消息方法接收到异常信息:{}", ExceptionUtil.getExceptionMessage(e));
        } finally {
            sysJobDto.setLogEndTime(new Date().getTime());
            //回调任务任务执行完成了；
            Message msg = new Message(
                    TaskEnum.TASK_CALLBACK_TOPIC.getCode(),
                    "*",
                    JSONUtil.toJsonStr(sysJobDto).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SpringUtils.getBean(DefaultMQProducer.class).send(msg);
        }
    }

}
