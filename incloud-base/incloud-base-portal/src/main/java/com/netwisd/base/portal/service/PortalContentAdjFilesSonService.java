package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentAdjFilesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesSonVo;
/**
 * @Description 调整 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:23:45
 */
public interface PortalContentAdjFilesSonService extends IService<PortalContentAdjFilesSon> {
    Page list(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto);
    Page lists(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto);
    PortalContentAdjFilesSonVo get(Long id);
    Boolean save(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto);
    Boolean update(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto);
    Boolean delete(Long id);
}
