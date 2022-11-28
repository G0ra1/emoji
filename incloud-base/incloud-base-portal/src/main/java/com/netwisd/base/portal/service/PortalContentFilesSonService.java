package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentFilesSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentFilesSonVo;
/**
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
public interface PortalContentFilesSonService extends IService<PortalContentFilesSon> {
    Page list(PortalContentFilesSonDto portalContentFilesSonDto);
    Page lists(PortalContentFilesSonDto portalContentFilesSonDto);
    PortalContentFilesSonVo get(Long id);
    Boolean save(PortalContentFilesSonDto portalContentFilesSonDto);
    Boolean update(PortalContentFilesSonDto portalContentFilesSonDto);
    Boolean delete(Long id);

    Boolean addHits(Long id);
}
