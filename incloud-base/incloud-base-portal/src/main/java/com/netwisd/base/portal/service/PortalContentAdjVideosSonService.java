package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentAdjVideosSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosSonVo;
/**
 * @Description  视频类型内容发布-调整表子表   功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 16:15:52
 */
public interface PortalContentAdjVideosSonService extends IService<PortalContentAdjVideosSon> {
    Page list(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto);
    Page lists(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto);
    PortalContentAdjVideosSonVo get(Long id);
    Boolean save(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto);
    Boolean update(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto);
    Boolean delete(Long id);
}
