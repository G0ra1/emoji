package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ProjectFile;
import com.netwisd.biz.mdm.dto.ProjectFileDto;
import com.netwisd.biz.mdm.vo.ProjectFileVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 项目附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 14:44:47
 */
@Mapper
public interface ProjectFileMapper extends BaseMapper<ProjectFile> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param projectFileDto
     * @return
     */
    Page<ProjectFileVo> getPageList(Page page, @Param("projectFileDto") ProjectFileDto projectFileDto);
}
