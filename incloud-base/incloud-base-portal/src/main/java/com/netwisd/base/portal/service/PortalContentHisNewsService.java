package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisNews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisNewsDto;
import com.netwisd.base.portal.vo.PortalContentHisNewsVo;
/**
 * @Description 历史 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-29 19:26:33
 */
public interface PortalContentHisNewsService extends IService<PortalContentHisNews> {
    Page list(PortalContentHisNewsDto portalContentHisNewsDto);
    Page lists(PortalContentHisNewsDto portalContentHisNewsDto);
    PortalContentHisNewsVo get(Long id);
    Boolean save(PortalContentHisNewsDto portalContentHisNewsDto);
    Boolean update(PortalContentHisNewsDto portalContentHisNewsDto);
    Boolean delete(Long id);
}
