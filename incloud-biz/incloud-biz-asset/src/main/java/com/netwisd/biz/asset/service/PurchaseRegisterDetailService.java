package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseRegisterDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseRegisterDetailDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterDetailVo;
/**
 * @Description 采购信息登记详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
public interface PurchaseRegisterDetailService extends BatchService<PurchaseRegisterDetail> {
    Page list(PurchaseRegisterDetailDto purchaseRegisterDetailDto);
    Page lists(PurchaseRegisterDetailDto purchaseRegisterDetailDto);
    PurchaseRegisterDetailVo get(Long id);
    Boolean save(PurchaseRegisterDetailDto purchaseRegisterDetailDto);
    Boolean saveList(List<PurchaseRegisterDetailDto> purchaseRegisterDetailDtos);
    Boolean update(PurchaseRegisterDetailDto purchaseRegisterDetailDto);
    Boolean delete(Long id);
    Boolean deleteByRegisterId(Long id);
    Boolean deleteByRegisterAssetsId(Long id);
    List<PurchaseRegisterDetailVo> getByRegisterId(Long id);
    List<PurchaseRegisterDetailVo> getByRegisterAssetsId(Long id);

    /**
     * 通过采购登记明细数量，构建生成采购登记详情信息
     * @param purchaseRegisterAssetsDto
     * @return
     */
    List<PurchaseRegisterDetailVo> getByAssetsBuild(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);

}
