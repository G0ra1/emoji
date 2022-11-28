package com.netwisd.biz.asset.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 主数据总的配置类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/25 12:01 下午
 */
@Configuration
/**
 * 排除掉druid starter 自带的自动配置类；
 * 如果使用DruidDataSourceAutoConfigure，会导致dynamicDataSource自动配置中的datasource失效；我们自定义的动态数据源就没法自动加载
 */
@EnableAutoConfiguration(exclude = { DruidDataSourceAutoConfigure.class })
public class AssetConfig {
}
