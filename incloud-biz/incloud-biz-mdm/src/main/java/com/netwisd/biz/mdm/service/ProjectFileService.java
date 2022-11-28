package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ProjectFile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ProjectFileDto;
import com.netwisd.biz.mdm.vo.ProjectFileVo;
/**
 * @Description 项目附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 14:44:47
 */
public interface ProjectFileService extends IService<ProjectFile> {
    Page list(ProjectFileDto projectFileDto);
    Page lists(ProjectFileDto projectFileDto);
    ProjectFileVo get(Long id);
    Boolean save(ProjectFileDto projectFileDto);
    Boolean update(ProjectFileDto projectFileDto);
    Boolean delete(Long id);
}
