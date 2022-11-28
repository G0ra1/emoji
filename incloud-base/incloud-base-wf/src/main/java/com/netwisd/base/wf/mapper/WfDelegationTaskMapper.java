package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfDelegationTask;
import com.netwisd.base.wf.dto.WfDelegationTaskDto;
import com.netwisd.base.wf.vo.WfDelegationTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 我委托的待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:19:21
 */
@Mapper
public interface WfDelegationTaskMapper extends BaseMapper<WfDelegationTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfDelegationTaskDto
     * @return
     */
    Page<WfDelegationTaskVo> getPageList(Page page, @Param("wfDelegationTaskDto") WfDelegationTaskDto wfDelegationTaskDto);
}
