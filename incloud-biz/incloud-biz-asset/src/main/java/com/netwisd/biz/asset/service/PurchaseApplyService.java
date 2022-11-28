package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.PurchaseApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @Description 购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
public interface PurchaseApplyService extends IService<PurchaseApply> {
    Page list(PurchaseApplyDto purchaseApplyDto);
    Page lists(PurchaseApplyDto purchaseApplyDto);
    PurchaseApplyVo get(Long id);
    List<PurchaseApplyVo> getByIds(String ids);
    Boolean save(PurchaseApplyDto purchaseApplyDto);
    Boolean saveList(List<PurchaseApplyDto> purchaseApplyDtos);
    Boolean update(PurchaseApplyDto purchaseApplyDto);
    Boolean delete(Long id);

    PurchaseApplyVo getByProcId(String procInstId);

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    void deleteByProc(String procInstId);

    Boolean purApplyAuditSuccess(String procInstId);

    Page getRegisterList(PurchaseApplyDto purchaseApplyDto);

    PurchaseApplyVo procSaveOrUpdate(PurchaseApplyDto purchaseApplyDto);
}
