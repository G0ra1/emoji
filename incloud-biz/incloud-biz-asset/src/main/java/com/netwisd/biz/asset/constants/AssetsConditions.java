package com.netwisd.biz.asset.constants;

/**
 * 自定义资产查询条件-拼接sql
 */
public enum AssetsConditions {

    STOCK(" and stock_number","库存资产"),
    STORAGE(" and storage_number","入库资产"),
    FOR_STORAGE(" and (assets_number - storage_number)","验收待入库资产"),
    DELIVERY(" and delivery_number","出库资产"),
    WITHDRAWAL(" and withdrawal_number","退库资产"),
    ACCEPT(" and accept_number","领用资产");

    public String conditions;
    public String message;

    AssetsConditions(String conditions, String message) {
        this.conditions = conditions;
        this.message = message;
    }
}
