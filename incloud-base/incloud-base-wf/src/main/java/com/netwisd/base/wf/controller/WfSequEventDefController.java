package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfSequEventDefDto;
import com.netwisd.base.wf.vo.WfSequEventDefVo;
import com.netwisd.base.wf.service.procdef.WfSequEventDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfsequeventdef" )
@Api(value = "wfsequeventdef", tags = "流程定义-序列流-事件Controller")
@Slf4j
public class WfSequEventDefController {

    private final  WfSequEventDefService wfSequEventDefService;

    /**
     * 分页查询流程定义-序列流-事件
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfSequEventDefDto 流程定义-序列流-事件
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfSequEventDefDto wfSequEventDefDto) {
        Page pageVo = wfSequEventDefService.list(wfSequEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-序列流-事件
     * @param wfSequEventDefDto 流程定义-序列流-事件
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfSequEventDefDto wfSequEventDefDto) {
        Page pageVo = wfSequEventDefService.lists(wfSequEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-序列流-事件
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfSequEventDefVo> get(@PathVariable("id" ) Long id) {
        WfSequEventDefVo wfSequEventDefVo = wfSequEventDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfSequEventDefVo);
    }

    /**
     * 新增流程定义-序列流-事件
     * @param wfSequEventDefDto 流程定义-序列流-事件
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-序列流-事件", notes = "新增流程定义-序列流-事件")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfSequEventDefDto wfSequEventDefDto) {
        Boolean result = wfSequEventDefService.save(wfSequEventDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-序列流-事件
     * @param wfSequEventDefDto 流程定义-序列流-事件
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-序列流-事件", notes = "修改流程定义-序列流-事件")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfSequEventDefDto wfSequEventDefDto) {
        Boolean result = wfSequEventDefService.update(wfSequEventDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-序列流-事件
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-序列流-事件", notes = "通过id删除流程定义-序列流-事件")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfSequEventDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
