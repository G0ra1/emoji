package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisSysjoints;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsVo;
/**
 * @Description 历史 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:40:29
 */
public interface PortalContentHisSysjointsService extends IService<PortalContentHisSysjoints> {
    Page list(PortalContentHisSysjointsDto portalContentHisSysjointsDto);
    Page lists(PortalContentHisSysjointsDto portalContentHisSysjointsDto);
    PortalContentHisSysjointsVo get(Long id);
    Boolean save(PortalContentHisSysjointsDto portalContentHisSysjointsDto);
    Boolean update(PortalContentHisSysjointsDto portalContentHisSysjointsDto);
    Boolean delete(Long id);
}
