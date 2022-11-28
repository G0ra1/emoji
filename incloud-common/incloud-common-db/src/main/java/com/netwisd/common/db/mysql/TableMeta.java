package com.netwisd.common.db.mysql;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * @Description: 数据库表定义
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:22 下午
 */
@Setter
@Getter
public class TableMeta {

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private Integer version;
    private String rowFormat;
    private Integer tableRows;
    private Integer avgRowLength;
    private Integer dataLength;
    private Integer maxDataLength;
    private Integer indexLength;
    private Integer dataFree;
    private Integer autoIncrement;
    private Date createTime;
    private Date updateTime;
    private Date checkTime;
    private String tableCollation;
    private String checksum;
    private String createOptions;
    private String tableComment;
}
