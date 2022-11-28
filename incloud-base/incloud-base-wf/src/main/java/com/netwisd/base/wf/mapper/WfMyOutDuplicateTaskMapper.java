package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.vo.wf.WfMyOutDuplicateTaskVo;
import com.netwisd.base.wf.entity.WfMyOutDuplicateTask;
import com.netwisd.base.wf.dto.WfMyOutDuplicateTaskDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 我发起的传阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 16:16:11
 */
@Mapper
public interface WfMyOutDuplicateTaskMapper extends BaseMapper<WfMyOutDuplicateTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfMyOutDuplicateTaskDto
     * @return
     */
    Page<WfMyOutDuplicateTaskVo> getPageList(Page page, @Param("wfMyOutDuplicateTaskDto") WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
}
