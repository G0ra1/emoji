package com.netwisd.base.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class);
    }

}
