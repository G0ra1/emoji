package com.netwisd.base.common.constants;

public enum LogEnum implements IEnum<String> {

    APPLICATION_LOG_PRODUCER_GROUP("ApplicationLogProducer", "应用日志生产组"),
    APPLICATION_LOG_CONSUMBER_GROUP("ApplicationLogConsumer", "应用日志消费组"),
    APPLICATION_LOG_TOPIC("ApplicationLogTopic", "应用日志Topic"),
    APPLICATION_LOG_SOCKET_CONSUMBER_GROUP("ApplicationLogSocketConsumer", "应用日志Socket消费组"),
    APPLICATION_LOG_SOCKET_TOPIC("ApplicationLogSocketTopic", "应用日志SocketTopic"),
    SYSTEM_LOG_PRODUCER_GROUP("SystemLogProducer", "系统日志生产组"),
    SYSTEM_LOG_CONSUMBER_GROUP("SystemLogConsumer", "系统日志消费者组"),
    SYSTEM_LOG_TOPIC("SystemLogTopic", "系统日志Topic"),
    SYSTEM_LOG_Tag("SystemLogTag", "系统日志Tag"),
    ;

    private String code;
    private String message;

    LogEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
