package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.Project;
import com.netwisd.biz.mdm.dto.ProjectDto;
import com.netwisd.biz.mdm.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param projectDto
     * @return
     */
    Page<ProjectVo> getPageList(Page page, @Param("projectDto") ProjectDto projectDto);
}
