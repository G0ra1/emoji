package com.netwisd.base.wf.feign;

import com.netwisd.base.wf.dto.UrmParamsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Description: 自定义请求内置（分级）标准人员表达式
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/30 10:27 上午
 */
@FeignClient("incloud-base-urm")
public interface InnerExpressionClient {
    @RequestMapping(value = "/expressionParser", method = RequestMethod.POST)
    List<Object> expressionParser(@RequestBody UrmParamsDto params);
}
