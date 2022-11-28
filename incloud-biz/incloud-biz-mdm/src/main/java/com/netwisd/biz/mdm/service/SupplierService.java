package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.Supplier;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.SupplierDto;
import com.netwisd.biz.mdm.vo.SupplierVo;
import java.util.List;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
public interface SupplierService extends IService<Supplier> {
    Page list(SupplierDto supplierDto);
    List<SupplierVo> lists(SupplierDto supplierDto);
    SupplierVo get(Long id);
    Boolean save(SupplierDto supplierDto);
    Boolean update(SupplierDto supplierDto);
    Boolean delete(Long id);
    Boolean saveTransmitAll(List<SupplierDto> dtoList);


}
