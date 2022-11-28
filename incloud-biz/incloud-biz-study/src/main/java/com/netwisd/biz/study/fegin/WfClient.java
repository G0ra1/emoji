package com.netwisd.biz.study.fegin;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("incloud-base-wf")
public interface WfClient {

    //只删除流程
    @PostMapping("/wfEngine/deleteOnlyProcess")
    Result<Boolean> deleteOnlyProcess(@RequestBody WfEngineDto.StateDto stateDto);

}
