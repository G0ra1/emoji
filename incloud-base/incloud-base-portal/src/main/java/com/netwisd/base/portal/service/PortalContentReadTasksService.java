package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.entity.PortalContentReadTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentReadTasksDto;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.vo.PortalContentReadTasksVo;

import java.util.List;

/**
 * @Description 任务集成类-已阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:54:38
 */
public interface PortalContentReadTasksService extends IService<PortalContentReadTasks> {
    Page list(PortalContentReadTasksDto portalContentReadTasksDto);
    List<PortalContentReadTasksVo> lists(PortalContentReadTasksDto portalContentReadTasksDto);
    PortalContentReadTasksVo get(Long id);
    Boolean save(PortalContentReadTasksDto portalContentReadTasksDto);
    Boolean update(PortalContentReadTasksDto portalContentReadTasksDto);
    Boolean delete(Long id);

    //同步别的系统 已阅数据 信息
    Boolean saveList(List<PortalContentReadTasksDto> portalContentReadTasksDtos);
    //同步别的系统 已阅数据 信息删除
    Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto);
    //根据 sysBizId 和 sysBizCode 查询具体的待办信息
    PortalContentReadTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto);
}
