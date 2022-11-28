package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.service.AssetsService;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
import com.netwisd.biz.asset.vo.PurchaseAcceptAssetVo;
import com.netwisd.biz.asset.vo.PurchaseAcceptVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.vo.MaterialAcceptVo;
import com.netwisd.biz.asset.service.MaterialAcceptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialAccept" )
@Api(value = "materialAccept", tags = "资产领用Controller")
@Slf4j
public class MaterialAcceptController {

    private final  MaterialAcceptService materialAcceptService;

    private final AssetsService assetsService;
    /**
     * 分页查询资产领用
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialAcceptDto 资产领用
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialAcceptDto materialAcceptDto) {
        Page pageVo = materialAcceptService.list(materialAcceptDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产领用
     * @param materialAcceptDto 资产领用
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialAcceptDto materialAcceptDto) {
        Page pageVo = materialAcceptService.lists(materialAcceptDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产领用
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialAcceptVo> get(@PathVariable("id" ) Long id) {
        MaterialAcceptVo materialAcceptVo = materialAcceptService.get(id);
        log.debug("查询成功");
        return Result.success(materialAcceptVo);
    }

    /**
     * 新增资产领用
     * @param materialAcceptDto 资产领用
     * @return Result
     */
    @ApiOperation(value = "新增资产领用", notes = "新增资产领用")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialAcceptDto materialAcceptDto) {
       Boolean result = materialAcceptService.save(materialAcceptDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改资产领用
     * @param materialAcceptDto 资产领用
     * @return Result
     */
    @ApiOperation(value = "修改资产领用", notes = "修改资产领用")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialAcceptDto materialAcceptDto) {
        materialAcceptService.update(materialAcceptDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产领用
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产领用", notes = "通过id删除资产领用")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialAcceptService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialAcceptService.deleteByProc(procInstId);
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
    public Result<MaterialAcceptVo> getByProc(@PathVariable("procInstId" ) String procInstId) {
        MaterialAcceptVo materialAcceptVo = materialAcceptService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialAcceptVo);
    }

    @ApiOperation(value = "流程开始", notes = "流程开始")
    @GetMapping("/proc/start/{procInstId}" )
    public Result<MaterialAcceptVo> startProc(@PathVariable("procInstId" ) String procInstId) {
        Result result = materialAcceptService.acceptSaveAfter(procInstId);
        log.debug("查询成功");
        return Result.success(result);
    }
    /**
     * 流程资产领用保存前，修改可用数量
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "资产领用保存前",notes = "资产领用保存前")
    @GetMapping("/acceptSaveBefore/{procInstId}")
    public Result acceptSaveBefore(@PathVariable("procInstId") String procInstId) {
        Boolean result = materialAcceptService.acceptSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 流程新增或修改
     * @param materialAcceptDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<MaterialAcceptVo> procSaveOrUpdate(@RequestBody MaterialAcceptDto materialAcceptDto) {
        return Result.success(materialAcceptService.procSaveOrUpdate(materialAcceptDto));
    }

    /**
     * 物资领用流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置入库流程结束", notes = "购置入库流程结束")
    @GetMapping("/acceptAuditSuccess/{procInstId}" )
    public Result acceptAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialAcceptService.acceptAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 获取待领用物资信息
     * @param assetsDto assetsDto
     * @return
     */
    @ApiOperation(value = "获取待领用物资信息", notes = "获取待领用情信息")
    @PostMapping("/getDetailByAssets")
    public Result<Page> getDetailByAssets(@RequestBody AssetsDto assetsDto) {
        Page pageVo =  assetsService.getDetailByAssets(assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 物资领用 终止领用
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置入库流程结束", notes = "购置入库流程结束")
    @GetMapping("/stopAccept/{procInstId}" )
    public Result stopAccept(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialAcceptService.stopAccept(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
}
