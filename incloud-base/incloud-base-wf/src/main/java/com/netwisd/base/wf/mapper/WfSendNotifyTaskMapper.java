package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfSendNotifyTask;
import com.netwisd.base.wf.dto.WfSendNotifyTaskDto;
import com.netwisd.base.wf.vo.WfSendNotifyTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 我发出的知会 功能描述...
 * @author 云数网讯 XHK@netwisd.com
 * @date 2022-03-09 10:06:02
 */
@Mapper
public interface WfSendNotifyTaskMapper extends BaseMapper<WfSendNotifyTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfSendNotifyTaskDto
     * @return
     */
    Page<WfSendNotifyTaskVo> getPageList(Page page, @Param("wfSendNotifyTaskDto") WfSendNotifyTaskDto wfSendNotifyTaskDto);
}
