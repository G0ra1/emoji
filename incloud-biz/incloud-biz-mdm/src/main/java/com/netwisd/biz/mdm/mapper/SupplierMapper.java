package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.Supplier;
import com.netwisd.biz.mdm.dto.SupplierDto;
import com.netwisd.biz.mdm.vo.SupplierVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param supplierDto
     * @return
     */
    Page<SupplierVo> getPageList(Page page, @Param("supplierDto") SupplierDto supplierDto);

    List<SupplierVo> outList();

}
