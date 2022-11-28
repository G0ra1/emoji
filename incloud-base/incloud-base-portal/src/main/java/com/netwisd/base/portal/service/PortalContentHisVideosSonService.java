package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisVideosSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosSonVo;
/**
 * @Description  视频类型内容发布-历史表子表  功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 14:08:44
 */
public interface PortalContentHisVideosSonService extends IService<PortalContentHisVideosSon> {
    Page list(PortalContentHisVideosSonDto portalContentHisVideosSonDto);
    Page lists(PortalContentHisVideosSonDto portalContentHisVideosSonDto);
    PortalContentHisVideosSonVo get(Long id);
    Boolean save(PortalContentHisVideosSonDto portalContentHisVideosSonDto);
    Boolean update(PortalContentHisVideosSonDto portalContentHisVideosSonDto);
    Boolean delete(Long id);
}
