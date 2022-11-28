package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.entity.PortalContentMydraftTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentMydraftTasksDto;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.vo.PortalContentMydraftTasksVo;

import java.util.List;

/**
 * @Description 任务集成类-我发起的任务 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 15:27:43
 */
public interface PortalContentMydraftTasksService extends IService<PortalContentMydraftTasks> {
    Page list(PortalContentMydraftTasksDto portalContentMydraftTasksDto);
    List<PortalContentMydraftTasksVo> lists(PortalContentMydraftTasksDto portalContentMydraftTasksDto);
    PortalContentMydraftTasksVo get(Long id);
    Boolean save(PortalContentMydraftTasksDto portalContentMydraftTasksDto);
    Boolean update(PortalContentMydraftTasksDto portalContentMydraftTasksDto);
    Boolean delete(Long id);

    //同步的 我发起任务的数据
    Boolean saveList(List<PortalContentMydraftTasksDto> portalContentMydraftTasksDtos);

    //删除我发起的任务
    public Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto);

    //根据 sysBizId 和 sysBizCode 查询具体的待办信息
    PortalContentMydraftTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto);
}
