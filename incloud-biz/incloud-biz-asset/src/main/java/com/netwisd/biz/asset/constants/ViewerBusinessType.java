package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum ViewerBusinessType {

    LIST(1,"业务数据"),
    ASSETS(2,"资产数据");

    Integer code;
    String name;

    ViewerBusinessType(Integer code , String name) {
        this.code = code;
        this.name = name;
    }

}
