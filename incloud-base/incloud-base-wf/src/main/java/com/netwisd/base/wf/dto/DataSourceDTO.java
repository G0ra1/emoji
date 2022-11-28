package com.netwisd.base.wf.dto;

import lombok.Data;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/25 11:19 上午
 */
@Data
public class DataSourceDTO{
    /**
     * 连接池名称(只是一个名称标识)</br> 默认是配置文件上的名称
     */
    private String poolName = "incloud_alibaba";
    /**
     * 连接池类型，如果不设置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type = com.alibaba.druid.pool.DruidDataSource.class;
    /**
     * JDBC driver
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    /**
     * JDBC url 地址
     */
    private String url = "jdbc:mysql://localhost:3301/incloud_alibaba?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true";
    /**
     * JDBC 用户名
     */
    private String username = "root";
    /**
     * JDBC 密码
     */
    private String password = "Netwisd*8";
}
