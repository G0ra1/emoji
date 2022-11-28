package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.netwisd.base.wf.entity.WfEventDef;
import com.netwisd.base.wf.dto.WfEventDefDto;
import com.netwisd.base.wf.vo.WfEventDefVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.base.wf.vo.WfEventVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@Mapper
public interface WfEventDefMapper extends BaseMapper<WfEventDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfEventDefDto
     * @return
     */
    Page<WfEventDefVo> getPageList(Page page, @Param("wfEventDefDto") WfEventDefDto wfEventDefDto);

    /*@Select("SELECT ev.* from incloud_base_wf_event ev right join incloud_base_wf_event_def evef on evef.event_id = ev.id ${ew.customSqlSegment}")
    List<WfEventRuntimeVo> getEventByConditions(@Param(Constants.WRAPPER) Wrapper wrapper);*/

    List<WfEventRuntimeVo> getEventByConditions(@Param("camundaProcdefId")String camundaProcdefId, @Param("camundaNodeDefId")String camundaNodeDefId,
                                                @Param("eventType")Integer eventType, @Param("eventBindType")String eventBindType);

    List<WfEventRuntimeVo> getMultiEventByConditions(@Param("camundaProcdefId")String camundaProcdefId, @Param("camundaNodeDefId")String camundaNodeDefId,
                                                     @Param("eventType")Integer eventType,@Param("eventBindTypes")List<String> eventBindTypes);
}
