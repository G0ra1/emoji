package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.DaybookSuppliesDto;
import com.netwisd.biz.asset.vo.DaybookSuppliesVo;
import com.netwisd.biz.asset.service.DaybookSuppliesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/daybookSupplies" )
@Api(value = "daybookSupplies", tags = "耗材流水表Controller")
@Slf4j
public class DaybookSuppliesController {

    private final  DaybookSuppliesService daybookSuppliesService;

    /**
     * 分页查询耗材流水表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param daybookSuppliesDto 耗材流水表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody DaybookSuppliesDto daybookSuppliesDto) {
        Page pageVo = daybookSuppliesService.list(daybookSuppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询耗材流水表
     * @param daybookSuppliesDto 耗材流水表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody DaybookSuppliesDto daybookSuppliesDto) {
        Page pageVo = daybookSuppliesService.lists(daybookSuppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询耗材流水表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<DaybookSuppliesVo> get(@PathVariable("id" ) Long id) {
        DaybookSuppliesVo daybookSuppliesVo = daybookSuppliesService.get(id);
        log.debug("查询成功");
        return Result.success(daybookSuppliesVo);
    }

    /**
     * 新增耗材流水表
     * @param daybookSuppliesDto 耗材流水表
     * @return Result
     */
    @ApiOperation(value = "新增耗材流水表", notes = "新增耗材流水表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody DaybookSuppliesDto daybookSuppliesDto) {
        Boolean result = daybookSuppliesService.save(daybookSuppliesDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改耗材流水表
     * @param daybookSuppliesDto 耗材流水表
     * @return Result
     */
    @ApiOperation(value = "修改耗材流水表", notes = "修改耗材流水表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody DaybookSuppliesDto daybookSuppliesDto) {
        Boolean result = daybookSuppliesService.update(daybookSuppliesDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除耗材流水表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除耗材流水表", notes = "通过id删除耗材流水表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = daybookSuppliesService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
