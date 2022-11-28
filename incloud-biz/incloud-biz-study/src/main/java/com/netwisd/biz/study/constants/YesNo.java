package com.netwisd.biz.study.constants;

/**
 * 统一公用枚举(包含是否)
 */
public enum YesNo {

    NO(0, "否"),
    YES(1, "是");
    public Integer code;
    public String message;

    YesNo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (YesNo value : YesNo.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
