package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaintainPlanDto;
import com.netwisd.biz.asset.vo.MaintainPlanVo;
import com.netwisd.biz.asset.service.MaintainPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 维修计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/maintainPlan" )
@Api(value = "maintainPlan", tags = "维修计划Controller")
@Slf4j
public class MaintainPlanController {

    private final  MaintainPlanService maintainPlanService;

    /**
     * 分页查询维修计划
     * 没有使用参数注解，就是默认从form请求的方式
     * @param maintainPlanDto 维修计划
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaintainPlanDto maintainPlanDto) {
        Page pageVo = maintainPlanService.list(maintainPlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询维修计划
     * @param maintainPlanDto 维修计划
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaintainPlanDto maintainPlanDto) {
        Page pageVo = maintainPlanService.lists(maintainPlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询维修计划
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaintainPlanVo> get(@PathVariable("id" ) Long id) {
        MaintainPlanVo maintainPlanVo = maintainPlanService.get(id);
        log.debug("查询成功");
        return Result.success(maintainPlanVo);
    }

    /**
     * 新增维修计划
     * @param maintainPlanDto 维修计划
     * @return Result
     */
    @ApiOperation(value = "新增维修计划", notes = "新增维修计划")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaintainPlanDto maintainPlanDto) {
        maintainPlanService.save(maintainPlanDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改维修计划
     * @param maintainPlanDto 维修计划
     * @return Result
     */
    @ApiOperation(value = "修改维修计划", notes = "修改维修计划")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaintainPlanDto maintainPlanDto) {
        maintainPlanService.update(maintainPlanDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除维修计划
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除维修计划", notes = "通过id删除维修计划")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        maintainPlanService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 通过流程实例id查询资产维修计划
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询",notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<MaintainPlanVo> getByProc(@PathVariable("procInstId") String procInstId) {
       MaintainPlanVo maintainPlanVo = maintainPlanService.getByProc(procInstId);
       log.debug("查询成功");
       return Result.success(maintainPlanVo);
    }

}
