package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentMydraftTasks;
import com.netwisd.base.portal.dto.PortalContentMydraftTasksDto;
import com.netwisd.base.portal.vo.PortalContentMydraftTasksVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 任务集成类-我发起的任务 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 15:27:43
 */
@Mapper
public interface PortalContentMydraftTasksMapper extends BaseMapper<PortalContentMydraftTasks> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentMydraftTasksDto
     * @return
     */
    Page<PortalContentMydraftTasksVo> getPageList(Page page, @Param("portalContentMydraftTasksDto") PortalContentMydraftTasksDto portalContentMydraftTasksDto);
}
