package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfSendNotifyTaskDto;
import com.netwisd.base.wf.vo.WfSendNotifyTaskVo;
import com.netwisd.base.wf.service.WfSendNotifyTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 我发出的知会 功能描述...
 * @author 云数网讯 XHK@netwisd.com
 * @date 2022-03-09 10:06:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfSendNotifyTask" )
@Api(value = "wfSendNotifyTask", tags = "我发出的知会Controller")
@Slf4j
public class WfSendNotifyTaskController {

    private final  WfSendNotifyTaskService wfSendNotifyTaskService;

    /**
     * 分页查询我发出的知会
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfSendNotifyTaskDto 我发出的知会
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Page pageVo = wfSendNotifyTaskService.list(wfSendNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我发出的知会 -管理员
     * @param wfSendNotifyTaskDto 我发出的知会
     * @return
     */
    @ApiOperation(value = "分页查询我发出的知会 -管理员", notes = "分页查询我发出的知会 -管理员")
    @PostMapping("/sendNotifyListForAd")
    public Result<Page> sendNotifyTaskForAd(@RequestBody WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Page pageVo = wfSendNotifyTaskService.sendNotifyListForAd(wfSendNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询我发出的知会
     * @param wfSendNotifyTaskDto 我发出的知会
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Page pageVo = wfSendNotifyTaskService.lists(wfSendNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询我发出的知会
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfSendNotifyTaskVo> get(@PathVariable("id" ) Long id) {
        WfSendNotifyTaskVo wfSendNotifyTaskVo = wfSendNotifyTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfSendNotifyTaskVo);
    }

    /**
     * 新增我发出的知会
     * @param wfSendNotifyTaskDto 我发出的知会
     * @return Result
     */
    @ApiOperation(value = "新增我发出的知会", notes = "新增我发出的知会")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Boolean result = wfSendNotifyTaskService.save(wfSendNotifyTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改我发出的知会
     * @param wfSendNotifyTaskDto 我发出的知会
     * @return Result
     */
    @ApiOperation(value = "修改我发出的知会", notes = "修改我发出的知会")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Boolean result = wfSendNotifyTaskService.update(wfSendNotifyTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除我发出的知会
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除我发出的知会", notes = "通过id删除我发出的知会")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfSendNotifyTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
