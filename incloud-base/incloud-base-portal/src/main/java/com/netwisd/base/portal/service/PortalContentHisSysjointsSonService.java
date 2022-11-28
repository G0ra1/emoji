package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisSysjointsSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsSonVo;
/**
 * @Description 历史 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:41:03
 */
public interface PortalContentHisSysjointsSonService extends IService<PortalContentHisSysjointsSon> {
    Page list(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto);
    Page lists(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto);
    PortalContentHisSysjointsSonVo get(Long id);
    Boolean save(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto);
    Boolean update(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto);
    Boolean delete(Long id);
}
