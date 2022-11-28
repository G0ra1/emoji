package com.netwisd.base.mdm.event;

import com.netwisd.common.core.data.IDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 通用事件发布器
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/19 4:29 下午
 */
@Component
public class MdmPublisher {
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String key,List list){
        applicationContext.publishEvent(new MdmEvent(this,key,list));
    }

    public void publish(String key,IDto iDto){
        applicationContext.publishEvent(new MdmEvent(this,key,iDto));
    }

}
