package com.netwisd.base.wf.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/3/24 9:30 上午
 */
@Slf4j
@Component
public class DynamicFeignClient<T> {
    private FeignClientBuilder feignClientBuilder;

    public DynamicFeignClient(@Autowired ApplicationContext appContext) {
        this.feignClientBuilder = new FeignClientBuilder(appContext);
    }

    public T GetFeignClient(final Class<T> type, String serviceId) {
        return this.feignClientBuilder.forType(type, serviceId).build();
    }
}
