package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfReceiveNotifyTaskVo;
import com.netwisd.base.wf.dto.WfReceiveNotifyTaskDto;
import com.netwisd.base.wf.entity.WfReceiveNotifyTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 我收到的知会 功能描述...
 * @author 云数网讯 XHL
 * @date 2022-02-14 09:39:14
 */
@Mapper
public interface WfReceiveNotifyTaskMapper extends BaseMapper<WfReceiveNotifyTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfReceiveNotifyTaskDto
     * @return
     */
    Page<WfReceiveNotifyTaskVo> getPageList(Page page, @Param("wfReceiveNotifyTaskDto") WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
}
