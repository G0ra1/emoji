package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentFiles;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentFilesDto;
import com.netwisd.base.portal.vo.PortalContentFilesVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
public interface PortalContentFilesService extends IService<PortalContentFiles> {
    Page list(PortalContentFilesDto portalContentFilesDto);
    Page lists(PortalContentFilesDto portalContentFilesDto);
    PortalContentFilesVo get(Long id);
    Boolean save(PortalContentFilesDto portalContentFilesDto);
    Boolean update(PortalContentFilesDto portalContentFilesDto);
    Boolean delete(String ids);

    Page listForShow(PortalContentFilesDto portalContentFilesDto);
//    //流程删除
//    Result procDel(String camundaProcinsId);
//    //流程中止
//    Result procStop(String camundaProcinsId);
//    //流程审批完后
//    Result auditSucceed(String camundaProcinsId);
}
