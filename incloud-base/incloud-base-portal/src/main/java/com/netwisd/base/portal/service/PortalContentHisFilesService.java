package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisFiles;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisFilesDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesVo;
/**
 * @Description 历史 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:24:56
 */
public interface PortalContentHisFilesService extends IService<PortalContentHisFiles> {
    Page list(PortalContentHisFilesDto portalContentHisFilesDto);
    Page lists(PortalContentHisFilesDto portalContentHisFilesDto);
    PortalContentHisFilesVo get(Long id);
    Boolean save(PortalContentHisFilesDto portalContentHisFilesDto);
    Boolean update(PortalContentHisFilesDto portalContentHisFilesDto);
    Boolean delete(Long id);
}
