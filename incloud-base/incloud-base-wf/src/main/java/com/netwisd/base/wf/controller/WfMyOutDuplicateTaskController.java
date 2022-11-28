package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfMyOutDuplicateTaskDto;
import com.netwisd.base.common.vo.wf.WfMyOutDuplicateTaskVo;
import com.netwisd.base.wf.service.runtime.WfMyOutDuplicateTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 我发起的传阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 16:16:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfmyoutduplicatetask" )
@Api(value = "wfmyoutduplicatetask", tags = "我发起的传阅Controller")
@Slf4j
public class WfMyOutDuplicateTaskController {

    private final  WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    /**
     * 分页查询我发起的传阅
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfMyOutDuplicateTaskDto 我发起的传阅
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Page pageVo = wfMyOutDuplicateTaskService.list(wfMyOutDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我发起的传阅 -管理员
     * @param wfMyOutDuplicateTaskDto 我发起的传阅 -管理员
     * @return
     */
    @ApiOperation(value = "分页查询我发起的传阅 -管理员", notes = "分页查询我发起的传阅 -管理员")
    @PostMapping("/outDuplicateListForAd" )
    public Result<Page> outDuplicateListForAd(@RequestBody WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Page pageVo = wfMyOutDuplicateTaskService.outDuplicateListForAd(wfMyOutDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询我发起的传阅
     * @param wfMyOutDuplicateTaskDto 我发起的传阅
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Page pageVo = wfMyOutDuplicateTaskService.lists(wfMyOutDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询我发起的传阅
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfMyOutDuplicateTaskVo> get(@PathVariable("id" ) Long id) {
        WfMyOutDuplicateTaskVo wfMyOutDuplicateTaskVo = wfMyOutDuplicateTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfMyOutDuplicateTaskVo);
    }

    /**
     * 新增我发起的传阅
     * @param wfMyOutDuplicateTaskDto 我发起的传阅
     * @return Result
     */
    @ApiOperation(value = "新增我发起的传阅", notes = "新增我发起的传阅")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Boolean result = wfMyOutDuplicateTaskService.save(wfMyOutDuplicateTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改我发起的传阅
     * @param wfMyOutDuplicateTaskDto 我发起的传阅
     * @return Result
     */
    @ApiOperation(value = "修改我发起的传阅", notes = "修改我发起的传阅")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Boolean result = wfMyOutDuplicateTaskService.update(wfMyOutDuplicateTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除我发起的传阅
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除我发起的传阅", notes = "通过id删除我发起的传阅")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfMyOutDuplicateTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
}
