package com.netwisd.biz.study.constants;

/**
 * 课件类型枚举
 */
public enum CouTypeEnum {
    AUDIO(1, "音频"), VIDEO(2, "视频"), DOCUMNET(3, "文档");
    public Integer code;
    public String message;

    CouTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (CouTypeEnum value : CouTypeEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
