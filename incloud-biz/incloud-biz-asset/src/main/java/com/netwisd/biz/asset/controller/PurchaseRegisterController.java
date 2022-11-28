package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.entity.PurchaseApply;
import com.netwisd.biz.asset.entity.PurchaseApplyDetail;
import com.netwisd.biz.asset.entity.PurchaseRegister;
import com.netwisd.biz.asset.service.PurchaseApplyDetailService;
import com.netwisd.biz.asset.service.PurchaseApplyService;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
import com.netwisd.biz.asset.vo.PurchaseRegisterDetailVo;
import com.netwisd.common.core.constants.ResultConstants;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.PurchaseRegisterDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterVo;
import com.netwisd.biz.asset.service.PurchaseRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 采购信息登记 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purchaseRegister" )
@Api(value = "purchaseRegister", tags = "购置采购信息登记Controller")
@Slf4j
public class PurchaseRegisterController {

    private final PurchaseRegisterService purchaseRegisterService;

    private final PurchaseApplyService purchaseApplyService;

    private final PurchaseApplyDetailService purchaseApplyDetailService;


    /**
     * 分页查询采购信息登记
     * 没有使用参数注解，就是默认从form请求的方式
     * @param purchaseRegisterDto 采购信息登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PurchaseRegisterDto purchaseRegisterDto) {
        Page pageVo = purchaseRegisterService.list(purchaseRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询采购信息登记
     * @param purchaseRegisterDto 采购信息登记
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PurchaseRegisterDto purchaseRegisterDto) {
        Page pageVo = purchaseRegisterService.lists(purchaseRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询采购信息登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PurchaseRegisterVo> get(@PathVariable("id" ) Long id) {
        PurchaseRegisterVo purchaseRegisterVo = purchaseRegisterService.get(id);
        log.debug("查询成功");
        return Result.success(purchaseRegisterVo);
    }

    /**
     * 新增采购信息登记
     * @param purchaseRegisterDto 采购信息登记
     * @return Result
     */
    @ApiOperation(value = "新增采购信息登记", notes = "新增采购信息登记")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PurchaseRegisterDto purchaseRegisterDto) {
        Boolean result = purchaseRegisterService.save(purchaseRegisterDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改采购信息登记
     * @param purchaseRegisterDto 采购信息登记
     * @return Result
     */
    @ApiOperation(value = "修改采购信息登记", notes = "修改采购信息登记")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PurchaseRegisterDto purchaseRegisterDto) {
        purchaseRegisterService.update(purchaseRegisterDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除采购信息登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除采购信息登记", notes = "通过id删除采购信息登记")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        purchaseRegisterService.delete(id);
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
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        purchaseRegisterService.deleteByProc(procInstId);
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
    public Result<PurchaseRegisterVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        PurchaseRegisterVo purchaseRegisterVo = purchaseRegisterService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(purchaseRegisterVo);
    }

    /**
     * 流程新增或修改
     * @param purchaseRegisterDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<PurchaseRegisterVo> procSaveOrUpdate(@RequestBody PurchaseRegisterDto purchaseRegisterDto) {
        return Result.success(purchaseRegisterService.procSaveOrUpdate(purchaseRegisterDto));
    }

    /**
     * 获取待登记数据的购置申请
     * @param purchaseApplyDto
     * @return Result
     */
    @ApiOperation(value = "获取待登记的购置申请", notes = "获取待登记的购置申请")
    @PostMapping("/getApplyList")
    public Result<Page> getApplyList(@RequestBody PurchaseApplyDto purchaseApplyDto) {
        Page pageVo = purchaseApplyService.getRegisterList(purchaseApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 获取待登记数据的购置申请
     * @param applyIds
     * @return Result
     */
    @ApiOperation(value = "获取待登记的购置申请详情", notes = "获取待登记的购置申请详情")
    @GetMapping("/getApplyDetailByIds/{applyIds}")
    public Result getApplyDetailByIds(@PathVariable String applyIds) {
        //Page pageVo = purchaseApplyDetailService.getPageByApplyIds(applyIds);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(purchaseApplyDetailService.getByApplyIds(applyIds));
    }

    /**
     * 获取采购登记详情信息
     * @param purchaseRegisterAssetsDto
     * @return Result
     */
    @ApiOperation(value = "获取采购登记详情信息", notes = "添加资产明细数量后，通过该资产明细，生成资产详情信息")
    @PostMapping("/getDetailByAssets" )
    public Result<List<PurchaseRegisterDetailVo>> getDetailByAssets(@RequestBody PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        List<PurchaseRegisterDetailVo> registerDetailVoList = purchaseRegisterService.getDetailByAssets(purchaseRegisterAssetsDto);
        log.debug("查询成功");
        return Result.success(registerDetailVoList);
    }



    /**
     * 获取待登记数据
     * @param purchaseApplyDetailDto
     * @return Result
     */
    @ApiOperation(value = "获取申请待登记数据", notes = "获取申请待登记数据")
    @PostMapping("/getApplyDetailList" )
    public Result<Page> getApplyDetailList(@RequestBody PurchaseApplyDetailDto purchaseApplyDetailDto) {
        Page pageVo = purchaseApplyDetailService.getRegisterList(purchaseApplyDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 购置采购保存前，修改申请数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存前", notes = "购置采购保存前")
    @GetMapping("purRegSaveBefore/{procInstId}" )
    public Result purRegSaveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseRegisterService.purRegSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置采购保存后，修改申请数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存后", notes = "购置采购保存后")
    @GetMapping("purRegSaveAfter/{procInstId}" )
    public Result purRegSaveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseRegisterService.purRegSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置采购流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购流程结束", notes = "购置采购流程结束")
    @GetMapping("purApplyAuditSuccess/{procInstId}" )
    public Result purApplyAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseRegisterService.purRegAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }




}
