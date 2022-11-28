package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjVideos;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjVideosDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosVo;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @Description  视频类内容发布-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 01:42:07
 */
public interface PortalContentAdjVideosService extends WfProcService<PortalContentAdjVideos,PortalContentAdjVideosDto,PortalContentAdjVideosVo> {
    Page list(PortalContentAdjVideosDto portalContentAdjVideosDto);
    List<PortalContentAdjVideosVo> lists(PortalContentAdjVideosDto portalContentAdjVideosDto);
    PortalContentAdjVideosVo get(Long id);
    Boolean save(PortalContentAdjVideosDto portalContentAdjVideosDto);
    Boolean update(PortalContentAdjVideosDto portalContentAdjVideosDto);
    Boolean delete(Long id);
    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
