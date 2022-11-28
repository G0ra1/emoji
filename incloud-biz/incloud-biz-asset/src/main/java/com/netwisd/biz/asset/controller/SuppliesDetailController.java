package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.vo.SuppliesDetailVo;
import com.netwisd.biz.asset.service.SuppliesDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 耗材库存明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/suppliesDetail" )
@Api(value = "suppliesDetail", tags = "耗材库存明细Controller")
@Slf4j
public class SuppliesDetailController {

    private final  SuppliesDetailService suppliesDetailService;

    /**
     * 分页查询耗材库存明细
     * 没有使用参数注解，就是默认从form请求的方式
     * @param suppliesDetailDto 耗材库存明细
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody SuppliesDetailDto suppliesDetailDto) {
        Page pageVo = suppliesDetailService.list(suppliesDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询耗材库存明细
     * @param suppliesDetailDto 耗材库存明细
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody SuppliesDetailDto suppliesDetailDto) {
        Page pageVo = suppliesDetailService.lists(suppliesDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询耗材库存明细
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<SuppliesDetailVo> get(@PathVariable("id" ) Long id) {
        SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(id);
        log.debug("查询成功");
        return Result.success(suppliesDetailVo);
    }

    /**
     * 新增耗材库存明细
     * @param suppliesDetailDto 耗材库存明细
     * @return Result
     */
    @ApiOperation(value = "新增耗材库存明细", notes = "新增耗材库存明细")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody SuppliesDetailDto suppliesDetailDto) {
        suppliesDetailService.save(suppliesDetailDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改耗材库存明细
     * @param suppliesDetailDto 耗材库存明细
     * @return Result
     */
    @ApiOperation(value = "修改耗材库存明细", notes = "修改耗材库存明细")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody SuppliesDetailDto suppliesDetailDto) {
        suppliesDetailService.update(suppliesDetailDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除耗材库存明细
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除耗材库存明细", notes = "通过id删除耗材库存明细")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        suppliesDetailService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
