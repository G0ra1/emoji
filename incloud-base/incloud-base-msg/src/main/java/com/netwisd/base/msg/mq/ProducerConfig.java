package com.netwisd.base.msg.mq;

import lombok.Data;

@Data
public class ProducerConfig extends MqConfig {

    private String topic;

    private String tag;

    private String producerGroup;

    private int sendMsgTimeout = 3000;

}
