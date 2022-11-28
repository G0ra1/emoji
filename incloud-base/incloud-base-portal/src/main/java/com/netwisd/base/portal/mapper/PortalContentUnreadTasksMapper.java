package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentUnreadTasks;
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.vo.PortalContentUnreadTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 任务集成类-待阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:09:00
 */
@Mapper
public interface PortalContentUnreadTasksMapper extends BaseMapper<PortalContentUnreadTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentUnreadTasksDto
     * @return
     */
    Page<PortalContentUnreadTasksVo> getPageList(Page page, @Param("portalContentUnreadTasksDto") PortalContentUnreadTasksDto portalContentUnreadTasksDto);
}
