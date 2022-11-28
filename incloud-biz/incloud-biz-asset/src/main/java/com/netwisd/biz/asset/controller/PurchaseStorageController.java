package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.biz.asset.entity.PurchaseAccept;
import com.netwisd.biz.asset.entity.PurchaseAcceptDetail;
import com.netwisd.biz.asset.service.PurchaseAcceptService;
import com.netwisd.biz.asset.vo.PurchaseRegisterResultVo;
import com.netwisd.biz.asset.vo.PurchaseStorageDetailVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.vo.PurchaseStorageVo;
import com.netwisd.biz.asset.service.PurchaseStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 物资验收入库 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purchaseStorage" )
@Api(value = "purchaseStorage", tags = "购置入库Controller")
@Slf4j
public class PurchaseStorageController {

    private final PurchaseStorageService purchaseStorageService;

    private final PurchaseAcceptService purchaseAcceptService;

    /**
     * 分页查询物资验收入库
     * 没有使用参数注解，就是默认从form请求的方式
     *
     * @param purchaseStorageDto 物资验收入库
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    public Result<Page> list(@RequestBody PurchaseStorageDto purchaseStorageDto) {
        Page pageVo = purchaseStorageService.list(purchaseStorageDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询物资验收入库
     *
     * @param purchaseStorageDto 物资验收入库
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists")
    public Result<Page> lists(@RequestBody PurchaseStorageDto purchaseStorageDto) {
        Page pageVo = purchaseStorageService.lists(purchaseStorageDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资验收入库
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<PurchaseStorageVo> get(@PathVariable("id") Long id) {
        PurchaseStorageVo purchaseStorageVo = purchaseStorageService.get(id);
        log.debug("查询成功");
        return Result.success(purchaseStorageVo);
    }

    /**
     * 新增物资验收入库
     *
     * @param purchaseStorageDto 物资验收入库
     * @return Result
     */
    @ApiOperation(value = "新增物资验收入库", notes = "新增物资验收入库")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PurchaseStorageDto purchaseStorageDto) {
        Boolean result = purchaseStorageService.save(purchaseStorageDto);
        List<PurchaseStorageDetailDto> detailList = purchaseStorageDto.getDetailList();
        if (ObjectUtils.isNotEmpty(detailList)){
            for(PurchaseStorageDetailDto purchaseStorageDetailDto : detailList){
                if (StringUtils.isBlank(purchaseStorageDetailDto.getClassifyTypeName())){
                    throw new IncloudException("请填写资产类型");
                }
            }
        }
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改物资验收入库
     *
     * @param purchaseStorageDto 物资验收入库
     * @return Result
     */
    @ApiOperation(value = "修改物资验收入库", notes = "修改物资验收入库")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PurchaseStorageDto purchaseStorageDto) {
        purchaseStorageService.update(purchaseStorageDto);
        List<PurchaseStorageDetailDto> detailList = purchaseStorageDto.getDetailList();
        if (ObjectUtils.isNotEmpty(detailList)){
            for(PurchaseStorageDetailDto purchaseStorageDetailDto : detailList){
                if (StringUtils.isBlank(purchaseStorageDetailDto.getClassifyTypeName())){
                    throw new IncloudException("请填写资产类型");
                }
            }
        }
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除物资验收入库
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资验收入库", notes = "通过id删除物资验收入库")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        purchaseStorageService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     *
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @GetMapping("/procDelete/{procInstId}")
    public Result deleteByProc(@PathVariable("procInstId") String procInstId) {
        purchaseStorageService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通过id查询资产入库管理
     *
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<PurchaseStorageVo> getByProc(@PathVariable("procInstId") String procInstId) {
        PurchaseStorageVo purchaseStorageVo = purchaseStorageService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(purchaseStorageVo);
    }

    /**
     * 流程购置入库保存前，修改申请数据
     *
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置入库保存前", notes = "购置入库保存前")
    @GetMapping("/purStorageSaveBefore/{procInstId}")
    public Result purStorageSaveBefore(@PathVariable("procInstId") String procInstId) {
        Boolean result = purchaseStorageService.purStorageSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 流程购置入库保存后，修改申请数据
     *
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "流程购置入库保存后", notes = "流程购置入库保存后")
    @GetMapping("/purStorageSaveAfter/{procInstId}")
    public Result purStorageSaveAfter(@PathVariable("procInstId") String procInstId) {
        Boolean result = purchaseStorageService.purStorageSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置入库流程结束
     *
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置入库流程结束", notes = "购置入库流程结束")
    @GetMapping("/purStorageAuditSuccess/{procInstId}")
    public Result purStorageAuditSuccess(@PathVariable("procInstId") String procInstId) {
        Boolean result = purchaseStorageService.purStorageAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 获取待入库物资信息
     *
     * @param assetsDetailDto assetsDetailDto
     * @return
     */
//

    /**
     * 流程新增或修改
     *
     * @param purchaseStorageDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<PurchaseStorageVo> procSaveOrUpdate(@RequestBody PurchaseStorageDto purchaseStorageDto) {
        List<PurchaseStorageDetailDto> detailList = purchaseStorageDto.getDetailList();
        if (ObjectUtils.isNotEmpty(detailList)){
            for(PurchaseStorageDetailDto purchaseStorageDetailDto : detailList){
                if (StringUtils.isBlank(purchaseStorageDetailDto.getClassifyTypeName())){
                    throw new IncloudException("请填写资产类型");
                }
            }
        }
        return Result.success(purchaseStorageService.procSaveOrUpdate(purchaseStorageDto));
    }

    /**
     * 获取待登记数据的购置验收单
     *
     * @param acceptDto
     * @return Result
     */
//    @ApiOperation(value = "获取待登记的采购登记", notes = "获取待登记的购置申请")
//    @PostMapping("/getAcceptanceList")
//    public Result<Page> getAcceptanceList(@RequestBody PurchaseAcceptDto acceptDto) {
//        Page pageVo = purchaseAcceptService.forStorageList(acceptDto);
//        log.debug("查询条数:" + pageVo.getTotal());
//        return Result.success(pageVo);
//    }

    /**
     * 获取待入库的购置验收
     *
     * @param purchaseStorageDto
     * @return Result
     */
    @ApiOperation(value = "获取待入库的验收单号", notes = "获取待入库的验收单号")
    @PostMapping("/getAcceptList")
    public Result getAcceptList(@RequestBody PurchaseStorageDto purchaseStorageDto) {
        Page acceptList = purchaseStorageService.getAcceptList(purchaseStorageDto);
        return Result.success(acceptList);
    }

    /**
     * 获取待登记数据的购置验收单
     *
     * @param purchaseAcceptDetailDto
     * @return Result
     */
    @ApiOperation(value = "获取待入库的资产详情", notes = "获取待登记的资产详情")
    @PostMapping("/getAcceptanceList")
    public Result<List<PurchaseStorageDetailVo>> getAcceptanceList(@RequestBody PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        List<PurchaseStorageDetailVo> storageDetailVoList = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(purchaseAcceptDetailDto.getAcceptanceId())){
            storageDetailVoList = purchaseAcceptService.forStorageList(purchaseAcceptDetailDto);
        }
//        else{
//            return Result.failed("未选择购置验收单!!");
//        }
        log.debug("查询条数:" + storageDetailVoList.size());
        return Result.success(storageDetailVoList);
    }

    /**
     * 测试流程条件判断
     *
     * @param expressionEntity
     *
     */
    @ApiOperation(value = "获取待入库的资产详情", notes = "获取待登记的资产详情")
    @PostMapping("/isEndByDraft")
    public Boolean isEndByDraft(@RequestBody ExpressionEntity expressionEntity) {
        return purchaseStorageService.isEndByDraft(expressionEntity);
    }
}
