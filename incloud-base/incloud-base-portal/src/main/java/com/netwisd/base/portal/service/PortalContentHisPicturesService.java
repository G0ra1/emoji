package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisPictures;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisPicturesDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesVo;
/**
 * @Description 历史 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:14:36
 */
public interface PortalContentHisPicturesService extends IService<PortalContentHisPictures> {
    Page list(PortalContentHisPicturesDto portalContentHisPicturesDto);
    Page lists(PortalContentHisPicturesDto portalContentHisPicturesDto);
    PortalContentHisPicturesVo get(Long id);
    Boolean save(PortalContentHisPicturesDto portalContentHisPicturesDto);
    Boolean update(PortalContentHisPicturesDto portalContentHisPicturesDto);
    Boolean delete(Long id);
}
