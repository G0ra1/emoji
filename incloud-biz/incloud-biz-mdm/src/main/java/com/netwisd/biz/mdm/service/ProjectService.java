package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.Project;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ProjectDto;
import com.netwisd.biz.mdm.vo.ProjectVo;

import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
public interface ProjectService extends IService<Project> {
    Page list(ProjectDto projectDto);
    List<ProjectVo> lists(ProjectDto projectDto);
    ProjectVo get(Long id);
    Boolean save(ProjectDto projectDto);
    Boolean update(ProjectDto projectDto);
    Boolean delete(Long id);
    void saveList(List<ProjectDto> projectDtos);
    Boolean delList(List<ProjectDto> projectDtos);
}
