package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfMyInDuplicateTaskDto;
import com.netwisd.base.wf.service.runtime.WfMyInDuplicateTaskService;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 接收的传阅任务Controller
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 09:39:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/myinduplicatetask" )
@Api(value = "wfduplicatetask", tags = "接收的传阅任务Controller")
@Slf4j
public class WfMyInDuplicateTaskController {

    private final WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    /**
     * 分页查询我接收的传阅任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfMyInDuplicateTaskDto 传阅任务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        Page pageVo = wfMyInDuplicateTaskService.list(wfMyInDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我接收的传阅任务 -管理员
     * @param wfMyInDuplicateTaskDto 传阅任务
     * @return
     */
    @ApiOperation(value = "分页查询我接收的传阅任务 -管理员", notes = "分页查询我接收的传阅任务 -管理员")
    @PostMapping("/inDuplicateListForAd" )
    public Result<Page> inDuplicateListForAd(@RequestBody WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        Page pageVo = wfMyInDuplicateTaskService.inDuplicateListForAd(wfMyInDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    @GetMapping("/one/{camundaTaskId}" )
    public Result<List> one(@PathVariable("camundaTaskId") String camundaTaskId) {
        List list = wfMyInDuplicateTaskService.getList(camundaTaskId);
        return Result.success(list);
    }

    /**
     * 通过id查询我我接收的传阅任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfMyInDuplicateTaskVo> get(@PathVariable("id" ) Long id) {
        WfMyInDuplicateTaskVo wfMyInDuplicateTaskVo = wfMyInDuplicateTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfMyInDuplicateTaskVo);
    }

    /**
     * 修改接收的传阅任务
     * @param wfMyInDuplicateTaskDto 传阅任务
     * @return Result
     */
    @ApiOperation(value = "修改接收的传阅任务", notes = "修改接收的传阅任务")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        Boolean result = wfMyInDuplicateTaskService.update(wfMyInDuplicateTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除传阅任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除我接收的传阅任务", notes = "通过id删除接收的传阅任务")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfMyInDuplicateTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
