package com.netwisd.base.wf;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:111
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/7 9:22 上午
 */
@SpringCloudApplication
@EnableFeignClients
public class WfApplication {
    public static void main(String[] args) {
        SpringApplication.run(WfApplication.class);
    }
}
