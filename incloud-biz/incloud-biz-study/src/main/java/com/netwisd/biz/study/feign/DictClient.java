package com.netwisd.biz.study.feign;

import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "incloud-base-dict")
public interface DictClient {

    @ApiOperation(value = "获取树形子集")
    @GetMapping("/dictTree/childList/{parentCode}")
    Result<List<DictTreeVo>> childList(@PathVariable(name = "parentCode") String parentCode);
}
