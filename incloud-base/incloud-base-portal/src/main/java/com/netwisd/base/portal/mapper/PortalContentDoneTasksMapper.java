package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentDoneTasks;
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.vo.PortalContentDoneTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 任务集成类内容-已办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 18:17:26
 */
@Mapper
public interface PortalContentDoneTasksMapper extends BaseMapper<PortalContentDoneTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentDoneTasksDto
     * @return
     */
    Page<PortalContentDoneTasksVo> getPageList(Page page, @Param("portalContentDoneTasksDto") PortalContentDoneTasksDto portalContentDoneTasksDto);
}
