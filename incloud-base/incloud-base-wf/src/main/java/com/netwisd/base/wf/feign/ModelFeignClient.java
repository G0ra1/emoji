package com.netwisd.base.wf.feign;

import com.netwisd.base.common.model.vo.ModelFormVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @Description:
 * @Author: XHL
 * @Date: 2022/2/28 11:12 上午
 */
@FeignClient(value = "incloud-base-model")
public interface ModelFeignClient {

    @ApiOperation(value = "查询数据建模中的表单详情", notes = "查询数据建模中的表单详情")
    @GetMapping(value = "/api/formDetail/{id}")
    public ModelFormVo getFormDetail(@PathVariable(value = "id") String id);

    @ApiOperation(value = "查询数据建模中的表单详情", notes = "查询数据建模中的表单详情")
    @GetMapping(value = "/api/formDetailByFormName/{formName}")
    public ModelFormVo formDetailByFormName(@PathVariable(value = "formName") String formName);
}
