package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentAdjSysjointsSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsSonVo;
/**
 * @Description 调整 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:52
 */
public interface PortalContentAdjSysjointsSonService extends IService<PortalContentAdjSysjointsSon> {
    Page list(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto);
    Page lists(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto);
    PortalContentAdjSysjointsSonVo get(Long id);
    Boolean save(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto);
    Boolean update(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto);
    Boolean delete(Long id);
}
