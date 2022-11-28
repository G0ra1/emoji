package com.netwisd.biz.asset.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.constants.DeliveryTypeEnum;
import com.netwisd.biz.asset.constants.DeployTypEnum;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.entity.MaterialDeploy;
import com.netwisd.biz.asset.entity.MaterialDeployDetail;
import com.netwisd.biz.asset.service.AssetsService;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialDeployDto;
import com.netwisd.biz.asset.vo.MaterialDeployVo;
import com.netwisd.biz.asset.service.MaterialDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialDeploy" )
@Api(value = "materialDeploy", tags = "资产调配Controller")
@Slf4j
public class MaterialDeployController {

    private final MaterialDeployService materialDeployService;

    private final AssetsService assetsService;

    /**
     * 分页查询资产调配
     * @param materialDeployDto 资产调配
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public Result<Page<MaterialDeployVo>> page(@RequestBody MaterialDeployDto materialDeployDto) {
        return Result.success(materialDeployService.page(materialDeployDto));
    }

    /**
     * 不分页查询资产调配
     * @param materialDeployDto 资产调配
     * @return
     */
    @ApiOperation(value = "不分页查询", notes = "不分页查询")
    @PostMapping("/list" )
    public Result<List<MaterialDeployVo>> list(@RequestBody MaterialDeployDto materialDeployDto) {
        return Result.success(materialDeployService.list(materialDeployDto));
    }

    /**
     * 通过id查询资产调配
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialDeployVo> get(@PathVariable("id" ) Long id) {
        return Result.success(materialDeployService.get(id));
    }

    /**
     * 新增资产调配
     * @param materialDeployDto 资产调配
     * @return Result
     */
    @ApiOperation(value = "新增资产调配", notes = "新增资产调配")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialDeployDto materialDeployDto) {
        return Result.success(materialDeployService.save(materialDeployDto));
    }

    /**
     * 修改资产调配
     * @param materialDeployDto 资产调配
     * @return Result
     */
    @ApiOperation(value = "修改资产调配", notes = "修改资产调配")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialDeployDto materialDeployDto) {
        return Result.success(materialDeployService.update(materialDeployDto));
    }

    /**
     * 通过id删除资产调配
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产调配", notes = "通过id删除资产调配")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(materialDeployService.delete(id));
    }

    /**
     * 通过流程实例ID查询购置申请
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例ID查询", notes = "通过流程实例ID查询")
    @GetMapping("getByProcId/{procInstId}" )
    public Result<MaterialDeployVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        MaterialDeployVo materialDeployVo = materialDeployService.getByProcId(procInstId);
        log.debug("查询成功");
        return Result.success(materialDeployVo);
    }

    /**
     * 流程新增或修改
     * @param materialDeployDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<MaterialDeployVo> procSaveOrUpdate(@RequestBody MaterialDeployDto materialDeployDto) {
        return Result.success( materialDeployService.procSaveOrUpdate(materialDeployDto));
    }

    /**
     * 获取待调配物资信息
     * @param assetsDto assetsDto
     * @return
     */
    @ApiOperation(value = "获取待调配物资信息", notes = "获取待调配情信息")
    @PostMapping("/getDetailByAssets")
    public Result<Page> getDetailByAssets(@RequestBody AssetsDto assetsDto) {
        Page pageVo =  materialDeployService.getDetailByAssets(assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

//    DeployTypEnum

    /**
     * 获取调配类型
     * @param type
     * @return Result
     */
    @ApiOperation(value = "获取调配类型", notes = "获取调配类型")
    @GetMapping("/getType")
    public Result<List> getType(@RequestParam(value = "type",required = false) Integer type) {
        List<Dict> dictList = new ArrayList<>();
        for (DeployTypEnum value : DeployTypEnum.values()) {
            if (ObjectUtil.isEmpty(type) || value.getCode() == type ) {
                Dict dict = new Dict();
                dict.set("dictItemCode",value.getCode());
                dict.set("dictItemName",value.getName());
                dictList.add(dict);
            }
        }
        return Result.success(dictList);
    }
}