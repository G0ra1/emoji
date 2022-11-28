package com.netwisd.base.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: 网关--基于 z
 * current package com.netwisd.base.zuul
 * @Author: zouliming
 * @Date: 2020/2/8 12:18 下午
 */
@EnableFeignClients
@SpringCloudApplication
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        System.out.println("zuul-service启动成功..........");
    }

}
