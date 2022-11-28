package com.netwisd.biz.study.constants;

/**
 * 专题时间类型
 */
public enum SpecialTimeTypeEnum {
    ORDINARY(0, "专题培训"),
    LONG_TERM(1, "长期培训");
    public Integer code;
    public String message;

    SpecialTimeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (SpecialTimeTypeEnum value : SpecialTimeTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
