package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.SuppliesDto;
import com.netwisd.biz.asset.vo.SuppliesVo;
import com.netwisd.biz.asset.service.SuppliesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 耗材库存台账 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/supplies" )
@Api(value = "supplies", tags = "耗材库存台账Controller")
@Slf4j
public class SuppliesController {

    private final  SuppliesService suppliesService;

    /**
     * 分页查询耗材库存台账
     * 没有使用参数注解，就是默认从form请求的方式
     * @param suppliesDto 耗材库存台账
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody SuppliesDto suppliesDto) {
        Page pageVo = suppliesService.list(suppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询耗材库存台账
     * @param suppliesDto 耗材库存台账
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody SuppliesDto suppliesDto) {
        Page pageVo = suppliesService.lists(suppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询耗材库存台账
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<SuppliesVo> get(@PathVariable("id" ) Long id) {
        SuppliesVo suppliesVo = suppliesService.get(id);
        log.debug("查询成功");
        return Result.success(suppliesVo);
    }

    /**
     * 新增耗材库存台账
     * @param suppliesDto 耗材库存台账
     * @return Result
     */
    @ApiOperation(value = "新增耗材库存台账", notes = "新增耗材库存台账")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody SuppliesDto suppliesDto) {
        suppliesService.save(suppliesDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改耗材库存台账
     * @param suppliesDto 耗材库存台账
     * @return Result
     */
    @ApiOperation(value = "修改耗材库存台账", notes = "修改耗材库存台账")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody SuppliesDto suppliesDto) {
        suppliesService.update(suppliesDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除耗材库存台账
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除耗材库存台账", notes = "通过id删除耗材库存台账")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        suppliesService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
