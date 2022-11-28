package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisPicturesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesSonVo;
/**
 * @Description 历史 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:02
 */
public interface PortalContentHisPicturesSonService extends IService<PortalContentHisPicturesSon> {
    Page list(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto);
    Page lists(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto);
    PortalContentHisPicturesSonVo get(Long id);
    Boolean save(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto);
    Boolean update(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto);
    Boolean delete(Long id);
}
