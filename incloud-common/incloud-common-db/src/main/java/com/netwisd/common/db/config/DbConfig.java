package com.netwisd.common.db.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: DB总配置类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/25 12:45 下午
 */
@Configuration
@AllArgsConstructor
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DruidDataSource.class)
public class DbConfig {

    private final DruidDataSource druidDataSource;

    @Bean
    public DsProcessor dsProcessor() {
        DSParamProcessor dsParamProcessor = new DSParamProcessor();
        return dsParamProcessor;
    }

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(){
        JdbcDataSourceProvider jdbcDataSourceProvider = new JdbcDataSourceProvider(druidDataSource);
        return jdbcDataSourceProvider;
    }
}
