package com.netwisd.common.db.util;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidWallConfig;

public class DbDsUtil {

    public static DataSourceProperty initDbDsProperty(String poolName, String url, String username, String password) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(poolName);
        dataSourceProperty.setUrl(url);
        dataSourceProperty.setUsername(username);
        dataSourceProperty.setPassword(password);
        DruidConfig druidConfig = new DruidConfig();
        druidConfig.setInitialSize(5);
        druidConfig.setMinIdle(5);
        druidConfig.setMaxActive(20);
        druidConfig.setMaxWait(30000);
        druidConfig.setBreakAfterAcquireFailure(true);
        druidConfig.setConnectionErrorRetryAttempts(3);
        druidConfig.setTimeBetweenEvictionRunsMillis(60000L);
        druidConfig.setMinEvictableIdleTimeMillis(300000L);
        druidConfig.setValidationQuery("select 1");
        druidConfig.setTestWhileIdle(true);
        druidConfig.setTestOnBorrow(false);
        druidConfig.setTestOnReturn(false);
        druidConfig.setPoolPreparedStatements(false);
        druidConfig.setMaxPoolPreparedStatementPerConnectionSize(20);
        DruidWallConfig druidWallConfig = new DruidWallConfig();
        druidWallConfig.setMultiStatementAllow(true);
        druidWallConfig.setNoneBaseStatementAllow(true);
        druidConfig.setWall(druidWallConfig);
        dataSourceProperty.setDruid(druidConfig);
        return dataSourceProperty;
    }
}
