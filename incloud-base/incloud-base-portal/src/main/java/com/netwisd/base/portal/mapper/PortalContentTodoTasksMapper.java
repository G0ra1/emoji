package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.vo.PortalContentTodoTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 任务集成类内容-待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 17:07:47
 */
@Mapper
public interface PortalContentTodoTasksMapper extends BaseMapper<PortalContentTodoTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentTodoTasksDto
     * @return
     */
    Page<PortalContentTodoTasksVo> getPageList(Page page, @Param("portalContentTodoTasksDto") PortalContentTodoTasksDto portalContentTodoTasksDto);
}
