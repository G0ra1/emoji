package com.netwisd.biz.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description: 测试1
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/11/17 16:30
 */
@SpringCloudApplication
@EnableFeignClients
@EnableConfigurationProperties
@EnableAsync
public class AssetApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class);
    }
}