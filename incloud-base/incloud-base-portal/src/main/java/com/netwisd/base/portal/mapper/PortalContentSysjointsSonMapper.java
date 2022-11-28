package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentSysjointsSon;
import com.netwisd.base.portal.dto.PortalContentSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@Mapper
public interface PortalContentSysjointsSonMapper extends BaseMapper<PortalContentSysjointsSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentSysjointsSonDto
     * @return
     */
    Page<PortalContentSysjointsSonVo> getPageList(Page page, @Param("portalContentSysjointsSonDto") PortalContentSysjointsSonDto portalContentSysjointsSonDto);
}
