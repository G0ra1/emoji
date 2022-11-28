package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.Projectjc;
import com.netwisd.biz.mdm.dto.ProjectjcDto;
import com.netwisd.biz.mdm.vo.ProjectjcVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 集采项目 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-19 14:17:49
 */
@Mapper
public interface ProjectjcMapper extends BaseMapper<Projectjc> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param projectjcDto
     * @return
     */
    Page<ProjectjcVo> getPageList(Page page, @Param("projectjcDto") ProjectjcDto projectjcDto);
}
