package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentLoginBackgroundSon;
import com.netwisd.base.portal.dto.PortalContentLoginBackgroundSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginBackgroundSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:03:27
 */
@Mapper
public interface PortalContentLoginBackgroundSonMapper extends BaseMapper<PortalContentLoginBackgroundSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentLoginBackgroundSonDto
     * @return
     */
    Page<PortalContentLoginBackgroundSonVo> getPageList(Page page, @Param("portalContentLoginBackgroundSonDto") PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto);
}
