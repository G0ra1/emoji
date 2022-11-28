package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.dto.WfTodoTaskDto;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
@Mapper
public interface WfTodoTaskMapper extends BaseMapper<WfTodoTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfTodoTaskDto
     * @return
     */
    Page<WfTodoTaskVo> getPageList(Page page, @Param("wfTodoTaskDto") WfTodoTaskDto wfTodoTaskDto);

    //查询候选人待办数据量
    int queryTodoTaskNum(@Param("wfTodoTaskDto") WfTodoTaskDto wfTodoTaskDto);
}
