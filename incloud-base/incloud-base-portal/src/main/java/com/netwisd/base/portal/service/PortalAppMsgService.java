package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalAppMsg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalAppMsgDto;
import com.netwisd.base.portal.vo.PortalAppMsgVo;
/**
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
public interface PortalAppMsgService extends IService<PortalAppMsg> {
    Page list(PortalAppMsgDto portalAppMsgDto);
    Page lists(PortalAppMsgDto portalAppMsgDto);
    PortalAppMsgVo get(Long id);
    Boolean save(PortalAppMsgDto portalAppMsgDto);
    Boolean update(PortalAppMsgDto portalAppMsgDto);
    Boolean delete(Long id);

    String getState(PortalAppMsgDto portalAppMsgDto);
}
