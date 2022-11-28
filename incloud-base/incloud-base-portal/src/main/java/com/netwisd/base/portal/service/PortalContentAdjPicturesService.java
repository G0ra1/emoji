package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjPictures;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 调整 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:30
 */
public interface PortalContentAdjPicturesService extends WfProcService<PortalContentAdjPictures,PortalContentAdjPicturesDto,PortalContentAdjPicturesVo> {
    Page list(PortalContentAdjPicturesDto portalContentAdjPicturesDto);
    Page lists(PortalContentAdjPicturesDto portalContentAdjPicturesDto);
    PortalContentAdjPicturesVo get(Long id);
    Boolean save(PortalContentAdjPicturesDto portalContentAdjPicturesDto);
    Boolean update(PortalContentAdjPicturesDto portalContentAdjPicturesDto);

    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
