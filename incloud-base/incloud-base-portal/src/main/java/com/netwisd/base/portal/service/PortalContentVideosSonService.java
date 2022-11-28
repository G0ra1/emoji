package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentVideosSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentVideosSonVo;

import java.util.List;

/**
 * @Description  视频类型内容发布子表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-05 21:48:10
 */
public interface PortalContentVideosSonService extends IService<PortalContentVideosSon> {
    Page list(PortalContentVideosSonDto portalContentVideosSonDto);
    List<PortalContentVideosSonVo> lists(PortalContentVideosSonDto portalContentVideosSonDto);
    PortalContentVideosSonVo get(Long id);
    Boolean save(PortalContentVideosSonDto portalContentVideosSonDto);
    Boolean update(PortalContentVideosSonDto portalContentVideosSonDto);
    Boolean delete(Long id);
    //通过id修改点击量
    Boolean upHits(Long id);
}
