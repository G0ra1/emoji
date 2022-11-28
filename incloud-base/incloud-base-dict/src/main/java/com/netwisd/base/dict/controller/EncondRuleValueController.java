package com.netwisd.base.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.dto.EncondRuleValueDto;
import com.netwisd.base.common.dict.vo.EncondRuleValueVo;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.dict.service.EncondRuleValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 编码规则值 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/encondRuleValue" )
@Api(value = "encondRuleValue", tags = "编码规则值Controller")
@Slf4j
public class EncondRuleValueController {

    private final  EncondRuleValueService encondRuleValueService;

    /**
     * 分页查询编码规则值
     * 没有使用参数注解，就是默认从form请求的方式
     * @param encondRuleValueDto 编码规则值
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody EncondRuleValueDto encondRuleValueDto) {
        Page pageVo = encondRuleValueService.list(encondRuleValueDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询编码规则值
     * @param encondRuleValueDto 编码规则值
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody EncondRuleValueDto encondRuleValueDto) {
        Page pageVo = encondRuleValueService.lists(encondRuleValueDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询编码规则值
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<EncondRuleValueVo> get(@PathVariable("id" ) Long id) {
        EncondRuleValueVo encondRuleValueVo = encondRuleValueService.get(id);
        log.debug("查询成功");
        return Result.success(encondRuleValueVo);
    }

    /**
     * 新增编码规则值
     * @param encondRuleValueDto 编码规则值
     * @return Result
     */
    @ApiOperation(value = "新增编码规则值", notes = "新增编码规则值")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody EncondRuleValueDto encondRuleValueDto) {
        Boolean result = encondRuleValueService.save(encondRuleValueDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改编码规则值
     * @param encondRuleValueDto 编码规则值
     * @return Result
     */
    @ApiOperation(value = "修改编码规则值", notes = "修改编码规则值")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody EncondRuleValueDto encondRuleValueDto) {
        Boolean result = encondRuleValueService.update(encondRuleValueDto);
        log.debug("更新成功");
        return Result.success(result);
    }
    /**
     * 通过id删除编码规则值
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除编码规则值", notes = "通过id删除编码规则值")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = encondRuleValueService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }


    /**
     * 创建规则号
     * @param encondRuleDto
     * @return Result
     */
    @ApiOperation(value = "创建规则号", notes = "创建规则号")
    @PostMapping("/createEncondValue")
    //public Result<String> creatEncondValue(@RequestParam Long ruleId,@RequestParam Map<String, Object> formMap,@RequestParam String ruleType) {
    public Result<String> creatEncondValue(@RequestBody EncondRuleDto encondRuleDto) {
        String value = encondRuleValueService.creatEncondValue(encondRuleDto);
        log.debug("创建成功");
        return Result.success(value);
    }


    /**
     * 通过规则ID跟规则值删除 规则值记录
     * @param formName 表单名称
     * @param value 规则值
     * @param ruleType 规则类型
     * @return Result
     */
    @ApiOperation(value = "通过表单名称跟规则值删除 规则值记录", notes = "通过表单名称跟规则值删除 规则值记录")
    @DeleteMapping("/deleteEncondValue" )
    public Result<Boolean> deleteValue(@RequestParam String formName,@RequestParam String value,@RequestParam String ruleType) {
        Boolean result = encondRuleValueService.deleteEncondValue(formName,value,ruleType);
        log.debug("删除成功");
        return Result.success(result);
    }


    /**
     * 根据表单名称查询该表单关联的规则详情
     * @param formName 表单名称
     * @return Result
     */
    @ApiOperation(value = "根据表单名称查询该表单关联的规则详情", notes = "根据表单名称查询该表单关联的规则详情")
    @GetMapping("/getRuleDetail")
    public Result<EncondRuleVo> getRuleDetail(@RequestParam String formName, @RequestParam String ruleType) {
        EncondRuleVo encondRulelVo = encondRuleValueService.getRuleDetail(formName,ruleType);
        log.debug("查询成功");
        return Result.success(encondRulelVo);
    }

    /**
     * 根据表单名称  表单字段生成规则值 --前端调用
     * @param formName 表单名称
     * @param encondField 字段名称
     //* @param entityMap 业务实体字段Map
     * @return Result
     */
    @ApiOperation(value = "根据表单名称 业务类型 生成规则值", notes = "根据表单名称 业务类型 生成规则值")
    @GetMapping("/createValue")
    public String createValue(@RequestParam String formName,@RequestParam String encondField ) {
        return encondRuleValueService.getRuleValueForqd(formName,encondField);
    }



    /**
     * 根据流程实例Id生成规则值 --前端调用
     * @param camundaProcdefId 流程实例Id
     */
    @ApiOperation(value = "根据流程实例Id生成规则值", notes = "根据流程实例Id生成规则值")
    @GetMapping("/createWfValue")
    public String createWfValue(@RequestParam String camundaProcdefId ) {
        return encondRuleValueService.createWfValue(camundaProcdefId);
    }

}
