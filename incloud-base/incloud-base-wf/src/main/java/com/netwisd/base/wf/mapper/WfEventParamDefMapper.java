package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.netwisd.base.wf.entity.WfEventParamDef;
import com.netwisd.base.wf.dto.WfEventParamDefDto;
import com.netwisd.base.wf.vo.WfEventParamDefVo;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 事件定义参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:00:37
 */
@Mapper
public interface WfEventParamDefMapper extends BaseMapper<WfEventParamDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfEventParamDefDto
     * @return
     */
    Page<WfEventParamDefVo> getPageList(Page page, @Param("wfEventParamDefDto") WfEventParamDefDto wfEventParamDefDto);

    List<WfEventParamRuntimeVo> getEventParamsByConditions(@Param("eventId")Long eventId,
                                                           @Param("camundaProcdefId")String camundaProcdefId, @Param("camundaNodeDefId")String camundaNodeDefId,
                                                           @Param("eventType")Integer eventType, @Param("eventBindType")String eventBindType);
}
