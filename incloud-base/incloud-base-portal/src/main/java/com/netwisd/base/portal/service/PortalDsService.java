package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalDs;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalDsDto;
import com.netwisd.base.portal.vo.PortalDsVo;

import java.util.List;

/**
 * @Description 数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
public interface PortalDsService extends IService<PortalDs> {
    Page list(PortalDsDto portalDsDto);
    List<PortalDsVo> lists(PortalDsDto portalDsDto);
    PortalDsVo get(Long id);
    Boolean save(PortalDsDto portalDsDto);
    Boolean update(PortalDsDto portalDsDto);
    Boolean delete(String id);
}
