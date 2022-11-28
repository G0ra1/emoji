package com.netwisd.base.log;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * asdfadsf
 */
@EnableFeignClients
@SpringCloudApplication
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class);
    }

}
