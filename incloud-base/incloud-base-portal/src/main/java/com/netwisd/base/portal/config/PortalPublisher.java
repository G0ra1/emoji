package com.netwisd.base.portal.config;

import com.netwisd.base.portal.dto.PortalContentNewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/26 3:11 下午
 */
@Component
public class PortalPublisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publish(Map<String, Object> data){

        applicationContext.publishEvent(new PortalEvent(this,data));
    }
}


