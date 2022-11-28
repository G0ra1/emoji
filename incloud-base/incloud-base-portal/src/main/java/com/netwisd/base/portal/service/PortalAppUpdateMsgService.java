package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalAppUpdateMsg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalAppUpdateMsgDto;
import com.netwisd.base.portal.vo.PortalAppUpdateMsgVo;
/**
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
public interface PortalAppUpdateMsgService extends IService<PortalAppUpdateMsg> {
    Page list(PortalAppUpdateMsgDto portalAppUpdateMsgDto);
    Page lists(PortalAppUpdateMsgDto portalAppUpdateMsgDto);
    PortalAppUpdateMsgVo get(Long id);
    Boolean save(PortalAppUpdateMsgDto portalAppUpdateMsgDto);
    Boolean update(PortalAppUpdateMsgDto portalAppUpdateMsgDto);
    Boolean delete(Long id);

    PortalAppUpdateMsgVo getNewVersion();
}
