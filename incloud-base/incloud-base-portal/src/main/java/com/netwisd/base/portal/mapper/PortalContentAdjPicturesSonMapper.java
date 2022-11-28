package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjPicturesSon;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 调整 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:13:46
 */
@Mapper
public interface PortalContentAdjPicturesSonMapper extends BaseMapper<PortalContentAdjPicturesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjPicturesSonDto
     * @return
     */
    Page<PortalContentAdjPicturesSonVo> getPageList(Page page, @Param("portalContentAdjPicturesSonDto") PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto);
}
