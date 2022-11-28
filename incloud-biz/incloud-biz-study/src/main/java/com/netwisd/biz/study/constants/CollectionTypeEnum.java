package com.netwisd.biz.study.constants;

/**
 * 收藏类型
 */
public enum CollectionTypeEnum {
    SPECIAL(0, "专题"),
    LESSON(1, "课程");
    public Integer code;
    public String message;

    CollectionTypeEnum(Integer code, String message) {
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
