package com.netwisd.common.db.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: DB相关配置
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/25 1:49 下午
 */
@AllArgsConstructor
@Getter
public enum DbEnum {
    MASTER("master"),
    TABLE("incloud_base_model_db_ds"),
    POOLNAME("pool_name"),
    ISENABLE("is_enable"),
    USERNAME("username"),
    TYPE("type"),
    PASSWORD("password"),
    URL("url");
    String name;
}
