package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfEvent;
import com.netwisd.base.wf.dto.WfEventDto;
import com.netwisd.base.wf.vo.WfEventVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
@Mapper
public interface WfEventMapper extends BaseMapper<WfEvent> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfEventDto
     * @return
     */
    Page<WfEventVo> getPageList(Page page, @Param("wfEventDto") WfEventDto wfEventDto);
}
