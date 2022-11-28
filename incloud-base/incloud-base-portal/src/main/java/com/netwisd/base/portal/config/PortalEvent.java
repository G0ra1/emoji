package com.netwisd.base.portal.config;

import com.netwisd.base.portal.dto.PortalContentNewsDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/26 3:01 下午
 */
@Setter
@Getter
public class PortalEvent extends ApplicationEvent {
    private Map<String, Object> data;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public PortalEvent(Object source,Map<String, Object> data) {
        super(source);
        this.data = data;
    }
}
