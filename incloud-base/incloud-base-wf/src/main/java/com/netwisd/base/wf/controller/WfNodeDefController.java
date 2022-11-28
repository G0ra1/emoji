package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfNodeDefDto;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfnodedef" )
@Api(value = "wfnodedef", tags = "流程定义-节点定义Controller")
@Slf4j
public class WfNodeDefController {

    private final  WfNodeDefService wfNodeDefService;

    /**
     * 分页查询流程定义-节点定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfNodeDefDto 流程定义-节点定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfNodeDefDto wfNodeDefDto) {
        Page pageVo = wfNodeDefService.list(wfNodeDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-节点定义
     * @param wfNodeDefDto 流程定义-节点定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfNodeDefDto wfNodeDefDto) {
        Page pageVo = wfNodeDefService.lists(wfNodeDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-节点定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfNodeDefVo> get(@PathVariable("id" ) Long id) {
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfNodeDefVo);
    }

    /**
     * 新增流程定义-节点定义
     * @param wfNodeDefDto 流程定义-节点定义
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-节点定义", notes = "新增流程定义-节点定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfNodeDefDto wfNodeDefDto) {
        Boolean result = wfNodeDefService.save(wfNodeDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-节点定义
     * @param wfNodeDefDto 流程定义-节点定义
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-节点定义", notes = "修改流程定义-节点定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfNodeDefDto wfNodeDefDto) {
        Boolean result = wfNodeDefService.update(wfNodeDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-节点定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-节点定义", notes = "通过id删除流程定义-节点定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfNodeDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
