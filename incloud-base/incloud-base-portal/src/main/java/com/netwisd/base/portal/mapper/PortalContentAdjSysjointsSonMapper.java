package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjSysjointsSon;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 调整 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:52
 */
@Mapper
public interface PortalContentAdjSysjointsSonMapper extends BaseMapper<PortalContentAdjSysjointsSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjSysjointsSonDto
     * @return
     */
    Page<PortalContentAdjSysjointsSonVo> getPageList(Page page, @Param("portalContentAdjSysjointsSonDto") PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto);
}
