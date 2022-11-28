package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.SupplierContacts;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.SupplierContactsDto;
import com.netwisd.biz.mdm.vo.SupplierContactsVo;
/**
 * @Description 供应商联系人 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:05:59
 */
public interface SupplierContactsService extends IService<SupplierContacts> {
    Page list(SupplierContactsDto supplierContactsDto);
    Page lists(SupplierContactsDto supplierContactsDto);
    SupplierContactsVo get(Long id);
    Boolean save(SupplierContactsDto supplierContactsDto);
    Boolean update(SupplierContactsDto supplierContactsDto);
    Boolean delete(Long id);
}
