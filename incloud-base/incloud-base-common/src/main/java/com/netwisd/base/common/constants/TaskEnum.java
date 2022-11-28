package com.netwisd.base.common.constants;

public enum TaskEnum implements IEnum<String> {

    TASK_PRODUCER_GROUP("TaskProducer", "调度任务发送通知生产组"),
    TASK_TOPIC("TaskTopic", "调度任务发送通知Topic"),
    TASK_CONSUMBER_GROUP("TaskConsumer", "调度任务发送通知消费组"),
    TASK_PROPERTIES("TASK_PROPERTIES", "调度任务执行目标Key"),
    TASK_CALLBACK_PRODUCER_GROUP("TaskCallBackProducer", "调度任务回调生产组"),
    TASK_CALLBACK_TOPIC("TaskCallBackTopic", "调度任务回调Topic"),
    TASK_CALLBACK_CONSUMBER_GROUP("TaskCallBackConsumer", "调度任务回调消费组"),
    ;



    private String code;
    private String message;

    TaskEnum(String code, String message) {
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
