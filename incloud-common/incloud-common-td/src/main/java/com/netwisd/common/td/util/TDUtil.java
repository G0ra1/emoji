package com.netwisd.common.td.util;

import cn.hutool.db.DbUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.td.properties.TDEngineProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class TDUtil<T> {

    @Autowired
    private TDEngineProperties tdEngineProperties;

    private static DruidDataSource dataSource;

    private final static QueryRunner queryRunner = new QueryRunner();

    /**
     * 查询单个结果
     *
     * @param clazz 返回对象
     * @param sql   执行语句
     * @param <T>
     * @return
     */
    public <T> T getRow(Class<T> clazz, String sql) {
        return exec(connection -> query(connection, sql, clazz, null));
    }

    /**
     * 查询单个结果
     *
     * @param clazz  返回类型
     * @param sql    执行语句
     * @param params 所需要参数
     * @param <T>
     * @return
     */
    public <T> T getRow(Class<T> clazz, String sql, Object... params) {
        return exec(connection -> query(connection, sql, clazz, params));
    }

    /**
     * 获取多个结果
     *
     * @param clazz
     * @param sql
     * @return
     */
    public List<T> getRows(Class<T> clazz, String sql) {
        return exec(connection -> queryList(connection, sql, clazz, null));
    }

    /**
     * 获取多个结果
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public List<T> getRows(Class<T> clazz, String sql, Object... params) {
        return exec(connection -> queryList(connection, sql, clazz, params));
    }

    /**
     * 插入记录
     *
     * @param sql
     * @return
     */
    public int insert(String sql) {
        return exec(connection -> update(connection, sql, null));
    }

    /**
     * 插入记录
     *
     * @param sql
     * @return
     */
    public int insert(String sql, Object... params) {
        return exec(connection -> update(connection, sql, params));
    }

    /**
     * 批量插入
     *
     * @param sql
     * @param params
     * @return
     */
    public int[] inserBatch(String sql, Object[][] params) {
        return exec(connection -> batch(connection, sql, params));
    }

    /**
     * 分页查询limit n,m 从第(n+1)行开始取值，取m条目数
     *
     * @param clazz
     * @param sql
     * @param page
     * @param params
     * @return
     */
    public Page<T> getRows(Class<T> clazz, String sql, Page<T> page, Object... params) {
        long size = page.getSize();//每页个数
        long current = page.getCurrent();//当前页码
        page.setTotal(getRows(clazz, sql, params).size());//总个数
        String fianlSql = sql + (" limit " + (current - 1) * size + "," + size);
        List records = exec(connection -> queryList(connection, fianlSql, clazz, params));
        return page.setRecords(records);
    }

    /**
     * 删除表
     *
     * @param table
     * @return
     */
    public int deleteTable(String table) {
        return exec(connection -> update(connection, "drop table if exists " + table));
    }

    private List<T> queryList(DruidPooledConnection connection, String sql, Class clazz, Object... params) {
        try {
            log.info("执行语句:{},参数:{}", sql, params);
            return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
        } catch (SQLException e) {
            log.error("SQL语句执行失败:{}", e);
            throw new IncloudException("语句执行失败");
        }
    }

    private <T> T query(DruidPooledConnection connection, String sql, Class<T> clazz, Object... params) {
        try {
            log.info("执行语句:{},参数:{}", sql, params);
            return queryRunner.query(connection, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            log.error("SQL语句执行失败:{}", e);
            throw new IncloudException("语句执行失败");
        }
    }

    private int[] batch(DruidPooledConnection connection, String sql, Object[][] params) {
        try {
            log.info("执行语句:{},参数:{}", sql, params);
            return queryRunner.batch(connection, sql, params);
        } catch (SQLException e) {
            log.error("SQL语句执行失败:{}", e);
            throw new IncloudException("语句执行失败");
        }
    }

    private int update(DruidPooledConnection connection, String sql, Object... params) {
        try {
            log.info("执行语句:{},参数:{}", sql, params);
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            log.error("SQL语句执行失败:{}", e);
            throw new IncloudException("语句执行失败");
        }
    }

    private <T> T exec(Function<DruidPooledConnection, T> function) {
        DruidPooledConnection connection = null;
        try {
            connection = getInstance(tdEngineProperties).getConnection();
            return function.apply(connection);
        } catch (IncloudException e) {
            throw e;
        } catch (Exception e) {
            log.error("TDengine执行失败:{}", e);
            throw new IncloudException("执行失败");
        } finally {
            DbUtil.close(connection);
        }
    }

    private synchronized DruidDataSource getInstance(TDEngineProperties tdEngineProperties) {
        if (dataSource == null) {
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(tdEngineProperties.getDriverClassName());
            dataSource.setUrl(tdEngineProperties.getUrl());
            dataSource.setUsername(tdEngineProperties.getUsername());
            dataSource.setPassword(tdEngineProperties.getPassword());
            dataSource.setInitialSize(tdEngineProperties.getInitialSize());
            dataSource.setMinIdle(tdEngineProperties.getMinIdle());
            dataSource.setMaxActive(tdEngineProperties.getMaxActive());
            dataSource.setMaxWait(tdEngineProperties.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(tdEngineProperties.getTimeBetweenEvictionRunsMillis());
            dataSource.setMinEvictableIdleTimeMillis(tdEngineProperties.getMinEvictableIdleTimeMillis());
            dataSource.setMaxEvictableIdleTimeMillis(tdEngineProperties.getMaxEvictableIdleTimeMillis());
            dataSource.setValidationQuery(tdEngineProperties.getValidationQuery());
        }
        return dataSource;
    }

}