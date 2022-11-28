package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfEventParamDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfEventParamDefDto;
import com.netwisd.base.wf.vo.WfEventParamDefVo;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 事件定义参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:00:37
 */
public interface WfEventParamDefService extends BatchService<WfEventParamDef> {
    Page list(WfEventParamDefDto wfEventParamDefDto);
    Page lists(WfEventParamDefDto wfEventParamDefDto);
    WfEventParamDefVo get(Long id);
    Boolean save(WfEventParamDefDto wfEventParamDefDto);
    Boolean update(WfEventParamDefDto wfEventParamDefDto);
    Boolean delete(Long id);
    List<WfEventParamRuntimeVo> getEventParamsByConditions(Long eventId,String camundaProcdefId, String camundaNodeDefId, Integer eventType, String eventBindType);

    /**
     * 根据流程定义Key 删除所有的 事件-参数 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 事件-参数 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);
}
