package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.dto.WfMyInDuplicateTaskDto;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 传阅任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 09:39:14
 */
@Mapper
public interface WfMyInDuplicateTaskMapper extends BaseMapper<WfMyInDuplicateTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfMyInDuplicateTaskDto
     * @return
     */
    Page<WfMyInDuplicateTaskVo> getPageList(Page page, @Param("wfMyInDuplicateTaskDto") WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
}
