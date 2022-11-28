package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.InventoryDto;
import com.netwisd.biz.asset.vo.InventoryVo;
import com.netwisd.biz.asset.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 物资盘点 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/inventory" )
@Api(value = "inventory", tags = "物资盘点Controller")
@Slf4j
public class InventoryController {

    private final  InventoryService inventoryService;

    private final AssetsDetailService assetsDetailService;

    /**
     * 分页查询物资盘点
     * 没有使用参数注解，就是默认从form请求的方式
     * @param inventoryDto 物资盘点
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody InventoryDto inventoryDto) {
        Page pageVo = inventoryService.list(inventoryDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询物资盘点
     * @param inventoryDto 物资盘点
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody InventoryDto inventoryDto) {
        Page pageVo = inventoryService.lists(inventoryDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资盘点
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<InventoryVo> get(@PathVariable("id" ) Long id) {
        InventoryVo inventoryVo = inventoryService.get(id);
        log.debug("查询成功");
        return Result.success(inventoryVo);
    }

    /**
     * 新增物资盘点
     * @param inventoryDto 物资盘点
     * @return Result
     */
    @ApiOperation(value = "新增物资盘点", notes = "新增物资盘点")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody InventoryDto inventoryDto) {
        inventoryService.save(inventoryDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改物资盘点
     * @param inventoryDto 物资盘点
     * @return Result
     */
    @ApiOperation(value = "修改物资盘点", notes = "修改物资盘点")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody InventoryDto inventoryDto) {
        inventoryService.update(inventoryDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除物资盘点
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资盘点", notes = "通过id删除物资盘点")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 流程新增或修改
     * @param inventoryDto 盘点表
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate" )
    public Result<InventoryVo> procSaveOrUpdate(@RequestBody InventoryDto inventoryDto) {
        InventoryVo inventoryVo = inventoryService.procSaveOrUpdate(inventoryDto);
        return Result.success(inventoryVo);
    }


    /**
     * 流程查看
     * @param procInstId procInstId
     * @return
     */
  @ApiOperation(value = "流程查看", notes = "流程查看")
    @GetMapping("/procDetail")
    public Result<InventoryVo> procDetail(@RequestParam String procInstId){
        InventoryVo inventoryVo = inventoryService.procDetail(procInstId);
        return Result.success(inventoryVo);
    }
    /**
     * 获取需要盘点的资产信息
     * @param
     * @return
     */
    @ApiOperation(value = "获取需要盘点的资产信息",notes = "获取需要盘点的资产信息")
    @RequestMapping(value = "/getAssetsDetail",method = RequestMethod.POST)
    public Result getAssetsDetail(@RequestBody AssetsDetailDto assetsDetailDto){
        Page pageVo = assetsDetailService.getAssetsDetail(assetsDetailDto);
        return Result.success(pageVo);
    }
}
