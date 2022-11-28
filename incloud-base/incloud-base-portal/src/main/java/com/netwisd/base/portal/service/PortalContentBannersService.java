package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentBanners;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @Description    banner类内容发布 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-19 15:32:39
 */
public interface PortalContentBannersService extends IService<PortalContentBanners> {
    Page list(PortalContentBannersDto portalContentBannersDto);
    List<PortalContentBannersVo> lists(PortalContentBannersDto portalContentBannersDto);
    PortalContentBannersVo get(Long id);
    Boolean save(PortalContentBannersDto portalContentBannersDto);
    Boolean update(PortalContentBannersDto portalContentBannersDto);
    Boolean delete(String ids);
    //通过id修改点击量
    Boolean upHits(Long id);
   /* //流程删除//todo 流程取消暂时注释
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);*/
}
