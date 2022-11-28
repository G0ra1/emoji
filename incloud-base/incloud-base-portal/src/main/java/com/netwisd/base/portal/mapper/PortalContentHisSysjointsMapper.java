package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisSysjoints;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:40:29
 */
@Mapper
public interface PortalContentHisSysjointsMapper extends BaseMapper<PortalContentHisSysjoints> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisSysjointsDto
     * @return
     */
    Page<PortalContentHisSysjointsVo> getPageList(Page page, @Param("portalContentHisSysjointsDto") PortalContentHisSysjointsDto portalContentHisSysjointsDto);
}
