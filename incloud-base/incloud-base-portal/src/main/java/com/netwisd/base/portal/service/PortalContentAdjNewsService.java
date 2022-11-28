package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjNews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjNewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjNewsVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 调整 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-27 15:26:39
 */
public interface PortalContentAdjNewsService extends WfProcService<PortalContentAdjNews,PortalContentAdjNewsDto,PortalContentAdjNewsVo> {
    Page list(PortalContentAdjNewsDto portalContentAdjNewsDto);
    Page lists(PortalContentAdjNewsDto portalContentAdjNewsDto);
    PortalContentAdjNewsVo get(Long id);
    Boolean save(PortalContentAdjNewsDto portalContentAdjNewsDto);
    Boolean update(PortalContentAdjNewsDto portalContentAdjNewsDto);

    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
