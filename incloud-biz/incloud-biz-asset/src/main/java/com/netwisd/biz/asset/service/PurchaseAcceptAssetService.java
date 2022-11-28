package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseAcceptAsset;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseAcceptAssetDto;
import com.netwisd.biz.asset.vo.PurchaseAcceptAssetVo;
/**
 * @Description 验收明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
public interface PurchaseAcceptAssetService extends BatchService<PurchaseAcceptAsset> {
    Page list(PurchaseAcceptAssetDto purchaseAcceptAssetDto);
    Page lists(PurchaseAcceptAssetDto purchaseAcceptAssetDto);
    PurchaseAcceptAssetVo get(Long id);
    Boolean save(PurchaseAcceptAssetDto purchaseAcceptAssetDto);
    Boolean saveList(List<PurchaseAcceptAssetDto> purchaseAcceptAssetDtos);
    Boolean update(PurchaseAcceptAssetDto purchaseAcceptAssetDto);
    Boolean delete(Long id);
    Boolean deleteByAcceptanceId(Long id);
    List<PurchaseAcceptAssetVo> getByAcceptanceId(Long id);
}
