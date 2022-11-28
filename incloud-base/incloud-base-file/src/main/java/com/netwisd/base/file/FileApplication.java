package com.netwisd.base.file;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description: current project incloud
 * current package com.netwisd.base.file
 * @Author: zouliming
 * @Date: 2020/2/9 3:30 下午
 */
@EnableFeignClients
@SpringCloudApplication
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
