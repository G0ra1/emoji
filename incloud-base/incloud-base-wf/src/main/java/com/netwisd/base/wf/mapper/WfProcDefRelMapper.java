package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfProcDefRel;
import com.netwisd.base.wf.dto.WfProcDefRelDto;
import com.netwisd.base.wf.vo.WfProcDefRelVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
@Mapper
public interface WfProcDefRelMapper extends BaseMapper<WfProcDefRel> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfProcDefRelDto
     * @return
     */
    Page<WfProcDefRelVo> getPageList(Page page, @Param("wfProcDefRelDto") WfProcDefRelDto wfProcDefRelDto);
}
