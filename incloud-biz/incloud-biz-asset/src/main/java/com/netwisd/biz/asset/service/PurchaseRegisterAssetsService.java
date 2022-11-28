package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseRegisterAssets;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
/**
 * @Description 采购信息登记明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-27 18:21:48
 */
public interface PurchaseRegisterAssetsService extends BatchService<PurchaseRegisterAssets> {
    Page list(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);
    Page lists(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);
    PurchaseRegisterAssetsVo get(Long id);
    Boolean save(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);
    Boolean saveList(List<PurchaseRegisterAssetsDto> purchaseRegisterAssetsDtos);
    Boolean update(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);
    Boolean delete(Long id);
    Boolean deleteByRegisterId(Long id);
    List<PurchaseRegisterAssetsVo> getByRegisterId(Long id);

    /**
     * 根据采购ids获取采购明细信息
     * @param registerIds
     * @return
     */
    List<PurchaseRegisterAssetsVo> getRegAssetsByIds(String registerIds);

}
