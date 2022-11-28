package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfDoneTaskDto;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 已办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfdonetask" )
@Api(value = "wfdonetask", tags = "已办任务Controller")
@Slf4j
public class WfDoneTaskController {

    private final  WfDoneTaskService wfDoneTaskService;

    /**
     * 分页查询已办任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDoneTaskDto 已办任务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.list(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 管理员分页查询已办任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDoneTaskDto 已办任务
     * @return
     */
    @ApiOperation(value = "管理员分页查询已办任务", notes = "管理员分页查询已办任务")
    @PostMapping("/doneListForAd" )
    public Result<Page> doneListForAd(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.doneListForAd(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询已办任务
     * @param wfDoneTaskDto 已办任务
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.lists(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询已办任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfDoneTaskVo> get(@PathVariable("id" ) Long id) {
        WfDoneTaskVo wfDoneTaskVo = wfDoneTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfDoneTaskVo);
    }

    /**
     * 新增已办任务
     * @param wfDoneTaskDto 已办任务
     * @return Result
     */
    @ApiOperation(value = "新增已办任务", notes = "新增已办任务")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Boolean result = wfDoneTaskService.save(wfDoneTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改已办任务
     * @param wfDoneTaskDto 已办任务
     * @return Result
     */
    @ApiOperation(value = "修改已办任务", notes = "修改已办任务")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Boolean result = wfDoneTaskService.update(wfDoneTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除已办任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除已办任务", notes = "通过id删除已办任务")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfDoneTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 我发起的流程台账
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDoneTaskDto 已办任务
     * @return
     */
    @ApiOperation(value = "我发起的流程台账", notes = "我发起的流程台账")
    @PostMapping("/myDraftList" )
    public Result<Page> myDraftList(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.myDraftList(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 我发起的流程台账 -管理员
     * @param wfDoneTaskDto 已办任务 -管理员
     * @return
     */
    @ApiOperation(value = "我发起的流程台账-管理员", notes = "我发起的流程台账-管理员")
    @PostMapping("/myDraftListForAd" )
    public Result<Page> myDraftListForAd(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.myDraftListForAd(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

}
