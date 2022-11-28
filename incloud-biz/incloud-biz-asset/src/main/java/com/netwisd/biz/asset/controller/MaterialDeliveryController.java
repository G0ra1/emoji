package com.netwisd.biz.asset.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.DeliveryTypeEnum;
import com.netwisd.biz.asset.constants.DeployTypEnum;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.dto.MaterialDeployDto;
import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.biz.asset.service.AssetsService;
import com.netwisd.biz.asset.service.MaterialDeployService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialDeliveryDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryVo;
import com.netwisd.biz.asset.service.MaterialDeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 资产出库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialDelivery" )
@Api(value = "materialDelivery", tags = "资产出库管理Controller")
@Slf4j
public class MaterialDeliveryController {

    private final  MaterialDeliveryService materialDeliveryService;

    private final AssetsService assetsService;

    private final MaterialDeployService materialDeployService;

    /**
     * 分页查询资产出库管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialDeliveryDto 资产出库管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialDeliveryDto materialDeliveryDto) {
        Page pageVo = materialDeliveryService.list(materialDeliveryDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产出库管理
     * @param materialDeliveryDto 资产出库管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialDeliveryDto materialDeliveryDto) {
        Page pageVo = materialDeliveryService.lists(materialDeliveryDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产出库管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialDeliveryVo> get(@PathVariable("id" ) Long id) {
        MaterialDeliveryVo materialDeliveryVo = materialDeliveryService.get(id);
        log.debug("查询成功");
        return Result.success(materialDeliveryVo);
    }

    /**
     * 新增资产出库管理
     * @param materialDeliveryDto 资产出库管理
     * @return Result
     */
    @ApiOperation(value = "新增资产出库管理", notes = "新增资产出库管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialDeliveryDto materialDeliveryDto) {
        materialDeliveryService.save(materialDeliveryDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产出库管理
     * @param materialDeliveryDto 资产出库管理
     * @return Result
     */
    @ApiOperation(value = "修改资产出库管理", notes = "修改资产出库管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialDeliveryDto materialDeliveryDto) {
        materialDeliveryService.update(materialDeliveryDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产出库管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产出库管理", notes = "通过id删除资产出库管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialDeliveryService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 获取出库类型
     * @param type
     * @return Result
     */
    @ApiOperation(value = "获取出库类型", notes = "获取出库类型")
    @GetMapping("/getType" )
    public Result<List> getType(@RequestParam(value = "type",required = false) Integer type) {
        List<Dict> dictList = new ArrayList<>();
        for (DeliveryTypeEnum value : DeliveryTypeEnum.values()) {
            if (ObjectUtil.isEmpty(type) || value.getCode() == type ) {
                Dict dict = new Dict();
                dict.set("dictItemCode",value.getCode());
                dict.set("dictItemName",value.getName());
                dictList.add(dict);
            }
        }
        return Result.success(dictList);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialDeliveryService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通过id查询资产入库管理
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}" )
    public Result<MaterialDeliveryVo> getByProc(@PathVariable("procInstId" ) String procInstId) {
        MaterialDeliveryVo materialDeliveryVo = materialDeliveryService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialDeliveryVo);
    }

    /**
     * 获取待出库物资信息
     * @param assetsDto assetsDto
     * @return
     */
    @ApiOperation(value = "获取待出库物资信息", notes = "获取待出库情信息")
    @PostMapping("/getDetailByAssets")
    public Result<Page> getDetailByAssets(@RequestBody AssetsDto assetsDto) {
        Page pageVo =  assetsService.getDetailByAssets(assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过申请单，获取待出库物资信息
     * @param materialDeployDto
     * @return
     */
    @ApiOperation(value = "获取待出库物资信息", notes = "获取待出库情信息")
    @PostMapping("/getOrderList")
    public Result<Page> getOrderList(@RequestBody MaterialDeployDto materialDeployDto) {
        Page pageVo = materialDeployService.listForDelivery(materialDeployDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success();
    }

    /**
     * 通过申请单，获取待出库物资信息
     * @param materialDeployResultDto
     * @return
     */
    @ApiOperation(value = "获取待出库物资信息", notes = "获取待出库情信息")
    @PostMapping("/getDetailByOrder")
    public Result<Page> getDetailByOrder(@RequestBody MaterialDeployResultDto materialDeployResultDto) {
        Page pageVo =  materialDeployService.getResultByDeploy(materialDeployResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success();
    }


}
