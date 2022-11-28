package com.netwisd.base.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/7 9:22 上午
 */
@SpringCloudApplication
@EnableFeignClients
@EnableConfigurationProperties
public class SocketApplication {
    public static void main(String[] args){
        SpringApplication.run(SocketApplication.class);
    }
}
