package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentPicnews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentPicnewsVo;

/**
 * @Description 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-20 10:09:51
 */
public interface PortalContentPicnewsService extends IService<PortalContentPicnews> {
    Page list(PortalContentPicnewsDto portalContentPicnewsDto);
    Page lists(PortalContentPicnewsDto portalContentPicnewsDto);
    PortalContentPicnewsVo get(Long id);
    Boolean save(PortalContentPicnewsDto portalContentPicnewsDto);
    Boolean update(PortalContentPicnewsDto portalContentPicnewsDto);
    Boolean delete(String ids);

    //增加点击量
    Boolean addHits(Long id);

    //流程删除
//    Result procDel(String camundaProcinsId);
//    //流程中止
//    Result procStop(String camundaProcinsId);
//    //流程审批完后
//    Result auditSucceed(String camundaProcinsId);
}
