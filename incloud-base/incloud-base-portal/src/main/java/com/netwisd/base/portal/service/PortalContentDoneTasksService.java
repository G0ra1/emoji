package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.entity.PortalContentDoneTasks;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.vo.PortalContentDoneTasksVo;

import java.util.List;

/**
 * @Description 任务集成类内容-已办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 18:17:26
 */
public interface PortalContentDoneTasksService extends IService<PortalContentDoneTasks> {
    Page list(PortalContentDoneTasksDto portalContentDoneTasksDto);
    List<PortalContentDoneTasksVo> lists(PortalContentDoneTasksDto portalContentDoneTasksDto);
    PortalContentDoneTasksVo get(Long id);
    Boolean save(PortalContentDoneTasksDto portalContentDoneTasksDto);
    Boolean update(PortalContentDoneTasksDto portalContentDoneTasksDto);
    Boolean delete(Long id);
    //同步别的系统已办信息
    Boolean saveList(List<PortalContentDoneTasksDto> portalContentDoneTasksDtos);
    //同步别的系统待办信息删除
    Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto);

    //根据 sysBizId 和 sysBizCode 查询具体的待办信息
    PortalContentDoneTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto);
}
