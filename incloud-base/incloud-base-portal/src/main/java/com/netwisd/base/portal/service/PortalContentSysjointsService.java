package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentSysjoints;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:18:54
 */
public interface PortalContentSysjointsService extends IService<PortalContentSysjoints> {
    Page list(PortalContentSysjointsDto portalContentSysjointsDto);
    Page lists(PortalContentSysjointsDto portalContentSysjointsDto);
    PortalContentSysjointsVo get(Long id);
    Boolean save(PortalContentSysjointsDto portalContentSysjointsDto);
    Boolean update(PortalContentSysjointsDto portalContentSysjointsDto);
    Boolean delete(String ids);

    Page listForShow(PortalContentSysjointsDto portalContentSysjointsDto);
//    //流程删除
//    Result procDel(String camundaProcinsId);
//    //流程中止
//    Result procStop(String camundaProcinsId);
//    //流程审批完后
//    Result auditSucceed(String camundaProcinsId);
}
