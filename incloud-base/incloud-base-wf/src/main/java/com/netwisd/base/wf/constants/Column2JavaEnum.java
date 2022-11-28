package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 数据库字段类型定义
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/22 6:46 下午
 */
@AllArgsConstructor
@Getter
public enum Column2JavaEnum {
    VARCHAR("string"),
    INTEGER("integer"),
    INT("integer"),
    TINYINT("tinyint"),
    BIGINT("long"),
    DECIMAL("decimal"),
    FLOAT("float"),
    DOUBLE("double"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    TIMESTRAMP("timestramp"),
    TEXT("text"),
    LONGTEXT("longtext"),
    BLOB("blob"),
    LONGBLOB("longblob");
    String name;
}
