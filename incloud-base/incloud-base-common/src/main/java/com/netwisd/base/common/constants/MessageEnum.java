package com.netwisd.base.common.constants;

public enum MessageEnum implements IEnum<String> {

    MESSAGE_SOCKET_PRODUCER_GROUP("MessageSocketProducer", "消息Socket生产组"),
    MESSAGE_SOCKET_TOPIC("MessageSocketTopic", "消息SocketTopic"),
    MESSAGE_SOCKET_CONSUMBER_GROUP("MessageSocketConsumer", "消息Socket消费组"),
    TASKS_SOCKET_PRODUCER_GROUP("TasksSocketProducer", "待办Socket生产组"),
    ;

    private String code;
    private String message;

    MessageEnum(String code, String message) {
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
