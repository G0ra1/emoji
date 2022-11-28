package com.netwisd.base.wf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/26 3:01 下午
 */
@Setter
@Getter
public class LicenseEvent extends ApplicationEvent {
    private License license;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LicenseEvent(Object source, License license) {
        super(source);
        this.license = license;
    }
}
