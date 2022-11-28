package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalYbzTodoTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalYbzTodoTasksDto;
import com.netwisd.base.portal.vo.PortalYbzTodoTasksVo;

import java.util.List;

/**
 * @Description 集成友报账-待办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:57:18
 */
public interface PortalYbzTodoTasksService extends IService<PortalYbzTodoTasks> {
    Page list(PortalYbzTodoTasksDto portalYbzTodoTasksDto);
    List<PortalYbzTodoTasksVo> lists(PortalYbzTodoTasksDto portalYbzTodoTasksDto);
    PortalYbzTodoTasksVo get(Long id);
    Boolean deleteByYbzId(String ybzId);
    Boolean saveList(List<PortalYbzTodoTasksDto> ybzTodoTasksDtos);
}
