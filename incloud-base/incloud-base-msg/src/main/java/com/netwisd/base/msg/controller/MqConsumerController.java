package com.netwisd.base.msg.controller;


import com.netwisd.base.msg.mq.ConsumerFactory;
import com.netwisd.base.msg.mq.rabbit.consumer.RabbitConsumer;
import com.netwisd.base.msg.mq.rocket.consumer.ConsumerConfig;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/consumer")
@AllArgsConstructor
@Api(value = "MQConsumer", tags = "MQConsumer Controller")
public class MqConsumerController {

    @SysLog(value = "MQConsumer新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/save")
    @ApiOperation(value = "MQ新增")
    public Result addMQ(@RequestBody Map map) throws Exception {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setHost(String.valueOf(map.get("host")));
        consumerConfig.setPort(Integer.valueOf(String.valueOf(map.get("port"))));
        consumerConfig.setUsername(String.valueOf(map.get("username")));
        consumerConfig.setPassword(String.valueOf(map.get("password")));
        consumerConfig.setQueueName(String.valueOf(map.get("queueName")));
        consumerConfig.setVirtualHost(String.valueOf(map.get("virtualHost")));
        ConsumerFactory consumer = new RabbitConsumer(consumerConfig);
        consumer.start();

        return Result.success();
    }

    @SysLog(value = "MQConsumer删除", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/shutdown")
    @ApiOperation(value = "MQ删除")
    public Result shutdownMq(@RequestBody Map map) throws Exception {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setHost(String.valueOf(map.get("host")));
        consumerConfig.setPort(Integer.valueOf(String.valueOf(map.get("port"))));
        consumerConfig.setUsername(String.valueOf(map.get("username")));
        consumerConfig.setPassword(String.valueOf(map.get("password")));
        consumerConfig.setQueueName(String.valueOf(map.get("queueName")));
        consumerConfig.setVirtualHost(String.valueOf(map.get("virtualHost")));
        ConsumerFactory consumer = new RabbitConsumer(consumerConfig);
        consumer.shutdown();
        return Result.success();
    }

}
