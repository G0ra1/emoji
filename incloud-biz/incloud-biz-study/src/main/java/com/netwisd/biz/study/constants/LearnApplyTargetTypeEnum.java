package com.netwisd.biz.study.constants;

/**
 * 学习申请目标类型枚举
 */
public enum LearnApplyTargetTypeEnum {
    COU(0, "课件"),
    SPECIAL(1, "专题");
    public Integer code;
    public String message;

    LearnApplyTargetTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (LearnApplyTargetTypeEnum value : LearnApplyTargetTypeEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
