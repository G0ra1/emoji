package com.netwisd.base.dict;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class);
    }

}
