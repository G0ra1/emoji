package com.netwisd.base.mdm.event;

import com.netwisd.common.core.data.IDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 通用事件
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/19 4:29 下午
 */
@Setter
@Getter
public class MdmEvent extends ApplicationEvent {
    private Map<String, List> listMap = new HashMap<>();
    private Map<String, IDto> dtoMap = new HashMap<>();

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MdmEvent(Object source,String key,List list) {
        super(source);
        this.listMap.put(key,list);
    }

    public MdmEvent(Object source, String key, IDto iDto) {
        super(source);
        this.dtoMap.put(key,iDto);
    }
}
