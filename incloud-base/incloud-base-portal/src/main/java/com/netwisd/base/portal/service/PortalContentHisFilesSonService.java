package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisFilesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesSonVo;
/**
 * @Description 历史 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:26:04
 */
public interface PortalContentHisFilesSonService extends IService<PortalContentHisFilesSon> {
    Page list(PortalContentHisFilesSonDto portalContentHisFilesSonDto);
    Page lists(PortalContentHisFilesSonDto portalContentHisFilesSonDto);
    PortalContentHisFilesSonVo get(Long id);
    Boolean save(PortalContentHisFilesSonDto portalContentHisFilesSonDto);
    Boolean update(PortalContentHisFilesSonDto portalContentHisFilesSonDto);
    Boolean delete(Long id);
}
