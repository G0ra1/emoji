package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjPicnews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicnewsVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 调整 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:25:00
 */
public interface PortalContentAdjPicnewsService extends WfProcService<PortalContentAdjPicnews,PortalContentAdjPicnewsDto,PortalContentAdjPicnewsVo> {
    Page list(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto);
    Page lists(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto);
    PortalContentAdjPicnewsVo get(Long id);
    Boolean save(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto);
    Boolean update(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto);

    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
