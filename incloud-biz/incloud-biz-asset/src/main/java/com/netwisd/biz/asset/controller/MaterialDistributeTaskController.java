package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialDistributeTaskDto;
import com.netwisd.biz.asset.vo.MaterialDistributeTaskVo;
import com.netwisd.biz.asset.service.MaterialDistributeTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialDistributeTask" )
@Api(value = "materialDistributeTask", tags = "资产派发任务表Controller")
@Slf4j
public class MaterialDistributeTaskController {

    private final  MaterialDistributeTaskService materialDistributeTaskService;

    /**
     * 分页查询资产派发任务表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialDistributeTaskDto 资产派发任务表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialDistributeTaskDto materialDistributeTaskDto) {
        Page pageVo = materialDistributeTaskService.list(materialDistributeTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产派发任务表
     * @param materialDistributeTaskDto 资产派发任务表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialDistributeTaskDto materialDistributeTaskDto) {
        Page pageVo = materialDistributeTaskService.lists(materialDistributeTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产派发任务表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialDistributeTaskVo> get(@PathVariable("id" ) Long id) {
        MaterialDistributeTaskVo materialDistributeTaskVo = materialDistributeTaskService.get(id);
        log.debug("查询成功");
        return Result.success(materialDistributeTaskVo);
    }

    /**
     * 新增资产派发任务表
     * @param materialDistributeTaskDto 资产派发任务表
     * @return Result
     */
    @ApiOperation(value = "新增资产派发任务表", notes = "新增资产派发任务表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialDistributeTaskDto materialDistributeTaskDto) {
        Boolean result = materialDistributeTaskService.save(materialDistributeTaskDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改资产派发任务表
     * @param materialDistributeTaskDto 资产派发任务表
     * @return Result
     */
    @ApiOperation(value = "修改资产派发任务表", notes = "修改资产派发任务表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialDistributeTaskDto materialDistributeTaskDto) {
        Boolean result = materialDistributeTaskService.update(materialDistributeTaskDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除资产派发任务表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产派发任务表", notes = "通过id删除资产派发任务表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = materialDistributeTaskService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
