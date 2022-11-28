package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalYbzDoneTasks;
import com.netwisd.base.portal.dto.PortalYbzDoneTasksDto;
import com.netwisd.base.portal.vo.PortalYbzDoneTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 集成友报账-已办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:59:08
 */
@Mapper
public interface PortalYbzDoneTasksMapper extends BaseMapper<PortalYbzDoneTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalYbzDoneTasksDto
     * @return
     */
    Page<PortalYbzDoneTasksVo> getPageList(Page page, @Param("portalYbzDoneTasksDto") PortalYbzDoneTasksDto portalYbzDoneTasksDto);
}
