package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfSequEventParamDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfSequEventParamDefDto;
import com.netwisd.base.wf.vo.WfSequEventParamDefVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 流程定义-序列流-事件-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:56:16
 */
public interface WfSequEventParamDefService extends BatchService<WfSequEventParamDef> {
    Page list(WfSequEventParamDefDto wfSequEventParamDefDto);
    Page lists(WfSequEventParamDefDto wfSequEventParamDefDto);
    WfSequEventParamDefVo get(Long id);
    Boolean save(WfSequEventParamDefDto wfSequEventParamDefDto);
    Boolean update(WfSequEventParamDefDto wfSequEventParamDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的 序列流-事件-参数 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 序列流-事件-参数 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);
}
