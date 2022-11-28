package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.SupplierContacts;
import com.netwisd.biz.mdm.dto.SupplierContactsDto;
import com.netwisd.biz.mdm.vo.SupplierContactsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 供应商联系人 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:05:59
 */
@Mapper
public interface SupplierContactsMapper extends BaseMapper<SupplierContacts> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param supplierContactsDto
     * @return
     */
    Page<SupplierContactsVo> getPageList(Page page, @Param("supplierContactsDto") SupplierContactsDto supplierContactsDto);
}
