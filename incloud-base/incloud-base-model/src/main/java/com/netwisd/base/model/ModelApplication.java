package com.netwisd.base.model;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@EnableFeignClients
@SpringCloudApplication
@EnableAutoConfiguration(exclude = { DruidDataSourceAutoConfigure.class })
public class ModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class);
    }

}
