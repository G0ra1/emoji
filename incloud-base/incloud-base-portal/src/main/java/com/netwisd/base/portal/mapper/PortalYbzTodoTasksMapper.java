package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalYbzTodoTasks;
import com.netwisd.base.portal.dto.PortalYbzTodoTasksDto;
import com.netwisd.base.portal.vo.PortalYbzTodoTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 集成友报账-待办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:57:18
 */
@Mapper
public interface PortalYbzTodoTasksMapper extends BaseMapper<PortalYbzTodoTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalYbzTodoTasksDto
     * @return
     */
    Page<PortalYbzTodoTasksVo> getPageList(Page page, @Param("portalYbzTodoTasksDto") PortalYbzTodoTasksDto portalYbzTodoTasksDto);
}
