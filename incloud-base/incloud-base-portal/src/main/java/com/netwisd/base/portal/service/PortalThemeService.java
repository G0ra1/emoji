package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalTheme;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalThemeDto;
import com.netwisd.base.portal.vo.PortalThemeVo;

import java.util.List;

/**
 * @Description   主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
public interface PortalThemeService extends IService<PortalTheme> {
    Page list(PortalThemeDto portalThemeDto);
    List<PortalThemeVo> lists(PortalThemeDto portalThemeDto);
    PortalThemeVo get(Long id);
    Boolean save(PortalThemeDto portalThemeDto);
    Boolean update(PortalThemeDto portalThemeDto);
    Boolean delete(Long id);
    Boolean switchTheme (Long id);
}
