package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisPicturesSon;
import com.netwisd.base.portal.dto.PortalContentHisPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:02
 */
@Mapper
public interface PortalContentHisPicturesSonMapper extends BaseMapper<PortalContentHisPicturesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisPicturesSonDto
     * @return
     */
    Page<PortalContentHisPicturesSonVo> getPageList(Page page, @Param("portalContentHisPicturesSonDto") PortalContentHisPicturesSonDto portalContentHisPicturesSonDto);
}
