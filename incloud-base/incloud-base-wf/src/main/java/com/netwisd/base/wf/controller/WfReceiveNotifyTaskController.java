package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfReceiveNotifyTaskVo;
import com.netwisd.base.wf.dto.WfForwardedTaskDto;
import com.netwisd.base.wf.dto.WfReceiveNotifyTaskDto;
import com.netwisd.base.wf.service.runtime.WfReceiveNotifyTaskService;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 我收到的知会
 * @author 云数网讯 XHL
 * @date 2022-02-14 09:39:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/receiveNotifyTask" )
@Api(value = "receiveNotifyTask", tags = "我收到的知会Controller")
@Slf4j
public class WfReceiveNotifyTaskController {

    private final WfReceiveNotifyTaskService wfReceiveNotifyTaskService;

    /**
     * 分页查询我收到的知会
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfReceiveNotifyTaskDto 我收到的知会任务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL)) @RequestBody WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        Page pageVo = wfReceiveNotifyTaskService.list(wfReceiveNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我收到的知会 -管理员
     * @param wfReceiveNotifyTaskDto 我收到的知会任务
     * @return
     */
    @ApiOperation(value = "分页查询我收到的知会 -管理员", notes = "分页查询我收到的知会 -管理员")
    @PostMapping("/receiveNotifyListForAd" )
    public Result<Page> receiveNotifyListForAd(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL)) @RequestBody WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        Page pageVo = wfReceiveNotifyTaskService.receiveNotifyListForAd(wfReceiveNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }


    /**
     * 根据任务id 获取所有的知会任务
     * @param camundaTaskId
     * @return
     */
    @GetMapping("/{camundaTaskId}" )
    public Result<List> getBytaskId(@PathVariable("camundaTaskId") String camundaTaskId) {
        List list = wfReceiveNotifyTaskService.getList(camundaTaskId);
        return Result.success(list);
    }

    /**
     * 通过id查询我我接收的知会任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfReceiveNotifyTaskVo> get(@PathVariable("id" ) Long id) {
        WfReceiveNotifyTaskVo wfReceiveNotifyTaskVo = wfReceiveNotifyTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfReceiveNotifyTaskVo);
    }

    /**
     * 修改接收的知会任务
     * @param wfReceiveNotifyTaskDto 知会任务
     * @return Result
     */
    @ApiOperation(value = "修改接收的知会任务", notes = "修改接收的知会任务")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        Boolean result = wfReceiveNotifyTaskService.update(wfReceiveNotifyTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除知会
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除知会", notes = "通过id删除知会")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfReceiveNotifyTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 新增我收到的知会
     * @param wfReceiveNotifyTaskDto 新增我收到的知会
     * @return Result
     */
    @ApiOperation(value = "新增我收到的知会", notes = "新增我收到的知会")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        Boolean result = wfReceiveNotifyTaskService.save(wfReceiveNotifyTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

}
