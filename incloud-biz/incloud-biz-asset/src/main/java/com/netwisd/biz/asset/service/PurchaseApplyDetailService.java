package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.entity.PurchaseApply;
import com.netwisd.biz.asset.entity.PurchaseApplyDetail;
import com.netwisd.biz.asset.vo.PurchaseAcceptVo;
import com.netwisd.biz.asset.vo.PurchaseApplyDetailVo;


import java.util.List;

/**
 * @Description 购置申请详情 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 17:26:16
 */
public interface PurchaseApplyDetailService extends IService<PurchaseApplyDetail> {
    Page list(PurchaseApplyDetailDto purchaseApplyDetailDto);

    Page lists(PurchaseApplyDetailDto purchaseApplyDetailDto);

    PurchaseApplyDetailVo get(Long id);


    Boolean save(PurchaseApplyDetailDto purchaseApplyDetailDto);

    Boolean saveList(List<PurchaseApplyDetailDto> purchaseApplyDetailDto);

    Boolean update(PurchaseApplyDetailDto purchaseApplyDetailDto);

    Boolean delete(Long id);

    Boolean deleteByApplyId(Long applyId);

    List<PurchaseApplyDetailVo> getByApplyId(Long applyId);

    Page getPageByApplyId(Long applyId);

    List<PurchaseApplyDetailVo> getByApplyIds(String applyIds);

    Page getPageByApplyIds(String applyIds);

    /**
     * 获取申请待登记数据
     *
     * @param purchaseApplyDetailDto
     * @return
     */
    Page getRegisterList(PurchaseApplyDetailDto purchaseApplyDetailDto);

}
