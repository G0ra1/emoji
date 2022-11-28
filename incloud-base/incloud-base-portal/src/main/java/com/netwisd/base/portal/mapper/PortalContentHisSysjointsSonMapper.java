package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisSysjointsSon;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:41:03
 */
@Mapper
public interface PortalContentHisSysjointsSonMapper extends BaseMapper<PortalContentHisSysjointsSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisSysjointsSonDto
     * @return
     */
    Page<PortalContentHisSysjointsSonVo> getPageList(Page page, @Param("portalContentHisSysjointsSonDto") PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto);
}
