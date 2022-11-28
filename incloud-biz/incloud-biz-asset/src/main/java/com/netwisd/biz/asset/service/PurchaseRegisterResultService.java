package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.PurchaseRegisterResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseRegisterResultDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterResultVo;
/**
 * @Description 采购登记结果表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:54:08
 */
public interface PurchaseRegisterResultService extends IService<PurchaseRegisterResult> {
    Page list(PurchaseRegisterResultDto purchaseRegisterResultDto);
    Page lists(PurchaseRegisterResultDto purchaseRegisterResultDto);
    PurchaseRegisterResultVo get(Long id);
    Boolean save(PurchaseRegisterResultDto purchaseRegisterResultDto);
    Boolean update(PurchaseRegisterResultDto purchaseRegisterResultDto);
    Boolean delete(Long id);

    Page getAcceptList(PurchaseRegisterResultDto purchaseRegisterResultDto);

    Page getRegResultByIds(String registerIds);

    Page getRegResultList(PurchaseRegisterResultDto registerResultDto);
}
