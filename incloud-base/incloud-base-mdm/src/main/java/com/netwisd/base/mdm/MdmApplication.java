package com.netwisd.base.mdm;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/13 8:22 上午
 */
@SpringCloudApplication
@EnableFeignClients
public class MdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdmApplication.class, args);
    }
}
