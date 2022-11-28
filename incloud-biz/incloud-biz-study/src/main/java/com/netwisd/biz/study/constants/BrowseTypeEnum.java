package com.netwisd.biz.study.constants;

/**
 * 收藏类型
 */
public enum BrowseTypeEnum {
    LESSON(0, "课程"),
    SPECIAL(1, "专题");
    public Integer code;
    public String message;

    BrowseTypeEnum(Integer code, String message) {
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
