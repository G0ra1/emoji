package com.netwisd.base.wf.starter.config;


import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfServiceImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/30 13:51:00
 */
@Configuration
@EnableFeignClients(basePackageClasses = {com.netwisd.base.wf.starter.feign.WfClient.class})
public class WfStarterConfig {

    @Bean
    public WfService wfService(){
        WfService wfService = new WfServiceImpl();
        return wfService;
    }
}
