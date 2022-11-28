package com.netwisd.biz.mdm.fegin;

import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "incloud-base-dict", fallback = DictClientFallBack.class)
public interface DictClient {

    @GetMapping(path = "/api/tree/list")
    public List<DictTreeVo> list(@RequestParam(value = "extIds")String extIds);

    /**
     * 创建业务规则号 --后端调用
     * @param formName 表单名称
     * @param encondField 业务表单规则值字段
     * @param entityMap 业务实体字段Map
     * @return Result
     */
    @ApiOperation(value = "创建业务规则号", notes = "创建业务规则号")
    @PostMapping("/api/createValue")
    public String getRuleValue(@RequestParam String formName,@RequestParam String encondField,@RequestBody Map<String, Object> entityMap );

}
