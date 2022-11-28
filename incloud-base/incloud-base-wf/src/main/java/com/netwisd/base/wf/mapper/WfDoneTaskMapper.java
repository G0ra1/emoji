package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.dto.WfDoneTaskDto;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 已办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
@Mapper
public interface WfDoneTaskMapper extends BaseMapper<WfDoneTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfDoneTaskDto
     * @return
     */
    Page<WfDoneTaskVo> getPageList(Page page, @Param("wfDoneTaskDto") WfDoneTaskDto wfDoneTaskDto);
}
