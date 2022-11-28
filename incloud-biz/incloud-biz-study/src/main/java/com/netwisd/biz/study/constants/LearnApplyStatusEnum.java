package com.netwisd.biz.study.constants;

/**
 * 学习申请状态枚举
 */
public enum LearnApplyStatusEnum {
    NO_AUDIT(0, "未审核"), APPLY_PASS(1, "审核通过"), APPLY_NOTPASS(2, "审核不通过");
    public Integer code;
    public String message;

    LearnApplyStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (LearnApplyStatusEnum value : LearnApplyStatusEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
