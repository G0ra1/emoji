package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentPictures;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.base.portal.vo.PortalContentPicturesVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:04
 */
public interface PortalContentPicturesService extends IService<PortalContentPictures> {
    Page list(PortalContentPicturesDto portalContentPicturesDto);
    Page lists(PortalContentPicturesDto portalContentPicturesDto);
    PortalContentPicturesVo get(Long id);
    Boolean save(PortalContentPicturesDto portalContentPicturesDto);
    Boolean update(PortalContentPicturesDto portalContentPicturesDto);
    Boolean delete(String ids);

    Page listForShow(PortalContentPicturesDto portalContentPicturesDto);
//    //流程删除
//    Result procDel(String camundaProcinsId);
//    //流程中止
//    Result procStop(String camundaProcinsId);
//    //流程审批完后
//    Result auditSucceed(String camundaProcinsId);
}
