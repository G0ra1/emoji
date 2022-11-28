package com.netwisd.common.db.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 数据库列定义
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:22 下午
 */
@Setter
@Getter
public class ColumnMeta {
    public static final String COLUMN_NAME_KEY = "columnName";

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private String ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private Integer characterMaximumLength;
    private Integer characterOctetLength;
    private Integer numericPrecision;
    private Integer numericScale;
    private Integer datetimePrecision;
    private String characterSetName;
    private String collationName;
    private String columnType;
    private String columnKey;
    private String columnComment;
    private String generationExpression;
    private String extra;
    private String privileges;
}
