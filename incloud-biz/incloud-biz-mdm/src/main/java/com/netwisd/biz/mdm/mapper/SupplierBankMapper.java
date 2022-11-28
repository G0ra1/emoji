package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.SupplierBank;
import com.netwisd.biz.mdm.dto.SupplierBankDto;
import com.netwisd.biz.mdm.vo.SupplierBankVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 供应商银行账号 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:03:18
 */
@Mapper
public interface SupplierBankMapper extends BaseMapper<SupplierBank> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param supplierBankDto
     * @return
     */
    Page<SupplierBankVo> getPageList(Page page, @Param("supplierBankDto") SupplierBankDto supplierBankDto);
}
