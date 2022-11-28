package com.netwisd.biz.asset.service;

import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseStorage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseStorageDto;
import com.netwisd.biz.asset.vo.PurchaseStorageVo;
/**
 * @Description 物资验收入库 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
public interface PurchaseStorageService extends BatchService<PurchaseStorage> {
    Page list(PurchaseStorageDto purchaseStorageDto);
    Page lists(PurchaseStorageDto purchaseStorageDto);
    PurchaseStorageVo get(Long id);
    Boolean save(PurchaseStorageDto purchaseStorageDto);
    Boolean saveList(List<PurchaseStorageDto> purchaseStorageDtos);
    Boolean update(PurchaseStorageDto purchaseStorageDto);
    Boolean updateSub(PurchaseStorageDto purchaseStorageDto);
    void delete(Long id);

    void deleteByProc(String procInstId);

    PurchaseStorageVo getByProc(String procInstId);

    /**
     * 购置入库流程完成
     */
    Boolean purStorageAuditSuccess(String procInstId);

    /**
    * 获取待入库库存物资信息
    * */
    Page getDetailByAssets(AssetsDetailDto assetsDetailDto);

    /**
    * 购置入库流程保存前，修改数据
    * @param procInstId procInstId
    * @return Result
    */
    Boolean purStorageSaveBefore(String procInstId);
    /**
     * 购置入库流程保存后，修改数据
     * @param procInstId procInstId
     * @return Result
     */
    Boolean purStorageSaveAfter(String procInstId);

    PurchaseStorageVo procSaveOrUpdate(PurchaseStorageDto purchaseStorageDto);
    /*
    *
    * 获取待入库的购置验收
    * */
    Page getAcceptList(PurchaseStorageDto purchaseStorageDto);


    Boolean isEndByDraft(ExpressionEntity expressionEntity);
    Boolean testCondition(String procInstId);
}
