package com.netwisd.biz.study.constants;

/**
 * 学习状态枚举
 */
public enum LearnStatusEnum {

    NO_LEARN(0, "未开始"), LEARNING(1, "进行中"), LEARNED(2, "已完成");
    public Integer code;
    public String message;

    LearnStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (LearnStatusEnum value : LearnStatusEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
