package com.netwisd.common.db.service;

import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Description: 自动建表入口
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:16 下午
 */
@Slf4j
public class TableHandler {

    /**
     * 定义数据库
     */
    public static String MYSQL = "mysql";

    /**
     * 数据库类型;
     */
    @Value("${incloud.database}")
    private String databaseType = MYSQL;

    @Value("${incloud.table.enable}")
    public boolean enable = false;

    public String getDatabaseType() {
        return databaseType;
    }

    @Autowired
    private MysqlTableService mysqlTableService;

    @PostConstruct
    public void start() {
        if (MYSQL.equals(databaseType))
            mysqlTableService.createMysqlTable();
        else
            throw new IncloudException("其他数据库创建未实现！");
    }
}
