package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentAdjPicturesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesSonVo;
/**
 * @Description 调整 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:13:46
 */
public interface PortalContentAdjPicturesSonService extends IService<PortalContentAdjPicturesSon> {
    Page list(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto);
    Page lists(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto);
    PortalContentAdjPicturesSonVo get(Long id);
    Boolean save(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto);
    Boolean update(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto);
    Boolean delete(Long id);
}
