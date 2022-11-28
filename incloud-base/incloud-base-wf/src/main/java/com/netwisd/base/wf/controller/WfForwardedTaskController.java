package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfForwardedTaskDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.vo.WfForwardedTaskVo;
import com.netwisd.base.wf.service.runtime.WfForwardedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 我发出的转办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:43:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/forwardedTask" )
@Api(value = "forwardedTask", tags = "我发出的转办Controller")
@Slf4j
public class WfForwardedTaskController {

    private final WfForwardedTaskService wfForwardedTaskService;

    /**
     * 分页查询我发出的转办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfForwardedTaskDto 我发出的转办
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL)) @RequestBody WfForwardedTaskDto wfForwardedTaskDto) {
        Page pageVo = wfForwardedTaskService.list(wfForwardedTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我发出的转办 -管理员
     * @param wfForwardedTaskDto 我发出的转办
     * @return
     */
    @ApiOperation(value = "分页查询我发出的转办 -管理员", notes = "分页查询我发出的转办 -管理员")
    @PostMapping("/forwardedListForAd" )
    public Result<Page> forwardedListForAd(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL)) @RequestBody WfForwardedTaskDto wfForwardedTaskDto) {
        Page pageVo = wfForwardedTaskService.list(wfForwardedTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询我发出的转办
     * @param wfForwardedTaskDto 我发出的转办
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfForwardedTaskDto wfForwardedTaskDto) {
        Page pageVo = wfForwardedTaskService.lists(wfForwardedTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询我发出的转办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfForwardedTaskVo> get(@PathVariable("id" ) Long id) {
        WfForwardedTaskVo wfForwardedTaskVo = wfForwardedTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfForwardedTaskVo);
    }

    /**
     * 新增我发出的转办
     * @param wfForwardedTaskDto 我发出的转办
     * @return Result
     */
    @ApiOperation(value = "新增我发出的转办", notes = "新增我发出的转办")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfForwardedTaskDto wfForwardedTaskDto) {
        Boolean result = wfForwardedTaskService.save(wfForwardedTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改我发出的转办
     * @param wfForwardedTaskDto 我发出的转办
     * @return Result
     */
    @ApiOperation(value = "修改我发出的转办", notes = "修改我发出的转办")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfForwardedTaskDto wfForwardedTaskDto) {
        Boolean result = wfForwardedTaskService.update(wfForwardedTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除我发出的转办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除我发出的转办", notes = "通过id删除我发出的转办")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfForwardedTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
