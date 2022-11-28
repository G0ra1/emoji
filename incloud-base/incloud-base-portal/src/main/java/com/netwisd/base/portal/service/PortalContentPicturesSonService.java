package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentPicturesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
/**
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
public interface PortalContentPicturesSonService extends IService<PortalContentPicturesSon> {
    Page list(PortalContentPicturesSonDto portalContentPicturesSonDto);
    Page lists(PortalContentPicturesSonDto portalContentPicturesSonDto);
    PortalContentPicturesSonVo get(Long id);
    Boolean save(PortalContentPicturesSonDto portalContentPicturesSonDto);
    Boolean update(PortalContentPicturesSonDto portalContentPicturesSonDto);
    Boolean delete(Long id);
}
