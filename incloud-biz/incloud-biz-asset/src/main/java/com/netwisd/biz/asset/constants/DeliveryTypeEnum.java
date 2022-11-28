package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum DeliveryTypeEnum {

    DELIVERY(1, "出库"),
    LEND(2, "借出"),
    TRANSFER(3, "调拨");

    private Integer code;
    private String name;

    DeliveryTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
