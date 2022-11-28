package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.entity.PortalContentUnreadTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.vo.PortalContentUnreadTasksVo;

import java.util.List;

/**
 * @Description 任务集成类-待阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:09:00
 */
public interface PortalContentUnreadTasksService extends IService<PortalContentUnreadTasks> {
    Page list(PortalContentUnreadTasksDto portalContentUnreadTasksDto);
    List<PortalContentUnreadTasksVo> lists(PortalContentUnreadTasksDto portalContentUnreadTasksDto);
    PortalContentUnreadTasksVo get(Long id);
    Boolean save(PortalContentUnreadTasksDto portalContentUnreadTasksDto);
    Boolean update(PortalContentUnreadTasksDto portalContentUnreadTasksDto);
    Boolean delete(Long id);

    //同步别的系统 待阅 信息
    Boolean saveList(List<PortalContentUnreadTasksDto> portalContentUnreadTasksDtos);

    //根据 sysBizId 和 sysBizCode 查询具体的待办信息
    PortalContentUnreadTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto);

    //同步别的系统待办信息删除
    Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto);
}
