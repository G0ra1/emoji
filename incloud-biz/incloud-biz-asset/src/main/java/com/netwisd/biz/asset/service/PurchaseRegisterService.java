package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.entity.PurchaseApplyDetail;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
import com.netwisd.biz.asset.vo.PurchaseRegisterDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.PurchaseRegister;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseRegisterDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterVo;
/**
 * @Description 采购信息登记 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
public interface PurchaseRegisterService extends BatchService<PurchaseRegister> {
    Page list(PurchaseRegisterDto purchaseRegisterDto);
    Page lists(PurchaseRegisterDto purchaseRegisterDto);
    PurchaseRegisterVo get(Long id);
    Boolean save(PurchaseRegisterDto purchaseRegisterDto);
    Boolean saveList(List<PurchaseRegisterDto> purchaseRegisterDtos);
    Boolean update(PurchaseRegisterDto purchaseRegisterDto);
    Boolean updateSub(PurchaseRegisterDto purchaseRegisterDto);
    Boolean delete(Long id);

    /**
     * 根据流程实例id删除
     */
    void deleteByProc(String procInstId);

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    PurchaseRegisterVo getByProc(String procInstId);


    /**
     * 通过采购登记明细数量，构建生成采购登记详情信息
     * @param purchaseRegisterAssetsDto
     * @return PurchaseRegisterAssetsVo
     *          生成的详情数据在子集list中
     */
    List<PurchaseRegisterDetailVo> getDetailByAssets(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);


    /**
     * 购置登记保存前
     *  增加申请详情未登记数量,减少登记数量
     * @param procInstId
     * @return
     */
    Boolean purRegSaveBefore(String procInstId);

    /**
     * 购置申请保存后
     *  增加申请详情登记数量,减少未登记数量
     * @param procInstId
     * @return
     */
    Boolean purRegSaveAfter(String procInstId);

    /**
     * 购置申请流程完成
     * @param procInstId
     * @return
     */
    Boolean purRegAuditSuccess(String procInstId);

    PurchaseRegisterVo procSaveOrUpdate(PurchaseRegisterDto purchaseRegisterDto);

    /**
     * 校验采购登记详情，采购数量和采购金额
     *
     * @param purchaseRegisterDto
     * @return
     */
    void verifyRegisterNumberAndAmount(PurchaseRegisterDto purchaseRegisterDto);

    void verifyRegisterNumberAndAmount(String procInstId);

    /**
     * 获取登记待验收数据
     * @param registerDto
     * @return
     */
    Page forAcceptanceList(PurchaseRegisterDto registerDto);
}
