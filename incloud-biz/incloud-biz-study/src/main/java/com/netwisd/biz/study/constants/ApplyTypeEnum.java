package com.netwisd.biz.study.constants;

/**
 * 变动类型
 */
public enum ApplyTypeEnum {
    ADD(0, "新增"),
    UPD(1, "修改");



    public Integer  code;
    public String message;

    ApplyTypeEnum(Integer  code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer  getCode() {
        return code;
    }

    public static String getMessage(Integer  code){
        for (ApplyTypeEnum value : ApplyTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
