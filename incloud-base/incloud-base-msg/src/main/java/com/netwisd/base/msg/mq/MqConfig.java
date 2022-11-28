package com.netwisd.base.msg.mq;

import lombok.Data;

@Data
public class MqConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    private String virtualHost;

    private String queueName;

    private Class<?> messageClass;

    private boolean orderlyMessage;
}

