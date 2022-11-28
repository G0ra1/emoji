package com.netwisd.base.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: current project incloud
 * current package com.netwisd.base.oauth
 * @Author: zouliming
 * @Date: 2020/2/8 1:01 下午
 */
@EnableFeignClients
@SpringCloudApplication
public class OauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
        System.out.println("oauth-service启动成功..........");
    }

}
