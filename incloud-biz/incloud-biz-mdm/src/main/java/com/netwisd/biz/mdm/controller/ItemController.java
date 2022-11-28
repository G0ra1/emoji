package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.service.ItemSkuLineService;
import com.netwisd.biz.mdm.service.ItemUnitService;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ItemDto;
import com.netwisd.biz.mdm.vo.ItemVo;
import com.netwisd.biz.mdm.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/item" )
@Api(value = "item", tags = "物资Controller")
@Slf4j
public class ItemController {

    private final  ItemService itemService;
    private final MdmMqService mdmMqService;
    private final ItemSkuLineService lineService;
    private final ItemUnitService unitService;

    /**
     * 分页查询物资
     * 没有使用参数注解，就是默认从form请求的方式
     * @param itemDto 物资
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ItemDto itemDto) {
        Page pageVo = itemService.list(itemDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ItemVo> get(@PathVariable("id" ) Long id) {
        ItemVo itemVo = itemService.get(id);
        log.debug("查询成功");
        return Result.success(itemVo);
    }

    /**
     * 新增物资
     * @param itemDto 物资
     * @return Result
     */
    @ApiOperation(value = "新增物资", notes = "新增物资")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ItemDto itemDto) {
        Boolean result = itemService.save(itemDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改物资
     * @param itemDto 物资
     * @return Result
     */
    @ApiOperation(value = "修改物资", notes = "修改物资")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ItemDto itemDto) {
        Boolean result = itemService.update(itemDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除物资
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资", notes = "通过id删除物资")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = itemService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 全量数据推送mq
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList" )
    public Result<String> outList(ItemDto itemDto){
        mdmMqService.syncMqForItem(itemDto);
        return Result.success("全量数据推送成功");
    }

    /**
     * 批量校验
     * @return
     */
    @ApiOperation(value = "批量校验", notes = "批量校验")
    @PostMapping("/checkItem" )
    public void checkItem(){
        itemService.checkItem();
    }

    /**
     * 通过id删除sku行
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除sku行", notes = "通过id删除sku行")
    @DeleteMapping("deleteLine/{id}" )
    public Result<Boolean> deleteLine(@PathVariable Long id) {
        Boolean result = lineService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id删除辅计量
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除辅计量", notes = "通过id删除辅计量")
    @DeleteMapping("deleteUnit/{id}" )
    public Result<Boolean> deleteUnit(@PathVariable Long id) {
        Boolean result = unitService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
}
