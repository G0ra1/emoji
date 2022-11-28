package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalPartDs;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.base.portal.vo.PortalPartDsVo;
/**
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
public interface PortalPartDsService extends IService<PortalPartDs> {
    Page list(PortalPartDsDto portalPartDsDto);
    Page lists(PortalPartDsDto portalPartDsDto);
    PortalPartDsVo get(Long id);
    Boolean save(PortalPartDsDto portalPartDsDto);
    Boolean update(PortalPartDsDto portalPartDsDto);
    Boolean delete(Long id);
}
