package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseAcceptDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseAcceptDetailDto;
import com.netwisd.biz.asset.vo.PurchaseAcceptDetailVo;
/**
 * @Description 验收资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
public interface PurchaseAcceptDetailService extends BatchService<PurchaseAcceptDetail> {
    Page list(PurchaseAcceptDetailDto purchaseAcceptDetailDto);
    Page lists(PurchaseAcceptDetailDto purchaseAcceptDetailDto);
    PurchaseAcceptDetailVo get(Long id);
    Boolean save(PurchaseAcceptDetailDto purchaseAcceptDetailDto);
    Boolean saveList(List<PurchaseAcceptDetailDto> purchaseAcceptDetailDtos);
    Boolean update(PurchaseAcceptDetailDto purchaseAcceptDetailDto);
    Boolean delete(Long id);
    Boolean deleteByAcceptanceId(Long id);
    List<PurchaseAcceptDetailVo> getByAcceptanceId(Long id);
    Boolean deleteByAcceptanceAssetsId(Long id);
    List<PurchaseAcceptDetailVo> getByAcceptanceAssetsId(Long id);
}
