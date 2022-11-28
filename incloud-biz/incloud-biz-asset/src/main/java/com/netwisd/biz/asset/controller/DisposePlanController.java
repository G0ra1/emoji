package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.DisposePlanDto;
import com.netwisd.biz.asset.vo.DisposePlanVo;
import com.netwisd.biz.asset.service.DisposePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 处置计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/disposePlan" )
@Api(value = "disposePlan", tags = "处置计划Controller")
@Slf4j
public class DisposePlanController {

    private final  DisposePlanService disposePlanService;

    /**
     * 分页查询处置计划
     * 没有使用参数注解，就是默认从form请求的方式
     * @param disposePlanDto 处置计划
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody DisposePlanDto disposePlanDto) {
        Page pageVo = disposePlanService.list(disposePlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询处置计划
     * @param disposePlanDto 处置计划
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody DisposePlanDto disposePlanDto) {
        Page pageVo = disposePlanService.lists(disposePlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询处置计划
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<DisposePlanVo> get(@PathVariable("id" ) Long id) {
        DisposePlanVo disposePlanVo = disposePlanService.get(id);
        log.debug("查询成功");
        return Result.success(disposePlanVo);
    }

    /**
     * 新增处置计划
     * @param disposePlanDto 处置计划
     * @return Result
     */
    @ApiOperation(value = "新增处置计划", notes = "新增处置计划")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody DisposePlanDto disposePlanDto) {
        disposePlanService.save(disposePlanDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改处置计划
     * @param disposePlanDto 处置计划
     * @return Result
     */
    @ApiOperation(value = "修改处置计划", notes = "修改处置计划")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody DisposePlanDto disposePlanDto) {
        disposePlanService.update(disposePlanDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除处置计划
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除处置计划", notes = "通过id删除处置计划")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        disposePlanService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
