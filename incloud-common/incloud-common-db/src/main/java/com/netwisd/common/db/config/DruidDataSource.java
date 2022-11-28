package com.netwisd.common.db.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * @Description: 读取druid数据源信息
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/25 12:40 下午
 */
@Data
@Configuration
@Slf4j
@ConfigurationProperties("spring.datasource.druid")
public class DruidDataSource extends DataSourceProperty{
}
