package com.netwisd.biz.asset.fegin;

import com.netwisd.base.common.center.dto.TodoDto;
import com.netwisd.base.common.center.vo.TodoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "incloud-base-center")
public interface CenterClient {

    @PostMapping(path = "/api/send")
    public TodoVo send(@RequestBody TodoDto todoDto);

    @PostMapping(path = "/api/toProcess")
    public TodoVo toProcess(@RequestBody TodoDto todoDto);
}
