package com.netwisd.base.wf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/26 3:11 下午
 */
@Component
public class LicensePublisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publish(License license){
        applicationContext.publishEvent(new LicenseEvent(this,license));
    }
}


