package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.PurchaseRegister;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseAccept;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
public interface PurchaseAcceptService extends BatchService<PurchaseAccept> {
    Page list(PurchaseAcceptDto purchaseAcceptDto);
    Page lists(PurchaseAcceptDto purchaseAcceptDto);
    PurchaseAcceptVo get(Long id);
    Boolean save(PurchaseAcceptDto purchaseAcceptDto);
    Boolean saveList(List<PurchaseAcceptDto> purchaseAcceptDtos);
    Boolean update(PurchaseAcceptDto purchaseAcceptDto);
    Boolean updateSub(PurchaseAcceptDto purchaseAcceptDto);
    Boolean delete(Long id);

    /**
     * 通根据流程实例id删除业务数据
     */
    Boolean deleteByProc(String procInstId);

    /**
     * 通过id查询资产入库管理
     */
    PurchaseAcceptVo getByProcId(String procInstId);

    /**
     * 获取采购后待验收数据
     */
    Page getAcceptList(PurchaseRegisterResultDto purchaseRegisterResultDto);

    /**
     * 获取购置验收详情信息
     */
    PurchaseAcceptAssetVo getDetailByAssets(PurchaseAcceptAssetDto purchaseAcceptAssetDto);

    /**
     * 购置验收保存前
     */
    Boolean purAcceptSaveBefore(String procInstId);

    /**
     * 购置验收保存后
     */
    Boolean purAcceptSaveAfter(String procInstId);

    /**
     * 流程完成后，填写资产台账及明细验收数量
     */
    Boolean purAcceptAuditSuccess(String procInstId);

    PurchaseAcceptVo procSaveOrUpdate(PurchaseAcceptDto purchaseAcceptDto);

    List<PurchaseAcceptDetailVo> getDetailByAssetsList(List<PurchaseRegisterResultDto> resultDtoList);

    /**
     * 获取待登记数据的购置验收单
     * @param purchaseAcceptDetailDto
     * @return Result
     */
    List<PurchaseStorageDetailVo> forStorageList(PurchaseAcceptDetailDto purchaseAcceptDetailDto);
}
