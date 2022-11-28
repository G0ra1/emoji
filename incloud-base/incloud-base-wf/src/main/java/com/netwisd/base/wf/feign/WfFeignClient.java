package com.netwisd.base.wf.feign;

import com.netwisd.base.wf.dto.BizTestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zouliming@netwisd.com
 * @description 主数据的feign调用
 * @date 2022/1/18 11:25
 */
@FeignClient(value = "incloud-base-wf")
public interface WfFeignClient {
    @PostMapping("/test/save")
    void save(@RequestBody BizTestDto bizTestDto);
}
