package com.netwisd.base.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:  asdf
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/7 9:22 上午
 */
@SpringCloudApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties
public class PortalApplication {
    public static void main(String[] args){
        SpringApplication.run(PortalApplication.class);
    }
}
