package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentReadTasks;
import com.netwisd.base.portal.dto.PortalContentReadTasksDto;
import com.netwisd.base.portal.vo.PortalContentReadTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 任务集成类-已阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:54:38
 */
@Mapper
public interface PortalContentReadTasksMapper extends BaseMapper<PortalContentReadTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentReadTasksDto
     * @return
     */
    Page<PortalContentReadTasksVo> getPageList(Page page, @Param("portalContentReadTasksDto") PortalContentReadTasksDto portalContentReadTasksDto);
}
