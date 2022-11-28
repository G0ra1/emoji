package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalTheme;
import com.netwisd.base.portal.dto.PortalThemeDto;
import com.netwisd.base.portal.vo.PortalThemeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description   主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
@Mapper
public interface PortalThemeMapper extends BaseMapper<PortalTheme> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalThemeDto
     * @return
     */
    Page<PortalThemeVo> getPageList(Page page, @Param("portalThemeDto") PortalThemeDto portalThemeDto);
}
