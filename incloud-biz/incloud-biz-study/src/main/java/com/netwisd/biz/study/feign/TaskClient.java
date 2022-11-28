package com.netwisd.biz.study.feign;

import com.netwisd.base.common.task.dto.SysJobDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/20 10:03
 */
@FeignClient(value = "incloud-base-task")
public interface TaskClient {

    @PostMapping(path = "/api/save")
    public Boolean saveTask(SysJobDto sysJobDto);


}
