package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.PurchaseAcceptDetail;
import com.netwisd.biz.asset.entity.PurchaseRegister;
import com.netwisd.biz.asset.entity.PurchaseRegisterAssets;
import com.netwisd.biz.asset.entity.PurchaseRegisterResult;
import com.netwisd.biz.asset.service.PurchaseRegisterAssetsService;
import com.netwisd.biz.asset.service.PurchaseRegisterResultService;
import com.netwisd.biz.asset.service.PurchaseRegisterService;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.service.PurchaseAcceptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purchaseAccept" )
@Api(value = "purchaseAccept", tags = "购置验收Controller")
@Slf4j
public class PurchaseAcceptController {

    private final  PurchaseAcceptService purchaseAcceptService;

    private final PurchaseRegisterService purchaseRegisterService;

    private final PurchaseRegisterAssetsService purchaseRegisterAssetsService;

    private final PurchaseRegisterResultService purchaseRegisterResultService;

    /**
     * 分页查询物资购置验收
     * 没有使用参数注解，就是默认从form请求的方式
     * @param purchaseAcceptDto 物资购置验收
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PurchaseAcceptDto purchaseAcceptDto) {
        Page pageVo = purchaseAcceptService.list(purchaseAcceptDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询物资购置验收
     * @param purchaseAcceptDto 物资购置验收
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PurchaseAcceptDto purchaseAcceptDto) {
        Page pageVo = purchaseAcceptService.lists(purchaseAcceptDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资购置验收
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PurchaseAcceptVo> get(@PathVariable("id" ) Long id) {
        PurchaseAcceptVo purchaseAcceptVo = purchaseAcceptService.get(id);
        log.debug("查询成功");
        return Result.success(purchaseAcceptVo);
    }

    /**
     * 新增物资购置验收
     * @param purchaseAcceptDto 物资购置验收
     * @return Result
     */
    @ApiOperation(value = "新增物资购置验收", notes = "新增物资购置验收")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PurchaseAcceptDto purchaseAcceptDto) {
        List<PurchaseAcceptDetailDto> detailList = purchaseAcceptDto.getDetailList();
        if (ObjectUtils.isNotEmpty(detailList)){
            for (PurchaseAcceptDetailDto purchaseAcceptDetailDto : detailList){
                if (ObjectUtils.isEmpty(purchaseAcceptDetailDto.getAcceptanceStatus())){
                    throw new IncloudException("请选择确认验收");
                }
            }
        }
        purchaseAcceptService.save(purchaseAcceptDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改物资购置验收
     * @param purchaseAcceptDto 物资购置验收
     * @return Result
     */
    @ApiOperation(value = "修改物资购置验收", notes = "修改物资购置验收")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PurchaseAcceptDto purchaseAcceptDto) {
        purchaseAcceptService.update(purchaseAcceptDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除物资购置验收
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资购置验收", notes = "通过id删除物资购置验收")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        purchaseAcceptService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result procDelete(@PathVariable("procInstId" ) String procInstId) {
        purchaseAcceptService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通过id查询资产入库管理
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/getByProcId/{procInstId}" )
    public Result<PurchaseAcceptVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        PurchaseAcceptVo acceptVo = purchaseAcceptService.getByProcId(procInstId);
        log.debug("查询成功");
        return Result.success(acceptVo);
    }

    /**
     * 流程新增或修改
     * @param purchaseAcceptDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<PurchaseAcceptVo> procSaveOrUpdate(@RequestBody PurchaseAcceptDto purchaseAcceptDto) {
        List<PurchaseAcceptDetailDto> detailList = purchaseAcceptDto.getDetailList();
        if (ObjectUtils.isNotEmpty(detailList)){
            for (PurchaseAcceptDetailDto purchaseAcceptDetailDto : detailList){
                if (ObjectUtils.isEmpty(purchaseAcceptDetailDto.getAcceptanceStatus())){
                    throw new IncloudException("请选择确认验收");
                }
            }
        }
        return Result.success(purchaseAcceptService.procSaveOrUpdate(purchaseAcceptDto));
    }

    /**
     * 通过id查询资产入库管理
     * @param purchaseRegisterResultDto
     * @return Result
     */
    @ApiOperation(value = "获取登记待验收数据", notes = "获取登记待验收数据")
    @PostMapping("/getAcceptList" )
    public Result<Page> getAcceptList(@Validation @RequestBody PurchaseRegisterResultDto purchaseRegisterResultDto) {
        Page pageVo = purchaseAcceptService.getAcceptList(purchaseRegisterResultDto);
        log.debug("查询成功");
        return Result.success(pageVo);
    }

    /**
     * 获取购置验收详情信息
     * @param purchaseAcceptAssetDto
     * @return Result
     */
    @ApiOperation(value = "获取购置验收详情信息", notes = "添加资产明细数量后，通过该资产明细，生成资产详情信息")
    @PostMapping("/getDetailByAssets" )
    public Result<PurchaseAcceptAssetVo> getDetailByAssets(@Validation @RequestBody PurchaseAcceptAssetDto purchaseAcceptAssetDto) {
        if(ObjectUtils.isEmpty(purchaseAcceptAssetDto.getAcceptanceNumber())){
//            return Result.failed("验收数量不能为空!!");
            throw new IncloudException("验收数量不能为空!!");
        }
        PurchaseAcceptAssetVo purchaseAcceptAssetVo = purchaseAcceptService.getDetailByAssets(purchaseAcceptAssetDto);
        log.debug("查询成功");
        return Result.success(purchaseAcceptAssetVo);
    }

    /**
     * 获取购置验收详情信息
     * @param assetDtoList
     * @return Result
     */
    @ApiOperation(value = "获取购置验收详情信息", notes = "添加资产明细数量后，通过该资产明细，生成资产详情信息")
    @PostMapping("/getDetailByAssetsList" )
    public Result<List<PurchaseAcceptDetailVo>> getDetailByAssetsList(@Validation @RequestBody List<PurchaseRegisterResultDto> assetDtoList) {
        List<PurchaseAcceptDetailVo> assetsVoList = purchaseAcceptService.getDetailByAssetsList(assetDtoList);
        log.debug("查询成功");
        return Result.success(assetsVoList);
    }

    /**
     * 购置采购保存前，修改申请数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存前", notes = "购置采购保存前")
    @GetMapping("/purAcceptSaveBefore/{procInstId}" )
    public Result purAcceptSaveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseAcceptService.purAcceptSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }


    /**
     * 购置采购保存后，修改申请数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存后", notes = "购置采购保存后")
    @GetMapping("/purAcceptSaveAfter/{procInstId}" )
    public Result purAcceptSaveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseAcceptService.purAcceptSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置验收流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置验收流程结束", notes = "购置验收流程结束")
    @GetMapping("/purAcceptAuditSuccess/{procInstId}" )
    public Result purAcceptAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseAcceptService.purAcceptAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 获取待登记数据的购置申请
     * @param registerDto
     * @return Result
     */
    @ApiOperation(value = "获取待登记的采购登记", notes = "获取待登记的购置申请")
    @PostMapping("/getRegisterList")
    public Result<Page> getRegisterList(@RequestBody PurchaseRegisterDto registerDto) {
        Page pageVo = purchaseRegisterService.forAcceptanceList(registerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 获取待登记数据的购置申请
     * @param registerIds
     * @return Result
     */
//    @ApiOperation(value = "获取待登记的购置申请详情", notes = "获取待登记的购置申请详情")
//    @GetMapping("/getRegResultByIds")
//    public Result<Page<PurchaseRegisterResultVo>> getRegResultByIds(@PathVariable String registerIds) {
//        //Page pageVo = purchaseApplyDetailService.getPageByApplyIds(applyIds);
//        //log.debug("查询条数:"+pageVo.getTotal());
//        return Result.success(purchaseRegisterResultService.getRegResultByIds(registerIds));
//    }

    /**
     * 获取待登记数据的购置申请
     * @param registerResultDto
     * @return Result
     */
    @ApiOperation(value = "获取待验收的采购结果", notes = "获取待验收的采购结果")
    @PostMapping("/getRegResultList")
    public Result<Page<PurchaseRegisterResultVo>> getRegResultList(@RequestBody PurchaseRegisterResultDto registerResultDto) {
        Long registerId = registerResultDto.getRegisterId();
        if (ObjectUtils.isEmpty(registerId)){
//            return Result.failed("采购登记不能为空!!");
//            return Result.success(registerResultDto.getPage());
//            throw new IncloudException("采购登记不能为空!!");
        }
        //Page pageVo = purchaseApplyDetailService.getPageByApplyIds(applyIds);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(purchaseRegisterResultService.getRegResultList(registerResultDto));
    }

}
