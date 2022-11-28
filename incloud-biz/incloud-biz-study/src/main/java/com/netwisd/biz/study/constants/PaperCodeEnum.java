package com.netwisd.biz.study.constants;

public enum PaperCodeEnum {
    GDSJ(0, "固定试卷"),
    SJSJ(1, "随机试卷");


    public Integer code;
    public String message;

    PaperCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (PaperCodeEnum value : PaperCodeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
