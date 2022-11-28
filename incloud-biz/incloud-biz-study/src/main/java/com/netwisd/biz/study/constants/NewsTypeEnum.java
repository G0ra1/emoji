package com.netwisd.biz.study.constants;

public enum NewsTypeEnum {
    QB("0","全部查"),
    YW("1", "要闻"),
    JTDT("2", "集团动态"),
    DXDT("3", "党校动态"),
    DSBN("4", "党史百年"),
    YQFK("5", "疫情防控"),
    SZDW("6", "师资队伍"),
    DXJS("7", "党校介绍"),
    ZTJJ("8", "专题简介");

    public String code;
    public String message;

    NewsTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (NewsTypeEnum value : NewsTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }

}
