package com.netwisd.biz.study.event;

import com.netwisd.common.core.data.IDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 事件发布器
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/15 15:04
 */
@Component
public class StudyPublisher {
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String key, List list){
        applicationContext.publishEvent(new StudyEvent(this,key,list));
    }

    public void publish(String key, IDto iDto){
        applicationContext.publishEvent(new StudyEvent(this,key,iDto));
    }
}
