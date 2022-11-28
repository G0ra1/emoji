package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDelegationTaskDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.vo.WfDelegationTaskVo;
import com.netwisd.base.wf.service.runtime.WfDelegationTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 我委托的待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:19:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/delegationTask" )
@Api(value = "delegationTask", tags = "我委托的待办Controller")
@Slf4j
public class WfDelegationTaskController {

    private final WfDelegationTaskService wfDelegationTaskService;

    /**
     * 分页查询我委托的待办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDelegationTaskDto 我委托的待办
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL)) @RequestBody WfDelegationTaskDto wfDelegationTaskDto) {
        Page pageVo = wfDelegationTaskService.list(wfDelegationTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询我委托的待办
     * @param wfDelegationTaskDto 我委托的待办
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfDelegationTaskDto wfDelegationTaskDto) {
        Page pageVo = wfDelegationTaskService.lists(wfDelegationTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询我委托的待办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfDelegationTaskVo> get(@PathVariable("id" ) Long id) {
        WfDelegationTaskVo wfDelegationTaskVo = wfDelegationTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfDelegationTaskVo);
    }

    /**
     * 新增我委托的待办
     * @param wfDelegationTaskDto 我委托的待办
     * @return Result
     */
    @ApiOperation(value = "新增我委托的待办", notes = "新增我委托的待办")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfDelegationTaskDto wfDelegationTaskDto) {
        Boolean result = wfDelegationTaskService.save(wfDelegationTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改我委托的待办
     * @param wfDelegationTaskDto 我委托的待办
     * @return Result
     */
    @ApiOperation(value = "修改我委托的待办", notes = "修改我委托的待办")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfDelegationTaskDto wfDelegationTaskDto) {
        Boolean result = wfDelegationTaskService.update(wfDelegationTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除我委托的待办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除我委托的待办", notes = "通过id删除我委托的待办")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfDelegationTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
