package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjFiles;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjFilesDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 调整 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:20:54
 */
public interface PortalContentAdjFilesService extends WfProcService<PortalContentAdjFiles,PortalContentAdjFilesDto,PortalContentAdjFilesVo> {
    Page list(PortalContentAdjFilesDto portalContentAdjFilesDto);
    Page lists(PortalContentAdjFilesDto portalContentAdjFilesDto);
    PortalContentAdjFilesVo get(Long id);
    Boolean save(PortalContentAdjFilesDto portalContentAdjFilesDto);
    Boolean update(PortalContentAdjFilesDto portalContentAdjFilesDto);

    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
