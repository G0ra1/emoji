package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfNodeDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfNodeDefDto;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
public interface WfNodeDefService extends BatchService<WfNodeDef> {
    Page list(WfNodeDefDto wfNodeDefDto);
    Page lists(WfNodeDefDto wfNodeDefDto);
    WfNodeDefVo get(Long id);
    Boolean save(WfNodeDefDto wfNodeDefDto);
    Boolean update(WfNodeDefDto wfNodeDefDto);
    Boolean delete(Long id);

    /**
     * 根据camunda流程定义id 和camunda 节点定义id 获取节点信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId camundaNodeDefId
     */
    WfNodeDefVo getNodeByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId);

    WfNodeDefVo getEntity(String camundaProcDefId,String camundaNodeDefId);

    /**
     * 保存或者编辑xml 网关信息、开始节点、结束节点
     * @param isUpdate 是否调整
     * @param wfNodeDefDtoList 封装对象
     */
    void saveXml2WfNodeDef(boolean isUpdate, List<WfNodeDefDto> wfNodeDefDtoList,Integer nodeType,String camundaSubProcessNodeDefId);

    /**
     * 保存或者编辑xml 用户节点
     * @param isUpdate 是否调整
     * @param userNodeDefDtoList 封装对象
     */
    void saveXml2UserTaskDef(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList,String camundaSubProcessNodeDefId);

    /**
     * 根据流程定义Key 删除所有的 节点定义 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 节点定义 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 保存或者编辑xml 子流程节点解析
     * @param isUpdate 是否调整
     * @param userNodeDefDtoList 封装对象
     */
    void saveXml2SubProcessDef(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList);

    /**
     * 保存或者编辑xml 子流程节点解析(CallActivity)
     * @param isUpdate 是否调整
     * @param userNodeDefDtoList 封装对象
     */
    void saveXml2CallActivity(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList,String camundaSubProcessNodeDefId);

    //------------------------4.0------------------------------------
    //保存节点信息
    void saveNodeInfo(List<WfNodeDefDto> nodeDefDto,boolean isNew);

    //根据camunda流程定义id 查询节点信息
    WfNodeDef geNodeByCmdProcIdAndCmdNodeId(String camundaProcdefId, String camundaNodeId);


}
