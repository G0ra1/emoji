package com.netwisd.biz.mdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: 测试构建
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/7 9:22 上午
 */
@SpringCloudApplication
@EnableFeignClients
@EnableConfigurationProperties
@EnableAsync
public class MdmBizApplication {
    public static void main(String[] args){
        SpringApplication.run(MdmBizApplication.class);
    }
}
