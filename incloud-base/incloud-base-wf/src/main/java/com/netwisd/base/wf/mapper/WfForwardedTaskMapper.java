package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfForwardedTask;
import com.netwisd.base.wf.dto.WfForwardedTaskDto;
import com.netwisd.base.wf.vo.WfForwardedTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 我发出的转办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:43:53
 */
@Mapper
public interface WfForwardedTaskMapper extends BaseMapper<WfForwardedTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfForwardedTaskDto
     * @return
     */
    Page<WfForwardedTaskVo> getPageList(Page page, @Param("wfForwardedTaskDto") WfForwardedTaskDto wfForwardedTaskDto);
}
