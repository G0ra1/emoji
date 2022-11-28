package com.netwisd.base.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.dict.service.EncondRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/encondRule" )
@Api(value = "encondRule", tags = "编码规则Controller")
@Slf4j
public class EncondRuleController {

    private final  EncondRuleService encondRuleService;

    /**
     * 分页查询编码规则
     * 没有使用参数注解，就是默认从form请求的方式
     * @param encondRuleDto 编码规则
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody EncondRuleDto encondRuleDto) {
        Page pageVo = encondRuleService.list(encondRuleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询编码规则
     * @param encondRuleDto 编码规则
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody EncondRuleDto encondRuleDto) {
        Page pageVo = encondRuleService.lists(encondRuleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询编码规则
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<EncondRuleVo> get(@PathVariable("id" ) Long id) {
        EncondRuleVo encondRuleVo = encondRuleService.get(id);
        log.debug("查询成功");
        return Result.success(encondRuleVo);
    }

    /**
     * 新增编码规则
     * @param encondRuleDto 编码规则
     * @return Result
     */
    @ApiOperation(value = "新增编码规则", notes = "新增编码规则")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody EncondRuleDto encondRuleDto) {
        encondRuleService.save(encondRuleDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改编码规则
     * @param encondRuleDto 编码规则
     * @return Result
     */
    @ApiOperation(value = "修改编码规则", notes = "修改编码规则")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody EncondRuleDto encondRuleDto) {
        encondRuleService.update(encondRuleDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除编码规则
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除编码规则", notes = "通过id删除编码规则")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        encondRuleService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
