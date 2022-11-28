package com.netwisd.base.common.user.eunm;

/**
 * 缓存类型
 */
public enum CacheTypeEnum {

    EMPLCACHE(1, "人员缓存版本"),
    DEPTCACHE(2, "组织缓存版本");

    public Integer code;

    public String message;

    CacheTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
