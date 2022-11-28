package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentSysjointsSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsSonVo;
/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
public interface PortalContentSysjointsSonService extends IService<PortalContentSysjointsSon> {
    Page list(PortalContentSysjointsSonDto portalContentSysjointsSonDto);
    Page lists(PortalContentSysjointsSonDto portalContentSysjointsSonDto);
    PortalContentSysjointsSonVo get(Long id);
    Boolean save(PortalContentSysjointsSonDto portalContentSysjointsSonDto);
    Boolean update(PortalContentSysjointsSonDto portalContentSysjointsSonDto);
    Boolean delete(Long id);
}
