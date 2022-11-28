package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum ViewerTypeEnum {

    USER(1, "人员"),
    ROLE(2, "角色"),
    DUTY(3, "职务"),
    POST(4, "岗位");

    private Integer code;
    private String message;

    ViewerTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
