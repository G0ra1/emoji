package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfProcDefRelDto;
import com.netwisd.base.wf.vo.WfProcDefRelVo;
import com.netwisd.base.wf.service.procdef.WfProcDefRelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfprocdefrel" )
@Api(value = "wfprocdefrel", tags = "流程定义和子流程定义关系表Controller")
@Slf4j
public class WfProcDefRelController {

    private final  WfProcDefRelService wfProcDefRelService;

    /**
     * 分页查询流程定义和子流程定义关系表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfProcDefRelDto 流程定义和子流程定义关系表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfProcDefRelDto wfProcDefRelDto) {
        Page pageVo = wfProcDefRelService.list(wfProcDefRelDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义和子流程定义关系表
     * @param wfProcDefRelDto 流程定义和子流程定义关系表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfProcDefRelDto wfProcDefRelDto) {
        Page pageVo = wfProcDefRelService.lists(wfProcDefRelDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义和子流程定义关系表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfProcDefRelVo> get(@PathVariable("id" ) Long id) {
        WfProcDefRelVo wfProcDefRelVo = wfProcDefRelService.get(id);
        log.debug("查询成功");
        return Result.success(wfProcDefRelVo);
    }

    /**
     * 新增流程定义和子流程定义关系表
     * @param wfProcDefRelDto 流程定义和子流程定义关系表
     * @return Result
     */
    @ApiOperation(value = "新增流程定义和子流程定义关系表", notes = "新增流程定义和子流程定义关系表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfProcDefRelDto wfProcDefRelDto) {
        Boolean result = wfProcDefRelService.save(wfProcDefRelDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义和子流程定义关系表
     * @param wfProcDefRelDto 流程定义和子流程定义关系表
     * @return Result
     */
    @ApiOperation(value = "修改流程定义和子流程定义关系表", notes = "修改流程定义和子流程定义关系表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfProcDefRelDto wfProcDefRelDto) {
        Boolean result = wfProcDefRelService.update(wfProcDefRelDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义和子流程定义关系表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义和子流程定义关系表", notes = "通过id删除流程定义和子流程定义关系表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfProcDefRelService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
