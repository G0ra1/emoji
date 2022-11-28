package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.PortalContentSysjointsDto;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentVideos;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.vo.PortalContentVideosVo;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @Description  视频类内容发布 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-23 14:08:28
 */
public interface PortalContentVideosService extends IService<PortalContentVideos> {
    Page list(PortalContentVideosDto portalContentVideosDto);
    List<PortalContentVideosVo> lists(PortalContentVideosDto portalContentVideosDto);
    PortalContentVideosVo get(Long id);
    Boolean save(PortalContentVideosDto portalContentVideosDto);
    Boolean update(PortalContentVideosDto portalContentVideosDto);
    Boolean delete(String id);

    Page listForShow(PortalContentVideosDto portalContentVideosDto);
   /* //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);*/
}
