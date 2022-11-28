package com.netwisd.biz.mdm.controller;

import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.dto.ItemSkuDto;
import com.netwisd.biz.mdm.service.ItemSkuService;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Description 物资分类Sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-26 15:14:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/itemSku" )
@Api(value = "itemSku", tags = "物资分类Sku Controller")
@Slf4j
public class ItemSkuController {

    private final ItemSkuService itemSkuService;

    /**
     * 通过id查询物资分类
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ItemSkuVo> get(@PathVariable("id" ) Long id) {
        ItemSkuVo ItemSkuVo = itemSkuService.get(id);
        log.debug("查询成功");
        return Result.success(ItemSkuVo);
    }

    /**
     * 新增物资分类
     * @param itemSkuDto 物资分类
     * @return Result
     */
    @ApiOperation(value = "新增物资分类", notes = "新增物资分类")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ItemSkuDto itemSkuDto) {
        Boolean result = itemSkuService.save(itemSkuDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改物资
     * @param itemSkuDto 物资分类
     * @return Result
     */
    @ApiOperation(value = "修改物资分类", notes = "修改物资分类")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ItemSkuDto itemSkuDto) {
        Boolean result = itemSkuService.update(itemSkuDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除物资
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资分类", notes = "通过id删除物资分类")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = itemSkuService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id查询物资分类sku
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询物资分类sku", notes = "通过id查询物资分类sku")
    @GetMapping("getByClassifyId/{id}" )
    public Result<List<ItemSkuVo>> getByClassifyId(@PathVariable("id" ) Long id) {
        List<ItemSkuVo> skuVoList= itemSkuService.getByClassifyId(id);
        log.debug("查询成功");
        return Result.success(skuVoList);
    }

    /**
     * 批量保存sku
     * @param itemClassifyDto 物资分类
     * @return Result
     */
    @ApiOperation(value = "批量保存sku", notes = "批量保存sku")
    @PutMapping("/saveOrUpdateBatch}")
    public Result<Boolean> saveOrUpdateBatch(@Validation @RequestBody ItemClassifyDto itemClassifyDto) {
        Boolean result = itemSkuService.saveOrUpdateBatch(itemClassifyDto);
        log.debug("批量保存sku成功");
        return Result.success(result);
    }
}
