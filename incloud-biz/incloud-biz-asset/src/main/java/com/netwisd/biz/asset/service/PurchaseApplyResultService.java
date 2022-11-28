package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.entity.PurchaseApplyResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseApplyResultDto;
import com.netwisd.biz.asset.vo.PurchaseApplyResultVo;

import java.util.List;

/**
 * @Description 购置申请结果表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-21 11:18:24
 */
public interface PurchaseApplyResultService extends IService<PurchaseApplyResult> {
    Page list(PurchaseApplyResultDto purchaseApplyResultDto);
    Page lists(PurchaseApplyResultDto purchaseApplyResultDto);
    PurchaseApplyResultVo get(Long id);
    Boolean save(PurchaseApplyResultDto purchaseApplyResultDto);
    Boolean update(PurchaseApplyResultDto purchaseApplyResultDto);
    Boolean delete(Long id);

    Boolean saveList(List<PurchaseApplyResultDto> purchaseApplyResultDtos);
}
