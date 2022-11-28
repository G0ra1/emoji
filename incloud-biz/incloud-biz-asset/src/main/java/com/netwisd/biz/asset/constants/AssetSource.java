package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum AssetSource {

    CAIGOUR(1,"采购",""),
    JIAGONG(2,"甲供",""),
    HISTORY(3,"历史数据","");

    Integer code;
    String name;
    String remark;

    AssetSource(Integer code , String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }
}
