package com.netwisd.base.msg.mq.rocket.consumer;

public enum ConsumeStatus {
    /**
     * 消费成功
     */
    SUCCESS,
    /**
     * 需要重试
     */
    RETRY
}
