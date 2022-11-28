package com.netwisd.base.wf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.DeploymentBpmnDto;
import com.netwisd.base.wf.dto.WfNodeDefDto;
import com.netwisd.base.wf.entity.WfProcDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcDefDto;
import com.netwisd.base.wf.vo.WfProcDefVo;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Description 流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
public interface WfProcdefService extends IService<WfProcDef> {
    Page list(WfProcDefDto wfProcdefDto);
    List<WfProcDefVo> lists(WfProcDefDto wfProcdefDto);
    WfProcDefVo get(Long id);
    WfProcDef get(String camundaProcdefId);
    Boolean delete(Long id);
    Boolean checkDelete(Long id,boolean boo);

    /**
     * 通过id删除流程定义(删除某版本的流程定义)
     * @param id
     * @return
     */
    Boolean delVerByCamundaDefId(String id);

    /**
     * 根据camundaDefId 获取对应的 XmlDoc 信息
     * @param camundaDefId
     * @return
     */
    InputStream getXmlByCamundaId(String camundaDefId);

    /**
     * 按照发布Id 修改XML 文件 并且清除相应的缓存
     * @param deploymentId
     * @param xml
     * @return
     */
    Boolean updateXmlByDeploymentId(String deploymentId, String xml);

    /**
     * isUpdate 是编辑 还是保存 如果是编辑则 部署id 不传
     * deploymentId 部署id
     * xml String类型的xml
     * 保存或者修改Bpmn对象 以及流程定义配置信息
     */
    public String saveOrUpdateBpmnModel(Boolean isUpdate,String deploymentId, String xml, Boolean isCurrentVer,Boolean isNewVer);


    /**
     * 根据camunda 流程定义Id 和版本查询具体的 流程定义信息
     * @param camundaDefId
     * @param version
     * @return
     */
    WfProcDefVo getByDefIdAndVersion(String camundaDefId,Integer version);

    /**
     * 根据流程定义 查询出当前生效的版本 并且返回 生效版本的xml文件
     * @param camundaDefId
     * @return
     */
    String createNewVersionByCamundaId(String camundaDefId);
    /**
     * 根据流程定义 查询出当前生效的版本 并且返回 生效版本的xml文件
     * @param camundaDefId
     * @return
     */
    String getXmlInfoByCamundaId(String camundaDefId);

    /**
     * 根据流程定义Key 查询出所有对应的版本信息
     * @param camundaProcdefKey
     * @return
     */
    List<WfProcDefVo> queryVerListByCamundaKey(String camundaProcdefKey,Integer currentVersion);

    /**
     * 根据camundaDefId设置 生效版本
     * @param camundaProcdefId
     * @return
     */
    boolean setCurrentVerByCamundaId(String camundaProcdefId);

    /**
     * 根据流程定义Key 删除所有的 流程定义 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 流程定义 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);


    /**
     * 根据流程定义Key 查询出 当前激活版本
     * @param camundaDefKey
     * @return
     */
    WfProcDefVo queryCurrentVerByCamundaDefKey(String camundaDefKey);


    /**
     * 新建表单页面时 根据流程定义key 获取对应的流程定义信息 以及firtNode 表单权限信息
     * @param camundaDefKey
     * @return
     */
    WfProcDefVo createFormByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义和 nodeKey 获取表单信息 以及表单权限信息
     * @param camundaProcdefId
     * @return
     */
    WfProcDefVo getProcDefInfoByCamundaProcdefId(String camundaProcdefId, String nodeKey);

    /**
     * 根据camundaProcdefId获取流程定义信息
     * @param camundaProcdefId
     * @return
     */
    WfProcDef getProcDefByCamundaProcdefId(String camundaProcdefId);


    /**
     * 加签调用发布
     * processName 流程名称
     * bpmnModelInstance
     * @return 流程发布id
     */
    public String saveOrUpdateBpmnModel(String processName, BpmnModelInstance bpmnModelInstance);


    //----------------------------------------------4.0------------------------------------------
    //流程发布 同时解析相关流程定义
    public void deployment(DeploymentBpmnDto deploymentBpmnDto);

    /**
     * 保存流程定义以及表单事件等
     * @param wfProcDefDto  流程定义信息
     * @param isAdd  是否新建
     * @param isNewVer 是否新版本
     * @param isCurrentVer  是否启用当前版本
     */
    public void saveProcDefInfo(WfProcDefDto wfProcDefDto, boolean isAdd,Integer isNewVer, Integer isCurrentVer);


    /**
     * 根据camundaProcdefKey设置 启用 禁用
     * @param wfProcdefDto
     * @return
     */
    boolean setStartableByCamundaProcdefKey(WfProcDefDto wfProcdefDto);

    /**
     * 根据camundaProcdefKey设置 是否处理中心显示
     * @param wfProcdefDto
     * @return
     */
    boolean setBizCenterByCamundaProcdefKey(WfProcDefDto wfProcdefDto);










}
