package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfTodoTaskDto;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wftodotask" )
@Api(value = "wftodotask", tags = "待办任务Controller")
@Slf4j
public class WfTodoTaskController {

    private final  WfTodoTaskService wfTodoTaskService;

    /**
     * 分页查询待办任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfTodoTaskDto 待办任务
     * @return
     */
    @ApiOperation(value = "分页查询待办任务", notes = "分页查询待办任务")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfTodoTaskDto wfTodoTaskDto) {
        Page pageVo = wfTodoTaskService.list(wfTodoTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询草稿任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfTodoTaskDto 待办任务
     * @return
     */
    @ApiOperation(value = "分页查询草稿任务", notes = "分页查询草稿任务")
    @PostMapping("/draftList" )
    public Result<Page> draftList(@RequestBody WfTodoTaskDto wfTodoTaskDto) {
        Page pageVo = wfTodoTaskService.draftList(wfTodoTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }


    /**
     * 通过id查询待办任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfTodoTaskVo> get(@PathVariable("id" ) Long id) {
        WfTodoTaskVo wfTodoTaskVo = wfTodoTaskService.get(id);
        log.debug("查询成功");
        return Result.success(wfTodoTaskVo);
    }

    /**
     * 通过id删除待办任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除待办任务", notes = "通过id删除待办任务")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfTodoTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    @ApiOperation(value = "根据身份证号查询待办数量", notes = "根据身份证号查询待办数量")
    @GetMapping("/queryTodoTaskNum/{idCard}" )
    public Result queryTodoTaskNum(@PathVariable("idCard" ) String idCard) {

        return wfTodoTaskService.queryTodoTaskNum(idCard);
    }

    @ApiOperation(value = "根据身份证号 以及路程定义id查询 待办数据量", notes = "根据身份证号 以及路程定义id查询 待办数据量")
    @GetMapping("/queryTodoTaskNumByProcDefId/{idCard}/{camundaProcdefId}" )
    public Result queryTodoTaskNumByProcDefId(@PathVariable("idCard" ) String idCard,@PathVariable("camundaProcdefId") String camundaProcdefId) {

        return wfTodoTaskService.queryTodoTaskNumByCmdProcDefId(idCard,camundaProcdefId);
    }

    /**
     * 管理员查询所有草稿台账
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfTodoTaskDto 草稿任务
     * @return
     */
    @ApiOperation(value = "管理员查询所有草稿台账", notes = "管理员查询所有草稿台账")
    @PostMapping("/draftListForAd" )
    public Result<Page> draftListForAd(@RequestBody WfTodoTaskDto wfTodoTaskDto) {
        Page pageVo = wfTodoTaskService.draftListForAd(wfTodoTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 管理员分页查询待办任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfTodoTaskDto 待办任务
     * @return
     */
    @ApiOperation(value = "管理员分页查询待办任务", notes = "管理员分页查询待办任务")
    @PostMapping("/todoListForAd" )
    public Result<Page> todoListForAd(@RequestBody WfTodoTaskDto wfTodoTaskDto) {
        Page pageVo = wfTodoTaskService.todoListForAd(wfTodoTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 直接修改待办人
     * 没有使用参数注解，就是默认从form请求的方式
     * @return
     */
    @ApiOperation(value = "分页查询草稿任务", notes = "分页查询草稿任务")
    @PostMapping("/updateAssigneeByTodoId/{id}/{assignees}" )
    public Result updateAssigneeByTodoId(@PathVariable("id") String id,@PathVariable("assignees")String assignees) {
        return Result.success(wfTodoTaskService.updateAssigneeByTodoId(id, assignees));
    }

    @ApiOperation(value = "根据当前登录人以及流程定义key获取待办数量", notes = "根据当前登录人以及流程定义key获取待办数量")
    @GetMapping("/queryTodokNumByCondition/{camundaProcdefKey}" )
    public Result queryTodokNumByCondition(@PathVariable("camundaProcdefKey" ) String camundaProcdefKey) {

        return wfTodoTaskService.queryTodokNumByCondition(camundaProcdefKey);
    }

}
