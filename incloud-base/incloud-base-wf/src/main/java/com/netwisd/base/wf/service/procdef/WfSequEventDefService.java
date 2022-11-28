package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfSequEventDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfSequEventDefDto;
import com.netwisd.base.wf.vo.WfSequEventDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
public interface WfSequEventDefService extends BatchService<WfSequEventDef> {
    Page list(WfSequEventDefDto wfSequEventDefDto);
    Page lists(WfSequEventDefDto wfSequEventDefDto);
    WfSequEventDefVo get(Long id);
    Boolean save(WfSequEventDefDto wfSequEventDefDto);
    Boolean update(WfSequEventDefDto wfSequEventDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的 序列流-事件 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 序列流-事件 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 保存 解析xml对象
     * @param wfSequEventDefDtoList
     */
    public void saveXml2SequEventDef(List<WfSequEventDefDto> wfSequEventDefDtoList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 事件以及对应的参数
     * @param camundaSequId
     */
    public void delSeqEventByCmdNodeIdAndCmdProcdefId(String camundaSequId,String camundaProcdefId);
}
