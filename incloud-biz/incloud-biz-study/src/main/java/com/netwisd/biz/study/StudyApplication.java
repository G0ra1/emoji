package com.netwisd.biz.study;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.Inet4Address;

/**
 * @Description: 测试构建
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/11/17 16:30
 */
@SpringCloudApplication
@EnableFeignClients
@EnableConfigurationProperties
@EnableAsync
public class StudyApplication {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(StudyApplication.class);
        long beginTime = System.currentTimeMillis();
        ConfigurableApplicationContext context = SpringApplication.run(StudyApplication.class);
        try {
            //ip
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            //获取端口
            String prot = context.getBean(Environment.class).getProperty("server.port");
            String ip = "http://" + hostAddress + ":" + prot + "/swagger-ui.html";
            logger.info(ip);
            String doc = "http://" + hostAddress + ":" + prot + "/doc.html";
            logger.info(doc);
            logger.info("项目启动了,当前时间:" + DateUtil.now()+"，启动耗时："+String.valueOf(System.currentTimeMillis()-beginTime)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}