package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ShelfDto;
import com.netwisd.biz.asset.entity.Warehouse;
import com.netwisd.biz.asset.service.ShelfService;
import com.netwisd.biz.asset.vo.ShelfVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.WarehouseDto;
import com.netwisd.biz.asset.vo.WarehouseVo;
import com.netwisd.biz.asset.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/warehouse" )
@Api(value = "warehouse", tags = "仓库Controller")
@Slf4j
public class WarehouseController {

    private final  WarehouseService warehouseService;
    private final ShelfService shelfService;

    /**
     * 分页查询仓库
     * 没有使用参数注解，就是默认从form请求的方式
     * @param warehouseDto 仓库
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WarehouseDto warehouseDto) {
        Page pageVo = warehouseService.list(warehouseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页自定义查询仓库
     * @param warehouseDto 仓库
     * @return
     */
    @ApiOperation(value = "不分页自定义查询", notes = "不分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<WarehouseVo>> lists(@RequestBody WarehouseDto warehouseDto) {
        List<WarehouseVo> list = warehouseService.lists(warehouseDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过id查询仓库
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WarehouseVo> get(@PathVariable("id" ) Long id) {
        WarehouseVo warehouseVo = warehouseService.get(id);
        log.debug("查询成功");
        return Result.success(warehouseVo);
    }

    /**
     * 新增仓库
     * @param warehouseDto 仓库
     * @return Result
     */
    @ApiOperation(value = "新增仓库", notes = "新增仓库")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WarehouseDto warehouseDto) {
        Boolean result = warehouseService.save(warehouseDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改仓库
     * @param warehouseDto 仓库
     * @return Result
     */
    @ApiOperation(value = "修改仓库", notes = "修改仓库")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WarehouseDto warehouseDto) {
        Boolean result = warehouseService.update(warehouseDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除仓库
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除仓库", notes = "通过id删除仓库")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = warehouseService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 批量保存货架号
     * @param warehouseDto 仓库
     * @return
     */
    @ApiOperation(value = "批量保存货架号", notes = "批量保存货架号")
    @PostMapping("/saveShelfs" )
    public Result<Boolean> saveShelfs(@RequestBody WarehouseDto warehouseDto) {
        Boolean result = shelfService.saveList(warehouseDto.getShelfList());
        log.debug("批量新增货架号成功");
        return Result.success(result);
    }

    /**
     * 通过id删除货架号
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除货架号", notes = "通过id删除货架号")
    @DeleteMapping("deleteShelf/{id}" )
    public Result<Boolean> deleteShelf(@PathVariable Long id) {
        Boolean result = shelfService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 批量编辑货架号
     * @param list 货架号
     * @return
     */
   /* @ApiOperation(value = "批量编辑货架号", notes = "批量编辑货架号")
    @PostMapping("/updateShelfs" )
    public Result<Boolean> updateShelfs(@RequestBody WarehouseDto warehouseDto) {
        Boolean result = shelfService.saveList(list);
        log.debug("批量新增货架号成功");
        return Result.success(result);
    }*/
    /**
     * 不分页自定义查询货架号
     * @param shelfDto 仓库
     * @return
     */
    @ApiOperation(value = "不分页自定义查询货架号", notes = "不分页自定义查询货架号")
    @PostMapping("/shelf/lists" )
    public Result<List<ShelfVo>> shelfLists(@RequestBody ShelfDto shelfDto) {
        List<ShelfVo> list = shelfService.lists(shelfDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }
    /**
     * 分页查询货架号
     * @param shelfDto 仓库
     * @return
     */
    @ApiOperation(value = "分页查询货架号", notes = "分页查询货架号")
    @PostMapping("/shelf/list" )
    public Result<Page> shelfList(@RequestBody ShelfDto shelfDto) {
        Page page = shelfService.list(shelfDto);
        log.debug("查询条数:"+page.getTotal());
        return Result.success(page);
    }
}
