package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisBanners;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisBannersDto;
import com.netwisd.base.portal.vo.PortalContentHisBannersVo;

import java.util.List;

/**
 * @Description banner类内容-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 03:09:59
 */
public interface PortalContentHisBannersService extends IService<PortalContentHisBanners> {
    Page list(PortalContentHisBannersDto portalContentHisBannersDto);
    List<PortalContentHisBannersVo> lists(PortalContentHisBannersDto portalContentHisBannersDto);
    PortalContentHisBannersVo get(Long id);
    Boolean save(PortalContentHisBannersDto portalContentHisBannersDto);
    Boolean update(PortalContentHisBannersDto portalContentHisBannersDto);
    Boolean delete(Long id);
}
