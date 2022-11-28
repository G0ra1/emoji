package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalYbzDoneTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalYbzDoneTasksDto;
import com.netwisd.base.portal.vo.PortalYbzDoneTasksVo;

import java.util.List;

/**
 * @Description 集成友报账-已办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:59:08
 */
public interface PortalYbzDoneTasksService extends IService<PortalYbzDoneTasks> {
    Page list(PortalYbzDoneTasksDto portalYbzDoneTasksDto);
    List<PortalYbzDoneTasksVo> lists(PortalYbzDoneTasksDto portalYbzDoneTasksDto);
    PortalYbzDoneTasksVo get(Long id);
    Boolean saveList(List<PortalYbzDoneTasksDto> ybzDoneTasksDtos);
}
