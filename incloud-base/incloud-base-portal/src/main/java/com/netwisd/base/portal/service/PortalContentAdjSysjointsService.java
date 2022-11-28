package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjSysjoints;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 调整 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:14
 */
public interface PortalContentAdjSysjointsService extends WfProcService<PortalContentAdjSysjoints,PortalContentAdjSysjointsDto,PortalContentAdjSysjointsVo> {
    Page list(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto);
    Page lists(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto);
    PortalContentAdjSysjointsVo get(Long id);
    Boolean save(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto);
    Boolean update(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto);

    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
