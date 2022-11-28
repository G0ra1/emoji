package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentPicturesSon;
import com.netwisd.base.portal.dto.PortalContentPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@Mapper
public interface PortalContentPicturesSonMapper extends BaseMapper<PortalContentPicturesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentPicturesSonDto
     * @return
     */
    Page<PortalContentPicturesSonVo> getPageList(Page page, @Param("portalContentPicturesSonDto") PortalContentPicturesSonDto portalContentPicturesSonDto);
}
