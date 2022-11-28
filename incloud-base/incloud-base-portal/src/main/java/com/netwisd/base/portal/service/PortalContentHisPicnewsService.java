package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisPicnews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentHisPicnewsVo;
/**
 * @Description 历史 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:24:33
 */
public interface PortalContentHisPicnewsService extends IService<PortalContentHisPicnews> {
    Page list(PortalContentHisPicnewsDto portalContentHisPicnewsDto);
    Page lists(PortalContentHisPicnewsDto portalContentHisPicnewsDto);
    PortalContentHisPicnewsVo get(Long id);
    Boolean save(PortalContentHisPicnewsDto portalContentHisPicnewsDto);
    Boolean update(PortalContentHisPicnewsDto portalContentHisPicnewsDto);
    Boolean delete(Long id);
}
