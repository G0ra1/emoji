package com.netwisd.base.portal.service;

import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.portal.entity.PortalContentAdjBanners;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentAdjBannersDto;
import com.netwisd.base.portal.vo.PortalContentAdjBannersVo;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @Description banner类内容-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 00:02:49
 */
public interface PortalContentAdjBannersService extends WfProcService<PortalContentAdjBanners,PortalContentAdjBannersDto,PortalContentAdjBannersVo> {
    Page list(PortalContentAdjBannersDto portalContentAdjBannersDto);
    List<PortalContentAdjBannersVo> lists(PortalContentAdjBannersDto portalContentAdjBannersDto);
    PortalContentAdjBannersVo get(Long id);
    Boolean save(PortalContentAdjBannersDto portalContentAdjBannersDto);
    Boolean update(PortalContentAdjBannersDto portalContentAdjBannersDto);
    Boolean delete(Long id);
    //流程删除
    Result procDel(String camundaProcinsId);
    //流程中止
    Result procStop(String camundaProcinsId);
    //流程审批完后
    Result auditSucceed(String camundaProcinsId);
}
