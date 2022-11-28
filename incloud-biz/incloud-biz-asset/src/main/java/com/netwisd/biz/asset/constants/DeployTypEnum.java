package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum DeployTypEnum {

    BORROW(2, "借用"),
    TRANSFER(3, "调拨");

    private Integer code;
    private String name;

    DeployTypEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
