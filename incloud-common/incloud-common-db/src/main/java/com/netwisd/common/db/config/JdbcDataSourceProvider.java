package com.netwisd.common.db.config;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.RsaUtil;
import com.netwisd.common.db.constants.DbEnum;
import com.netwisd.common.db.util.DbDsUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义的基于数据库的数制源实现
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/24 4:44 下午
 */
@AllArgsConstructor
@Slf4j
public class JdbcDataSourceProvider extends AbstractDataSourceProvider {

    private final DruidDataSource druidDataSource;

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = selectDs();
        //使用了动态数据源，得设置一个主数据源
        dataSourcePropertiesMap.put(DbEnum.MASTER.getName(), druidDataSource);
        return createDataSourceMap(dataSourcePropertiesMap);
    }

    /**
     * 查询数据源表
     *
     * @return
     */
    public Map<String, DataSourceProperty> selectDs() {
        Map<String, DataSourceProperty> map = new HashMap<>();
        try {
            List<Entity> list = Db.use().findAll(DbEnum.TABLE.getName());
            initData(list, map);
        } catch (Exception throwables) {
            log.error("incloud_base_model_db_ds表不存在，没有切换数据源可选！");
        }
        return map;
    }

    public List<Entity> getTable(String tableName) {
        List<Entity> list = null;
        try {
            list = Db.use().findAll(tableName);
        } catch (Exception throwables) {
            log.error("incloud_base_model_db_ds表不存在，没有切换数据源可选！");
        }
        return list;
    }

    /**
     * 组装数据
     *
     * @param list
     * @param map
     */
    public void initData(List<Entity> list, Map<String, DataSourceProperty> map) {
        for (Entity entity : list) {
            Integer isEnable = entity.getInt(DbEnum.ISENABLE.getName());
            String type = entity.getStr(DbEnum.TYPE.getName());
            String poolName = entity.getStr(DbEnum.POOLNAME.getName());
            String url = entity.getStr(DbEnum.URL.getName());
            String username = entity.getStr(DbEnum.USERNAME.getName());
            String password;
            //启用并且是Mysql加入数据源
            if (isEnable == YesNo.YES.getCode() && "mysql".equals(type)) {
                try {
                    //处理下密码为明文
                    password = RsaUtil.handleSecret(entity.getStr(DbEnum.PASSWORD.getName()));
                    log.info("开始处理数据源:{},{},{}", url, username, password);
                    DriverManager.getConnection(url, username, password);
                } catch (Exception e) {
                    log.error("数据源:{}解密失败或链接不上，跳过本数据源,{}", poolName, entity);
                    continue;
                }
                map.put(poolName, DbDsUtil.initDbDsProperty(poolName, url, username, password));
            }
        }
    }
}