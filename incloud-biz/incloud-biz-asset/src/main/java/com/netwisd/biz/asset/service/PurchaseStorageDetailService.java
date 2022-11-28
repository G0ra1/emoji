package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseStorageDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseStorageDetailDto;
import com.netwisd.biz.asset.vo.PurchaseStorageDetailVo;
/**
 * @Description 物资验收入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
public interface PurchaseStorageDetailService extends BatchService<PurchaseStorageDetail> {
    Page list(PurchaseStorageDetailDto purchaseStorageDetailDto);
    Page lists(PurchaseStorageDetailDto purchaseStorageDetailDto);
    PurchaseStorageDetailVo get(Long id);
    Boolean save(PurchaseStorageDetailDto purchaseStorageDetailDto);
    Boolean saveList(List<PurchaseStorageDetailDto> purchaseStorageDetailDtos);
    Boolean update(PurchaseStorageDetailDto purchaseStorageDetailDto);
    Boolean delete(Long id);
    Boolean deleteByStorageId(Long id);
    List<PurchaseStorageDetailVo> getByStorageId(Long storageId);

}
