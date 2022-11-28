package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.vo.PortalContentTodoTasksVo;

import java.util.List;

/**
 * @Description 任务集成类内容-待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 17:07:47
 */
public interface PortalContentTodoTasksService extends IService<PortalContentTodoTasks> {
    Page list(PortalContentTodoTasksDto portalContentTodoTasksDto);
    List<PortalContentTodoTasksVo> lists(PortalContentTodoTasksDto portalContentTodoTasksDto);
    PortalContentTodoTasksVo get(Long id);
    Boolean save(PortalContentTodoTasksDto portalContentTodoTasksDto);
    Boolean update(PortalContentTodoTasksDto portalContentTodoTasksDto);
    Boolean delete(Long id);
    //同步别的系统待办信息
    Boolean saveList(List<PortalContentTodoTasksDto> portalContentTodoTasksDtos);
    //同步别的系统待办信息删除
    Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto);

    //根据 sysBizId 和 sysBizCode 查询具体的待办信息
    PortalContentTodoTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto);

    //根据业务id  同步签收
    Boolean cliamHandle(ApiTaskDto apiTaskDto);

    //删除待办、删除已办、删除待阅、删除已阅
    Boolean delAllBySysBizIdAndCode(ApiTaskDto apiTaskDto);

}
