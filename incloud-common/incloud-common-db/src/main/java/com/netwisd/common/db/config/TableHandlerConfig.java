package com.netwisd.common.db.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.netwisd.common.core.util.SpringContextUtils;
import com.netwisd.common.db.service.MysqlTableService;
import com.netwisd.common.db.service.TableHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: starter启动配置类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 2:46 下午
 */
@Configuration
@MapperScan(basePackages = {"com.netwisd.common.db.mapper"})
public class TableHandlerConfig {

    @Bean
    public TableHandler tableHandler(){
        TableHandler tableHandler = new TableHandler();
        return tableHandler;
    }

    @Bean
    public MysqlTableService mysqlTableService(){
        MysqlTableService mysqlTableService = new MysqlTableService();
        return mysqlTableService;
    }
}
