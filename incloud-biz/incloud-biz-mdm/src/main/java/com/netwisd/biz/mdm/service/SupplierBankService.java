package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.SupplierBank;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.SupplierBankDto;
import com.netwisd.biz.mdm.vo.SupplierBankVo;
/**
 * @Description 供应商银行账号 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:03:18
 */
public interface SupplierBankService extends IService<SupplierBank> {
    Page list(SupplierBankDto supplierBankDto);
    Page lists(SupplierBankDto supplierBankDto);
    SupplierBankVo get(Long id);
    Boolean save(SupplierBankDto supplierBankDto);
    Boolean update(SupplierBankDto supplierBankDto);
    Boolean delete(Long id);
}
