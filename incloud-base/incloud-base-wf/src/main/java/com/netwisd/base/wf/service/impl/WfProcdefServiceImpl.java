package com.netwisd.base.wf.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.model.vo.ModelFormVo;
import com.netwisd.base.wf.constants.EngineErrEnum;
import com.netwisd.base.wf.constants.EventTypeSignEnum;
import com.netwisd.base.wf.constants.NodeTypeEnum;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.dto.*;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.feign.ModelFeignClient;
import com.netwisd.base.wf.mapper.WfProcdefMapper;
import com.netwisd.base.wf.service.*;
import com.netwisd.base.wf.service.other.WfButtonDefService;
import com.netwisd.base.wf.service.other.WfFormDefService;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import com.netwisd.base.wf.service.procdef.*;
import com.netwisd.base.wf.util.DomUtils;
import com.netwisd.base.wf.util.XmlUtils;
import com.netwisd.base.wf.vo.*;
import com.netwisd.base.wf.xml.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.netwisd.base.wf.util.XmlUtils.tagContentCheck;

/**
 * @Description 流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
@Service
@Slf4j
public class WfProcdefServiceImpl extends BatchServiceImpl<WfProcdefMapper, WfProcDef> implements WfProcdefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcdefMapper wfProcdefMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private WfNodeDefService wfNodeDefService;

    @Autowired
    private WfEventDefService wfEventDefService;

    @Autowired
    private WfEventParamDefService wfEventParamDefService;

    @Autowired
    private WfButtonDefService wfButtonDefService;

    @Autowired
    private WfFormDefService wfFormDefService;

    @Autowired
    private WfExpreUserDefService wfExpreUserDefService;

    @Autowired
    private WfFormFieldsDefService wfFormFieldsDefService;

    @Autowired
    private WfVarDefService wfVarDefService;

    @Autowired
    private WfSequDefService wfSequDefService;

    @Autowired
    private WfExpreSequDefService wfExpreSequDefService;

    @Autowired
    private WfSequEventDefService wfSequEventDefService;

    @Autowired
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelFeignClient modelFeignClient;

    final static String firstNode = "firstNode";

    /**
     * 单表简单查询操作
     * @param wfProcdefDto
     * @return
     */
    @Override
    public Page list(WfProcDefDto wfProcdefDto) {
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getProcdefTypeId()), WfProcDef::getProcdefTypeId, wfProcdefDto.getProcdefTypeId())//流程分类
                .eq(StrUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefId()), WfProcDef::getCamundaProcdefId, wfProcdefDto.getCamundaProcdefId())//流程定义key
                .eq(WfProcDef::getCurrentVersion, YesNo.YES.code) //只取生效版本
                .like(StrUtil.isNotEmpty(wfProcdefDto.getVersionTag()), WfProcDef::getVersionTag, wfProcdefDto.getVersionTag())
                .like(StrUtil.isNotEmpty(wfProcdefDto.getProcdefName()), WfProcDef::getProcdefName, wfProcdefDto.getProcdefName());
        Page<WfProcDef> page = wfProcdefMapper.selectPage(wfProcdefDto.getPage(),queryWrapper);
        Page<WfProcDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfProcDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param wfProcdefDto
     * @return
     */
    @Override
    public List<WfProcDefVo> lists(WfProcDefDto wfProcdefDto) {
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getProcdefTypeId()), WfProcDef::getProcdefTypeId, wfProcdefDto.getProcdefTypeId())//流程分类
                .eq(StrUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefId()), WfProcDef::getCamundaProcdefId, wfProcdefDto.getCamundaProcdefId())//流程定义key
                .eq(WfProcDef::getCurrentVersion, YesNo.YES.code) //只取生效版本
                .eq(WfProcDef::getIsBizCenter, YesNo.YES.code) //只取业务中心展示
                .like(StrUtil.isNotEmpty(wfProcdefDto.getVersionTag()), WfProcDef::getVersionTag, wfProcdefDto.getVersionTag())
                .like(StrUtil.isNotEmpty(wfProcdefDto.getProcdefName()), WfProcDef::getProcdefName, wfProcdefDto.getProcdefName());
        List<WfProcDef> list = wfProcdefMapper.selectList(queryWrapper);
        List<WfProcDefVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, WfProcDefVo.class);
        }
        return listVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public WfProcDefVo get(Long id) {
        WfProcDef wfProcdef = super.getById(id);
        if(null == wfProcdef) {
            throw new IncloudException("不存在的流程定义信息");
        }
        WfProcDefVo wfProcdefVo = dozerMapper.map(wfProcdef, WfProcDefVo.class);
        log.debug("查询成功");
        return wfProcdefVo;
    }

    @Override
    public WfProcDef get(String camundaProcdefId) {
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaProcdefId);
        WfProcDef one = this.getOne(queryWrapper);
        return one;
    }

    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过ID删除
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return checkDelete(id,false);

    }

    @Override
    @Transactional
    public Boolean checkDelete(Long id,boolean boo) {
        log.debug("通过id删除流程定义(删除所有的对应的流程信息-包含所有版本).id参数：" + id);
        //查询出这条数据的详细信息
        WfProcDef wfProcdef = super.getById(id);
        if(null != wfProcdef) {
            //先检查是否存在 正在运行的实例 如果有实例暂时不能调整
            if(boo) {
                List<Task> taskList = taskService.createTaskQuery().processDefinitionId(wfProcdef.getCamundaProcdefId()).list();
                if(CollectionUtil.isNotEmpty(taskList)) {
                    throw new IncloudException("存在正在运行的流程实例，不能进行流程定义编辑！");
                }
            }
            wfButtonDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfEventDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfExpreSequDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfExpreUserDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfFormDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfFormFieldsDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfNodeDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfSequDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfSequEventDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            wfVarDefService.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
            LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WfProcDef::getCamundaProcdefKey, wfProcdef.getCamundaProcdefKey());
            List<WfProcDef> wfProcDefList = wfProcdefMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(wfProcDefList)) {
                for (WfProcDef wfProcDef : wfProcDefList) {
                    // camunda 调用流程删除
                    repositoryService.deleteDeployment(wfProcDef.getDeploymentId());
                }
            }
            this.deleteByCamundaDefKey(wfProcdef.getCamundaProcdefKey());
        } else {
            throw new IncloudException("没有对应的流程定义信息！");
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delVerByCamundaDefId(String camundaDefId) {
        log.debug("通过camundaProcdefId删除流程定义(删除某版本的流程定义).camundaDefId参数：" + camundaDefId);
        //查询出这条数据的详细信息
        if(StringUtils.isNotBlank(camundaDefId)) {
            //先检查是否存在 正在运行的实例 如果有实例暂时不能调整
            List<Task> taskList = taskService.createTaskQuery().processDefinitionId(camundaDefId).list();
            if(CollectionUtil.isNotEmpty(taskList)) {
                throw new IncloudException("存在正在运行的流程实例，不能进行流程定义编辑！");
            }
            wfButtonDefService.deleteByCamundaDefId(camundaDefId);
            wfEventDefService.deleteByCamundaDefId(camundaDefId);
            wfExpreSequDefService.deleteByCamundaDefId(camundaDefId);
            wfExpreUserDefService.deleteByCamundaDefId(camundaDefId);
            wfFormDefService.deleteByCamundaDefId(camundaDefId);
            wfFormFieldsDefService.deleteByCamundaDefId(camundaDefId);
            wfNodeDefService.deleteByCamundaDefId(camundaDefId);
            wfSequDefService.deleteByCamundaDefId(camundaDefId);
            wfSequEventDefService.deleteByCamundaDefId(camundaDefId);
            wfVarDefService.deleteByCamundaDefId(camundaDefId);
            this.deleteByCamundaDefId(camundaDefId);
            repositoryService.deleteProcessDefinition(camundaDefId);
        } else {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        return true;
    }

    @Override
    public InputStream getXmlByCamundaId(String camundaId) {
        InputStream inputStream = repositoryService.getProcessModel(camundaId);
        return inputStream;
    }

    @Override
    @Transactional
    public Boolean updateXmlByDeploymentId(String deploymentId, String xml) {
        if(StringUtils.isBlank(deploymentId)) {
            throw new IncloudException("deploymentId 不能为空！");
        }
        if(StringUtils.isBlank(xml)) {
            throw new IncloudException("xml信息 不能为空！");
        }
        int line = wfProcdefMapper.updateXmlByDeploymentId(deploymentId,xml);
        log.debug("根据deploymentId修改 XML文件 影响行数：{}", line);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        processEngineConfiguration.getDeploymentCache().removeProcessDefinition(processDefinition.getId());
        processEngineConfiguration.getDeploymentCache().removeProcessDefinition(processDefinition.getId());
        log.debug("根据deploymentId修改 XML文件 刷新缓存成功！");
        return true;
    }

    @Override
    public WfProcDefVo getByDefIdAndVersion(String camundaDefId, Integer version) {
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaDefId)
                .eq(WfProcDef::getProcdefVersion,version);
        WfProcDef one = this.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(one)){
            throw new IncloudException("查不到对应的流程定义！");
        }
        WfProcDefVo wfProcDefVo = dozerMapper.map(one, WfProcDefVo.class);
        return wfProcDefVo;
    }

    @Override
    @Transactional
    public String saveOrUpdateBpmnModel(Boolean isUpdate,String deploymentId, String xml,Boolean isCurrentVer,Boolean isNewVer) {
        long startTimeMillis = System.currentTimeMillis();
        if(StringUtils.isBlank(xml)) {
            throw new IncloudException("xml文件不能为空！");
        }
        //xml 转bpmn 对象
        Bpmn bpmn = XmlUtils.xmltoDoc(xml);
        log.debug("bmpn.xml 文件解析Bpmn 对象成功！");
        //如果部署id 为空则 则要调用 发布接口
        if(!isUpdate) {
            //调用后台发布
            try {
                DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(bpmn.getBpmndefinitions().getBpmnprocess().getName());
                deploymentBuilder.addString(bpmn.getBpmndefinitions().getBpmnprocess().getName() + ".bpmn", xml);
                deploymentBuilder.source("Netwisd"); //设置数据来源
                deploymentId = deploymentBuilder.deploy().getId();
                //RestAPI 发布
                //InputStream inputStream = data.getInputStream();
                //byte[] bytes = new byte[inputStream.available()];
                //inputStream.read(bytes);
                //deploymentId = localRestMethod("http://localhost:8007/rest/deployment/create",bpmn.getBpmndefinitions().getBpmnprocess().getName() + ".bpmn","Camunda Modeler",true,bytes);
                log.debug("bmpn.xml 文件发布成功！部署id为：" + deploymentId);
            } catch (Exception e) {
                String message = e.getMessage();
                if(message.contains(EngineErrEnum.ENGINE_09005.code)) {
                    throw new IncloudException(EngineErrEnum.ENGINE_09005.message);
                } else {
                    e.printStackTrace();
                    throw new IncloudException(message);
                }
            }
        }
        //查询camunda 定义信息
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        if(null == processDefinition) {
            throw new IncloudException("没有查询到流程定义信息！");
        }
        log.debug("根据部署id 查询流程定义为：{}", processDefinition.toString());

        // --解析流程定义 基础信息
        WfProcDefDto wfProcdefDto = XmlUtils.resolveProcessDef(bpmn.getBpmndefinitions().getBpmnprocess(),deployment,processDefinition,this);
        log.debug("解析xml流程-定义基础信息：{}", wfProcdefDto.toString());
        //查询表单子系统
        //WfForm wfForm = wfFormService.getById(0L); //wfProcdefDto.getFormId()
        //if(null == wfForm) {
            //throw new IncloudException("不存在相关的表单信息！");
        //} else {
            //wfProcdefDto.setFormKey(wfForm.getFormKey());
            //wfProcdefDto.setIsClone(YesNo.NO.code);
        //}
        WfProcDef wfProcdef = this.saveXml2procDef(isUpdate,wfProcdefDto,isCurrentVer,isNewVer);
        // -- 保存流程定义表单信息
        WfFormDefDto wfFormDefDto = new WfFormDefDto();
        wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
        wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
        //wfFormDefDto.setFormId(Long.valueOf(wfProcdef.getFormId()));
        //wfFormDefDto.setFormName(wfProcdef.getFormName());
        wfFormDefDto.setProcdefId(wfProcdef.getId());
        wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
        wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
        WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
        if(isUpdate) {
            //TODO 表单不能修改 影响太大
            //boolean formDefBoo = wfFormDefService.updateById(wfFormDef);
            //log.debug("保存流程定义-绑定的表单：{}", formDefBoo);
        } else {
            boolean formDefBoo = wfFormDefService.save(wfFormDef);
            log.debug("修改流程定义-绑定的表单：{}", formDefBoo);
        }
        // --解析流程定义 事件信息
        BpmnextensionElements bpmnextensionElements = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnextensionElements(); //执行事件
        List<WfEventDefDto> wfEventDefDtoList = XmlUtils.resolveEventDef(bpmnextensionElements,wfProcdef.getId(),0L, EventTypeSignEnum.PROC_DEF_EVENT.code,processDefinition,null);
        log.debug("解析xml流程定义-事件信息：{}", wfEventDefDtoList.toString());
        if(isUpdate) {
            wfEventDefService.delEventByNodeId(0L,processDefinition.getId());//流程定义nodeDefId 默认为0
        }
        wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
        // -- 流程定义 解析按钮
        List<WfButtonDefDto> wfButtonDefDtoList = XmlUtils.resolveButtonDef(bpmnextensionElements,null,wfProcdef.getId(),processDefinition);
        log.debug("解析xml流程定义-按钮信息：{}", wfButtonDefDtoList.toString());
        if(CollectionUtil.isNotEmpty(wfButtonDefDtoList)) {
            List<WfButtonDef> wfButtonDefList = new ArrayList<>();
            for (WfButtonDefDto wfButtonDefDto : wfButtonDefDtoList) {
                WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
                wfButtonDefList.add(wfButtonDef);
            }
            if(isUpdate) {
                LambdaQueryWrapper<WfButtonDef> wfButtonDefWrapper = new LambdaQueryWrapper<>();
                wfButtonDefWrapper.eq(WfButtonDef::getCamundaProcdefId, processDefinition.getId());
                boolean updateButtonBoo = wfButtonDefService.saveOrUpdateOrDeleteBatch(wfButtonDefWrapper,wfButtonDefList);
                log.debug("修改流程定义-按钮信息：{}", updateButtonBoo);
            } else {
                boolean saveButtonBoo = wfButtonDefService.saveBatch(wfButtonDefList);
                log.debug("保存流程定义-按钮信息：{}", saveButtonBoo);
            }
        }
        // --节点定义信息 1.开始节点 基础信息
        List<BpmnstartEvent> bpmnstartEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnstartEvent();
        List<WfNodeDefDto> startNodeDefDtoList = XmlUtils.resolveStartNodeDef(bpmnstartEventList,NodeTypeEnum.STARTEVENT.code,wfProcdef.getId() ,processDefinition,null);
        log.debug("解析xml开始节点-基础信息：{}", startNodeDefDtoList.toString());
        if(CollectionUtil.isNotEmpty(startNodeDefDtoList)) {
            wfNodeDefService.saveXml2WfNodeDef(isUpdate,startNodeDefDtoList,NodeTypeEnum.STARTEVENT.code,null);
        }

        // --节点定义信息 2.结束节点 基础信息
        List<BpmnendEvent> bpmnendEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnendEvent();
        List<WfNodeDefDto> endNodeDefDtoList = XmlUtils.resolveEndNodeDef(bpmnendEventList,NodeTypeEnum.ENDEVENT.code,wfProcdef.getId() ,processDefinition,null);
        log.debug("解析xml结束节点-基础信息：{}", endNodeDefDtoList.toString());
        if(CollectionUtil.isNotEmpty(endNodeDefDtoList)) {
            wfNodeDefService.saveXml2WfNodeDef(isUpdate,endNodeDefDtoList,NodeTypeEnum.ENDEVENT.code,null);
        }

        // --节点定义信息 3.用户节点 基础信息
        List<BpmnuserTask> bpmnuserTaskList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnuserTask();
        List<WfNodeDefDto> userNodeDefDtoList = XmlUtils.resolveUserTaskNodeDef(bpmnuserTaskList,NodeTypeEnum.USERTASK.code,wfProcdef.getId(),processDefinition,wfFormDef.getFormId(),null);
        if(CollectionUtil.isNotEmpty(userNodeDefDtoList)) {
            log.debug("解析xml用户节点信息：{}", userNodeDefDtoList.toString());
            wfNodeDefService.saveXml2UserTaskDef(isUpdate,userNodeDefDtoList,null);
        }

        //网关信息
        List<WfNodeDefDto> wfNodeDefDtoList = XmlUtils.resolveGatewayDef(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnexclusiveGateway(),wfProcdef.getId(),processDefinition,null);
        wfNodeDefService.saveXml2WfNodeDef(isUpdate,wfNodeDefDtoList,NodeTypeEnum.EXCLUSIVEGATEWAY.code,null);

        //序列流定义信息
        List<WfSequDefDto> wfSequDefDtoList = XmlUtils.resolveSequenceFlowDef(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsequenceFlow(),wfProcdef.getId(),processDefinition,wfFormDef.getFormId(),null);
        if(CollectionUtil.isNotEmpty(wfSequDefDtoList)) {
            log.debug("解析xml 序列流 信息：{}", wfSequDefDtoList.toString());
            wfSequDefService.saveXml2WfSequDef(isUpdate,wfSequDefDtoList,null);
        }
        //内嵌子流程信息
        List<WfNodeDefDto> subProcessNodeDefDtoList = XmlUtils.resolveSubProcess(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsubProcess(),NodeTypeEnum.SUBPROCESS.code,wfProcdef.getId(),processDefinition,null,wfFormDef.getFormId());
        if(CollectionUtil.isNotEmpty(subProcessNodeDefDtoList)) {
            log.debug("解析xml 子流程 信息：{}", subProcessNodeDefDtoList.toString());
            wfNodeDefService.saveXml2SubProcessDef(isUpdate, subProcessNodeDefDtoList);
        }
        //外嵌套子流程信息
        List<WfNodeDefDto> callActivityNodeDefDtoList = XmlUtils.resolveCallActivity(bpmn.getBpmndefinitions().getBpmnprocess().getBpmncallActivity(),NodeTypeEnum.CALLACTIVITY.code,wfProcdef.getId(),processDefinition,null,wfFormDef.getFormId());
        if(CollectionUtil.isNotEmpty(callActivityNodeDefDtoList)) {
            log.debug("解析xml 子流程(call activity) 信息：{}", callActivityNodeDefDtoList.toString());
            wfNodeDefService.saveXml2CallActivity(isUpdate, callActivityNodeDefDtoList,null);
        }

        //bpmn 对象 转型xml
        String bpmnXmlStr = "";
        try {
            String xmlStr = XmlUtils.objectToXml(bpmn.getBpmndefinitions(), Bpmndefinitions.class);
            bpmnXmlStr = XmlUtils.unCheckXml(xmlStr);
        }catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("解析流程定义异常！" +e.getMessage());
        }
        //修改camunda部署文件 以及清缓存
        log.debug("设置完dbId的xml 文件！" + bpmnXmlStr);
        updateXmlByDeploymentId(deploymentId, bpmnXmlStr);
        long overTimeMillis = System.currentTimeMillis();
        log.debug("bmpn.xml 文件解析Bpmn 对象,保存入库时间为：{}", startTimeMillis-overTimeMillis);
        return deploymentId;
    }

    /**
     * 解析xml时  验证版本是否重复 并且保存流程定义信息
     * @param isupdate 是否是编辑操作
     * @param wfProcdefDto
     * @param isCurrentVer 是否是 当前最新版本  如果是则需要把其他流程版本设置为false
     * @param isNewVer 是否是 创建的新版本  如果是新版本则不验证camunda key 是否重复
     */
    public WfProcDef saveXml2procDef(boolean isupdate,WfProcDefDto wfProcdefDto,Boolean isCurrentVer,boolean isNewVer) {
        log.debug("保存xml流程-定义基础信息：{}", wfProcdefDto.toString());
        //如果是创建的的新版本
        if(!isNewVer) {
            LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefKey()), WfProcDef::getCamundaProcdefKey, wfProcdefDto.getCamundaProcdefKey());
            List<WfProcDef> wfProcDefList = wfProcdefMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(wfProcDefList)) {
                throw new IncloudException("存在重复的流程定义key！");
            }
        }
        WfProcDef wfProcdef = dozerMapper.map(wfProcdefDto, WfProcDef.class);
        //如果是编辑操作 则不操作流程版本
        if(!isupdate) {
            if(null != wfProcdefDto.getCamundaProcdefId()) {
                LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefId()), WfProcDef::getCamundaProcdefId, wfProcdefDto.getCamundaProcdefId())
                        .eq(ObjectUtil.isNotEmpty(wfProcdefDto.getProcdefVersion()), WfProcDef::getProcdefVersion, wfProcdefDto.getProcdefVersion());
                List<WfProcDef> wfProcDefList = wfProcdefMapper.selectList(queryWrapper);
                if(CollectionUtil.isNotEmpty(wfProcDefList)) {
                    throw new IncloudException(wfProcdefDto.getProcdefName() + " 流程版本重复！");
                }
            }
        } else {
            wfProcdef.setCreateTime(null); //不更新创建时间
            boolean upBoo = super.updateById(wfProcdef);
            log.debug("编辑流程定义-基础信息是否成功：{}", upBoo);
            if(!upBoo) {
                throw new IncloudException("保存流程定义异常！");
            }
        }

        //如果设置当前版本为 生效版本 则需要把其他版本都设置成false
        if(null != isCurrentVer) {
            if(isCurrentVer) {
                LambdaUpdateWrapper<WfProcDef>  updateWrapper = new LambdaUpdateWrapper();
                updateWrapper.eq(WfProcDef::getCamundaProcdefKey, wfProcdefDto.getCamundaProcdefKey());
                updateWrapper.set(WfProcDef::getCurrentVersion,YesNo.NO.code);
                super.update(updateWrapper);
                wfProcdef.setCurrentVersion(YesNo.YES.code);
                boolean boo = super.save(wfProcdef);
                log.debug("当前版本 保存流程定义-基础信息是否成功：{}", boo);
                if(!boo) {
                    throw new IncloudException("保存流程定义异常！");
                }
            } else {
                wfProcdef.setCurrentVersion(YesNo.NO.code);
                boolean boo = super.save(wfProcdef);
                log.debug("保存流程定义-基础信息是否成功：{}", boo);
                if(!boo) {
                    throw new IncloudException("保存流程定义异常！");
                }
            }
        }

        return wfProcdef;
    }

    @Override
    public String createNewVersionByCamundaId(String camundaProcdefId) {
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("camundaId 不能为空！");
        }
        InputStream inputStream = repositoryService.getProcessModel(camundaProcdefId);
        String bpmnXmlStr = "";
        try {
            //inputStream 转成字符串
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            bpmnXmlStr = new String(bytes);
        }catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("inputStream 转 String 异常！");
        }
        return bpmnXmlStr;
    }

    @Override
    public String getXmlInfoByCamundaId(String camundaProcdefId) {
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("camundaId 不能为空！");
        }
        /*LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaProcdefId)
                .eq(WfProcDef::getCurrentVersion, YesNo.YES.code);
        WfProcDef one = this.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(one)){
            throw new IncloudException("查不到对应的流程定义！");
        }
        //根据流程定义信息 查询出对应的xml文件
        InputStream inputStream = repositoryService.getProcessModel(one.getCamundaProcdefId());*/
        InputStream inputStream = repositoryService.getProcessModel(camundaProcdefId);
        String bpmnXmlStr = "";
        try {
            //inputStream 转成字符串
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String xml = new String(bytes);
            //xml str 转 bmpm
            Bpmn bpmn = XmlUtils.xmltoDoc(xml);
            List<BpmnsequenceFlow> bpmnsequenceFlowList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsequenceFlow();
            if(CollectionUtil.isNotEmpty(bpmnsequenceFlowList)) {
                for (BpmnsequenceFlow bpmnsequenceFlow : bpmnsequenceFlowList) {
                    BpmnextensionElements bpmnextensionElements = bpmnsequenceFlow.getBpmnextensionElements();
                    //修改 序列流特殊字符
                    if(null != bpmnextensionElements) {
                        //处理特殊字符
                        String netwisdsequExpText = bpmnextensionElements.getNetwisdsequExpText();
                        if (StringUtils.isNotBlank(netwisdsequExpText)) {
                            netwisdsequExpText = tagContentCheck(netwisdsequExpText);
                            bpmnextensionElements.setNetwisdsequExpText(netwisdsequExpText);
                        }
                    }
                    //修改 序列流 自定义字段特殊字符
                    BpmnconditionExpression bpmnconditionExpression = bpmnsequenceFlow.getBpmnconditionExpression();
                    if(null != bpmnconditionExpression) {
                        String content = bpmnconditionExpression.getContent();
                        if(StringUtils.isNotBlank(content)) {
                            content = tagContentCheck(content);
                            bpmnconditionExpression.setContent(content);
                        }
                    }
                }
            }
            String xmlStr = XmlUtils.objectToXml(bpmn.getBpmndefinitions(), Bpmndefinitions.class);
            bpmnXmlStr = XmlUtils.unCheckXml(xmlStr);
        }catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("inputStream 转 String 异常！");
        }
        return bpmnXmlStr;
    }

    @Override
    public List<WfProcDefVo> queryVerListByCamundaKey(String camundaProcdefKey,Integer currentVersion) {
        if(StringUtils.isBlank(camundaProcdefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        log.debug("方法：queryVerListByCamundaKey。参数camundaDefKey：" + camundaProcdefKey);
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefKey,camundaProcdefKey)
                //.eq(WfProcDefgetIsClone::, YesNo.NO.code)//不是查询加签clone出来的流程
                .eq(ObjectUtil.isNotNull(currentVersion),WfProcDef::getCurrentVersion, currentVersion);
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        List<WfProcDefVo> wfProcDefVoListfProcDef = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfProcDefListfProcDef)) {
            for (WfProcDef wfProcDef : wfProcDefListfProcDef) {
                WfProcDefVo wfProcDefVo = dozerMapper.map(wfProcDef, WfProcDefVo.class);
                wfProcDefVoListfProcDef.add(wfProcDefVo);
            }
        }
        log.debug("方法：queryVerListByCamundaKey。返回值：" + wfProcDefVoListfProcDef.toString());
        return wfProcDefVoListfProcDef;
    }



    @Override
    @Transactional
    public boolean setCurrentVerByCamundaId(String camundaProcdefId) {
        log.debug("方法：setCurrentVerByCamundaId。camundaDefId：" + camundaProcdefId);
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        //把所有的版本设置为未生效
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaProcdefId);
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        if(CollectionUtil.isEmpty(wfProcDefListfProcDef)) {
            throw new IncloudException("没有查询出对应的");
        }
        WfProcDef wfProcDef = wfProcDefListfProcDef.get(0);
        LambdaUpdateWrapper <WfProcDef>  updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(ObjectUtil.isNotEmpty(wfProcDef.getCamundaProcdefKey()), WfProcDef::getCamundaProcdefKey, wfProcDef.getCamundaProcdefKey());
        updateWrapper.set(ObjectUtil.isNotEmpty(wfProcDef.getCurrentVersion()),WfProcDef::getCurrentVersion,YesNo.NO.code);
        super.update(updateWrapper);
        //把当前流程设置为生效版本
        wfProcDef.setCurrentVersion(YesNo.YES.code);
        super.updateById(wfProcDef);
        log.debug("方法：setCurrentVerByCamundaId。返回值：true。");
        return true;
    }

    @Override
    @Transactional
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 流程定义 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfProcDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfProcDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfProcdefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 流程定义 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 流程定义 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfProcDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfProcDef::getCamundaProcdefId,camundaDefId);
        int line = wfProcdefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 流程定义 信息(删除某个版本). 影响行数：{}", line);
    }

//    public String localRestMethod(String url,String deploymentName,String deploymentSource,boolean deployChangedOnly, byte[] bytes){
//        JSONObject __jSONObject = null;
//        try {
//            log.info("url为：{}",url);
//            HttpResponse execute = null;
//            HttpPost httpPost = new HttpPost(url);
//            String accessToken = AppUserUtil.getAccessToken();
//            //httpPost.setHeader("Content-Type", "multipart/form-data");
//            httpPost.setHeader("Authorization",accessToken);
//            Charset charset = Charset.forName("UTF-8");//设置编码
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            builder.addBinaryBody(deploymentName,bytes, org.apache.http.entity.ContentType.DEFAULT_BINARY,deploymentName);
//            //MultipartEntity reqEntity = new MultipartEntity();
//            StringBody _deploymentName = new StringBody(deploymentName,charset);
//            StringBody _deploymentSource = new StringBody(deploymentSource,charset);
//            StringBody boo = new StringBody("true",charset);
//            builder.addPart("deployment-name",_deploymentName);
//            builder.addPart("deployment-source",_deploymentSource);
//            builder.addPart("deploy-changed-only",boo);
//            HttpEntity httpEntity = builder.build();
//
//            httpPost.setEntity(httpEntity);
//            execute = httpClient.execute(httpPost);
//            HttpEntity result = execute.getEntity();
//            String resString = EntityUtils.toString(result);
//            System.out.println(resString);
//            JSONObject jSONObject = JSONUtil.parseObj(resString);
//            JSONObject _jSONObject =JSONUtil.parseObj(jSONObject.get("deployedProcessDefinitions").toString());
//            for(String str:_jSONObject.keySet()){
//                __jSONObject =JSONUtil.parseObj(_jSONObject.get(str));
//            }
//
//            if (execute.getStatusLine().getStatusCode() == 200) {
//                log.info("发布成功！" + resString);
//            }else {
//                log.info("发布失败，错误代码：{}",execute.getStatusLine().getStatusCode());
//                throw new IncloudException("发布失败");
//            }
//        } catch (IOException e) {
//            throw new IncloudException("发布失败");
//        }
//        return (String) __jSONObject.get("deploymentId");
//    }


    @Override
    public WfProcDefVo queryCurrentVerByCamundaDefKey(String camundaDefKey) {
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        log.debug("方法：queryCurrentVerByCamundaDefKey。参数camundaDefKey：" + camundaDefKey);
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefKey,camundaDefKey)
                .eq(WfProcDef::getCurrentVersion, YesNo.YES.code);
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        List<WfProcDefVo> wfProcDefVoListfProcDef = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfProcDefListfProcDef)) {
            for (WfProcDef wfProcDef : wfProcDefListfProcDef) {
                WfProcDefVo wfProcDefVo = dozerMapper.map(wfProcDef, WfProcDefVo.class);
                wfProcDefVoListfProcDef.add(wfProcDefVo);
            }
        } else {
            throw new IncloudException("没有找到流程定义信息。");
        }
        log.debug("方法：queryCurrentVerByCamundaDefKey。返回值：" + wfProcDefVoListfProcDef.toString());
        return wfProcDefVoListfProcDef.get(0);
    }

    @Override
    public WfProcDefVo createFormByCamundaDefKey(String camundaDefKey) {
        WfProcDefVo wfProcDefVo = queryCurrentVerByCamundaDefKey(camundaDefKey);
        if(ObjectUtil.isNotEmpty(wfProcDefVo)) {
            //查询出节点定义信息
            WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(wfProcDefVo.getCamundaProcdefId(),firstNode);
            if(null == wfNodeDefVo) {
                throw  new IncloudException("没有获取初始化节点信息。");
            }
            wfProcDefVo.setWfNodeDef(wfNodeDefVo);
            //根据流程定义信息查询出表单def信息
            WfFormDefDto wfFormDefDto = new WfFormDefDto();
            wfFormDefDto.setCamundaProcdefId(wfProcDefVo.getCamundaProcdefId());
            wfFormDefDto.setCamundaNodeDefId(firstNode);
            List<WfFormDefVo> formDefVos = wfFormDefService.lists(wfFormDefDto);
            if(CollectionUtil.isEmpty(formDefVos)) {
                throw new IncloudException("没有对应的表单信息！");
            }
            if(StringUtils.isBlank(wfProcDefVo.getCamundaProcdefId())) {
                throw new IncloudException("没有对应的流程定义id信息！");
            }
            //查询表单字段信息 fistNode 为固定值
            List<WfFormFieldsDefVo> formVarDefVoList = wfFormFieldsDefService.getFormFields(wfProcDefVo.getCamundaProcdefId(),"firstNode");
            Map<Long,List<WfFormFieldsDefVo>> groupMap = formVarDefVoList.stream().collect(Collectors.groupingBy(WfFormFieldsDefVo::getFormId));
            formDefVos.forEach(d -> {
                ModelFormVo modelFormVo;
                try {
                    modelFormVo = modelFeignClient.formDetailByFormName(d.getFormName());
                }catch (Exception e){
                    log.error("根据表单名称：{}获取表单失败！",d.getFormName());
                    throw new IncloudException("根据表单名称：{}获取表单失败！",d.getFormName());
                }
                List<WfFormFieldsDefVo> wfFormFieldsDefVos = groupMap.get(d.getFormId());
                d.setFormVarDefVoList(wfFormFieldsDefVos);
                d.setPageUrl(modelFormVo.getPageUrl());
            });
            wfProcDefVo.setWfFormDefs(formDefVos);
            //查询表单关联的按钮信息
            List<WfButtonDefVo> wfButtonList = wfButtonDefService.queryDefButtons(wfProcDefVo.getCamundaProcdefId(),"firstNode");
            wfProcDefVo.setWfButtonDefs(wfButtonList);
            return wfProcDefVo;
        } else {
            throw new IncloudException("没有查询到对应的激活版本！");
        }
    }

    @Override
    public WfProcDefVo getProcDefInfoByCamundaProcdefId(String camundaProcdefId, String nodeKey) {
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("camundaProcdefId 不能为空！");
        }
        if(StringUtils.isBlank(nodeKey)) {
            throw new IncloudException("nodeKey 不能为空！");
        }
        log.debug("方法：getProcDefInfoByCamundaProcdefId。{}，{}。",camundaProcdefId,nodeKey);
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaProcdefId);
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(wfProcDefListfProcDef)) {
            List<WfProcDefVo> wfProcDefVoList = DozerUtils.mapList(dozerMapper, wfProcDefListfProcDef, WfProcDefVo.class);
            WfProcDefVo wfProcDefVo = wfProcDefVoList.get(0);
            //根据流程定义信息查询出表单def信息
            WfFormDefDto wfFormDefDto = new WfFormDefDto();
            wfFormDefDto.setCamundaProcdefId(wfProcDefVo.getCamundaProcdefId());
            wfFormDefDto.setCamundaNodeDefId(nodeKey);
            List<WfFormDefVo> formDefVos = wfFormDefService.lists(wfFormDefDto);
            if(CollectionUtil.isEmpty(formDefVos)) {
                throw new IncloudException("没有对应的表单信息！");
            }
            //查询表单字段信息 fistNode 为固定值
            List<WfFormFieldsDefVo> formVarDefVoList = wfFormFieldsDefService.getFormFields(wfProcDefVo.getCamundaProcdefId(),nodeKey);
            Map<Long,List<WfFormFieldsDefVo>> groupMap = formVarDefVoList.stream().collect(Collectors.groupingBy(WfFormFieldsDefVo::getFormId));
            formDefVos.forEach(d -> {
                List<WfFormFieldsDefVo> wfFormFieldsDefVos = groupMap.get(d.getFormId());
                d.setFormVarDefVoList(wfFormFieldsDefVos);
            });
            wfProcDefVo.setWfFormDefs(formDefVos);
            //查询表单关联的按钮信息
            List<WfButtonDefVo> wfButtonList = wfButtonDefService.queryDefButtons(wfProcDefVo.getCamundaProcdefId(),nodeKey);
            wfProcDefVo.setWfButtonDefs(wfButtonList);
            //查询初始化节点信息
            WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(wfProcDefVo.getCamundaProcdefId(),nodeKey);
            if(null == wfNodeDefVo) {
                throw new IncloudException("没有获取初始化节点信息。");
            }
            wfProcDefVo.setWfNodeDef(wfNodeDefVo);
            return wfProcDefVo;
        } else {
            log.debug("方法：getProcDefInfoByCamundaProcdefId。返回值：null" );
            throw  new IncloudException("没有对应的流程定义信息！");
        }
    }

    @Override
    public WfProcDef getProcDefByCamundaProcdefId(String camundaProcdefId) {
        log.debug("方法：getProcDefByCamundaProcdefId。参数：" + camundaProcdefId);
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("流程定义id不能为空！");
        }
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefId,camundaProcdefId);
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(wfProcDefListfProcDef)) {
            log.debug("方法：getProcDefByCamundaProcdefId。返回值：" + wfProcDefListfProcDef.toString());
            return wfProcDefListfProcDef.get(0);
        } else {
            log.debug("方法：getProcDefByCamundaProcdefId。返回值：null。");
            return null;
        }
    }

    @Override
    public String saveOrUpdateBpmnModel(String processName, BpmnModelInstance bpmnModelInstance) {
//        long startTimeMillis = System.currentTimeMillis();
//        boolean isUpdate =false;
//        log.debug("bmpn.xml 文件解析Bpmn 对象成功！");
//        //如果部署id 为空则 则要调用 发布接口
//        String deploymentId = "";
//        if(!isUpdate) {
//            //调用后台发布
//            try {
//                DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processName);
//                //deploymentBuilder.addString(bpmn.getBpmndefinitions().getBpmnprocess().getName() + ".bpmn", xml);
//                deploymentBuilder.addModelInstance(processName + ".bpmn", bpmnModelInstance);
//                deploymentBuilder.source("Netwisd"); //设置数据来源
//                deploymentId = deploymentBuilder.deploy().getId();
//                //RestAPI 发布
//                //InputStream inputStream = data.getInputStream();
//                //byte[] bytes = new byte[inputStream.available()];
//                //inputStream.read(bytes);
//                //deploymentId = localRestMethod("http://localhost:8007/rest/deployment/create",bpmn.getBpmndefinitions().getBpmnprocess().getName() + ".bpmn","Camunda Modeler",true,bytes);
//                log.debug("bmpn.xml 文件发布成功！部署id为：" + deploymentId);
//            } catch (Exception e) {
//                String message = e.getMessage();
//                if(message.contains(EngineErrEnum.ENGINE_09005.code)) {
//                    throw new IncloudException(EngineErrEnum.ENGINE_09005.message);
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //查询camunda 定义信息
//        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
//        if(null == processDefinition) {
//            throw new IncloudException("没有查询到流程定义信息！");
//        }
//        log.debug("根据部署id 查询流程定义为：{}", processDefinition.toString());
//
//
//        String xml = createNewVersionByCamundaId(processDefinition.getId());
//        if(StringUtils.isBlank(xml)) {
//            throw new IncloudException("xml文件不能为空！");
//        }
//        //xml 转bpmn 对象
//        Bpmn bpmn = XmlUtils.xmltoDoc(xml);
//
//        // --解析流程定义 基础信息
//        WfProcDefDto wfProcdefDto = XmlUtils.resolveProcessDef(bpmn.getBpmndefinitions().getBpmnprocess(),deployment,processDefinition);
//        log.debug("解析xml流程-定义基础信息：{}", wfProcdefDto.toString());
//        //查询表单子系统
//        WfForm wfForm = wfFormService.getById(wfProcdefDto.getFormId());
//        if(null == wfForm) {
//            throw new IncloudException("不存在相关的表单信息！");
//        } else {
//            wfProcdefDto.setModuleCode(wfForm.getModuleCode());
//            wfProcdefDto.setModuleName(wfForm.getModuleName());
//            wfProcdefDto.setFormKey(wfForm.getFormKey());
//        }
//        WfProcDef wfProcdef = this.saveXml2procDef(isUpdate,wfProcdefDto,false,true);
//        // -- 保存流程定义表单信息
//        WfFormDefDto wfFormDefDto = new WfFormDefDto();
//        wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
//        wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
//        wfFormDefDto.setFormId(Long.valueOf(wfProcdef.getFormId()));
//        wfFormDefDto.setFormName(wfProcdef.getFormName());
//        wfFormDefDto.setProcdefId(wfProcdef.getId());
//        wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
//        wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
//        WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
//        if(isUpdate) {
//            //TODO 表单不能修改 影响太大
//            //boolean formDefBoo = wfFormDefService.updateById(wfFormDef);
//            //log.debug("保存流程定义-绑定的表单：{}", formDefBoo);
//        } else {
//            boolean formDefBoo = wfFormDefService.save(wfFormDef);
//            log.debug("修改流程定义-绑定的表单：{}", formDefBoo);
//        }
//        // --解析流程定义 事件信息
//        BpmnextensionElements bpmnextensionElements = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnextensionElements(); //执行事件
//        List<WfEventDefDto> wfEventDefDtoList = XmlUtils.resolveEventDef(bpmnextensionElements,wfProcdef.getId(),0L, EventTypeSignEnum.PROC_DEF_EVENT.code,processDefinition,null);
//        log.debug("解析xml流程定义-事件信息：{}", wfEventDefDtoList.toString());
//        if(isUpdate) {
//            wfEventDefService.delEventByNodeId(0L);//流程定义nodeDefId 默认为0
//        }
//        wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
//        // -- 流程定义 解析按钮
//        List<WfButtonDefDto> wfButtonDefDtoList = XmlUtils.resolveButtonDef(bpmnextensionElements,null,wfProcdef.getId(),processDefinition);
//        log.debug("解析xml流程定义-按钮信息：{}", wfButtonDefDtoList.toString());
//        if(CollectionUtil.isNotEmpty(wfButtonDefDtoList)) {
//            List<WfButtonDef> wfButtonDefList = new ArrayList<>();
//            for (WfButtonDefDto wfButtonDefDto : wfButtonDefDtoList) {
//                WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
//                wfButtonDefList.add(wfButtonDef);
//            }
//            if(isUpdate) {
//                LambdaQueryWrapper<WfButtonDef> wfButtonDefWrapper = new LambdaQueryWrapper<>();
//                wfButtonDefWrapper.eq(WfButtonDef::getCamundaProcdefId, processDefinition.getId());
//                boolean updateButtonBoo = wfButtonDefService.saveOrUpdateOrDeleteBatch(wfButtonDefWrapper,wfButtonDefList);
//                log.debug("修改流程定义-按钮信息：{}", updateButtonBoo);
//            } else {
//                boolean saveButtonBoo = wfButtonDefService.saveBatch(wfButtonDefList);
//                log.debug("保存流程定义-按钮信息：{}", saveButtonBoo);
//            }
//        }
//        // --节点定义信息 1.开始节点 基础信息
//        List<BpmnstartEvent> bpmnstartEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnstartEvent();
//        List<WfNodeDefDto> startNodeDefDtoList = XmlUtils.resolveStartNodeDef(bpmnstartEventList,NodeTypeEnum.STARTEVENT.code,wfProcdef.getId() ,processDefinition,null);
//        log.debug("解析xml开始节点-基础信息：{}", startNodeDefDtoList.toString());
//        if(CollectionUtil.isNotEmpty(startNodeDefDtoList)) {
//            wfNodeDefService.saveXml2WfNodeDef(isUpdate,startNodeDefDtoList,NodeTypeEnum.STARTEVENT.code);
//        }
//
//        // --节点定义信息 2.结束节点 基础信息
//        List<BpmnendEvent> bpmnendEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnendEvent();
//        List<WfNodeDefDto> endNodeDefDtoList = XmlUtils.resolveEndNodeDef(bpmnendEventList,NodeTypeEnum.ENDEVENT.code,wfProcdef.getId() ,processDefinition,null);
//        log.debug("解析xml结束节点-基础信息：{}", endNodeDefDtoList.toString());
//        if(CollectionUtil.isNotEmpty(endNodeDefDtoList)) {
//            wfNodeDefService.saveXml2WfNodeDef(isUpdate,endNodeDefDtoList,NodeTypeEnum.ENDEVENT.code);
//        }
//
//        // --节点定义信息 3.用户节点 基础信息
//        List<BpmnuserTask> bpmnuserTaskList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnuserTask();
//        List<WfNodeDefDto> userNodeDefDtoList = XmlUtils.resolveUserTaskNodeDef(bpmnuserTaskList,NodeTypeEnum.USERTASK.code,wfProcdef.getId(),processDefinition,wfFormDef.getFormId(),null);
//        if(CollectionUtil.isNotEmpty(userNodeDefDtoList)) {
//            log.debug("解析xml用户节点信息：{}", userNodeDefDtoList.toString());
//            wfNodeDefService.saveXml2UserTaskDef(isUpdate,userNodeDefDtoList);
//        }
//
//        //网关信息
//        List<WfNodeDefDto> wfNodeDefDtoList = XmlUtils.resolveGatewayDef(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnexclusiveGateway(),wfProcdef.getId(),processDefinition,null);
//        wfNodeDefService.saveXml2WfNodeDef(isUpdate,wfNodeDefDtoList,NodeTypeEnum.EXCLUSIVEGATEWAY.code);
//
//        //序列流定义信息
//        List<WfSequDefDto> wfSequDefDtoList = XmlUtils.resolveSequenceFlowDef(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsequenceFlow(),wfProcdef.getId(),processDefinition,wfFormDef.getFormId(),null);
//        if(CollectionUtil.isNotEmpty(wfSequDefDtoList)) {
//            log.debug("解析xml 序列流 信息：{}", wfSequDefDtoList.toString());
//            wfSequDefService.saveXml2WfSequDef(isUpdate,wfSequDefDtoList);
//        }
//        //子流程信息
//        List<WfNodeDefDto> subProcessNodeDefDtoList = XmlUtils.resolveSubProcess(bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsubProcess(),NodeTypeEnum.SUBPROCESS.code,wfProcdef.getId(),processDefinition);
//        if(CollectionUtil.isNotEmpty(subProcessNodeDefDtoList)) {
//            log.debug("解析xml 子流程 信息：{}", wfSequDefDtoList.toString());
//
//        }
//        //bpmn 对象 转型xml
//        String bpmnXmlStr = "";
//        try {
//            String xmlStr = XmlUtils.objectToXml(bpmn.getBpmndefinitions(), Bpmndefinitions.class);
//            bpmnXmlStr = XmlUtils.unCheckXml(xmlStr);
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw new IncloudException("");
//        }
//        //修改camunda部署文件 以及清缓存
//        log.debug("设置完dbId的xml 文件！" + bpmnXmlStr);
//        updateXmlByDeploymentId(deploymentId, bpmnXmlStr);
//        //TODO 子流程
//        long overTimeMillis = System.currentTimeMillis();
//        log.debug("bmpn.xml 文件解析Bpmn 对象,保存入库时间为！：{}", startTimeMillis-overTimeMillis);
//
//        return deploymentId;
        return null;
    }

    @Override
    @Transactional
    public void deployment(DeploymentBpmnDto deploymentBpmnDto) { //String xmlStr, String procDefId
        String xmlStr = deploymentBpmnDto.getData(); //新增
        String procDefId = deploymentBpmnDto.getProcDefId(); //编辑
        Integer isNewVer = deploymentBpmnDto.getIsNewVer(); //是否创建新版本
        Integer isCurrentVer = deploymentBpmnDto.getIsCurrentVer(); //是否使用当前版本
        boolean isAdd = true; //判断流程定义是否传 传就是编辑
        //发布XML
        Document dom = DomUtils.loadStr(xmlStr);
        Element root = dom.getRootElement();
        Element proElement = root.element("process");
        String deploymentId = "";
        if(StringUtils.isNotBlank(procDefId)) {
            //编辑查询流程定义
            isAdd = false;
            WfProcDefVo wfProcDefVo = this.get(Long.valueOf(procDefId));
            deploymentId = wfProcDefVo.getDeploymentId();
        } else {
            //发布新流程定义
            deploymentId = deploymentWithCmd(xmlStr, proElement);
        }
        //查询camunda 定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        if(null == processDefinition) {
            throw new IncloudException("没有查询到流程定义信息！");
        }
        log.debug("根据部署id 查询流程定义为：{}", processDefinition.toString());
        //保存流程定义、表单、事件
        WfProcDefDto wfProcDefDto = DomUtils.parseBpmnProcess(root,processDefinition);
        if(StringUtils.isNotBlank(procDefId)) {
            wfProcDefDto.setId(Long.valueOf(procDefId));
        }
        this.saveProcDefInfo(wfProcDefDto,isAdd,isNewVer,isCurrentVer);
        //保存开始节点、表单、事件、按钮
        List<WfNodeDefDto> startNodeDefDtos = DomUtils.parseStartEventElements(proElement,NodeTypeEnum.STARTEVENT.code,wfProcDefDto.getId(),processDefinition,null);
        wfNodeDefService.saveNodeInfo(startNodeDefDtos,isAdd);
        //保存结束节点、表单、事件、按钮
        List<WfNodeDefDto> endNodeDefDtos = DomUtils.parseEndEventElements(proElement,NodeTypeEnum.ENDEVENT.code,wfProcDefDto.getId(),processDefinition,null);
        wfNodeDefService.saveNodeInfo(endNodeDefDtos,isAdd);
        //保存用户节点、表单、事件、按钮
        List<WfNodeDefDto> userTaskNodeDefDtos = DomUtils.parseUserTaskEventElements(proElement,NodeTypeEnum.USERTASK.code,wfProcDefDto.getId(),processDefinition,null);
        wfNodeDefService.saveNodeInfo(userTaskNodeDefDtos,isAdd);
        //保存排它网关
        List<WfNodeDefDto> excWfNodeDefDtos = DomUtils.parseExclusiveGatewayElements(proElement,NodeTypeEnum.EXCLUSIVEGATEWAY.code,wfProcDefDto.getId(),processDefinition);
        wfNodeDefService.saveNodeInfo(excWfNodeDefDtos,isAdd);
        //保存并行网关
        List<WfNodeDefDto> paraWfNodeDefDtos = DomUtils.parseParallelGatewayElements(proElement,NodeTypeEnum.PARALLELGATEWAY.code,wfProcDefDto.getId(),processDefinition);
        wfNodeDefService.saveNodeInfo(paraWfNodeDefDtos,isAdd);
        //保存合并网关
        List<WfNodeDefDto> inclWfNodeDefDtos = DomUtils.parseparInclusiveGatewayElements(proElement,NodeTypeEnum.INCLUSIVEGATEWAY.code,wfProcDefDto.getId(),processDefinition);
        wfNodeDefService.saveNodeInfo(inclWfNodeDefDtos,isAdd);
        //保存序列流
        List<WfSequDefDto> wfSequDefDtos = DomUtils.parseSequenceFlowElements(proElement,wfProcDefDto.getId(),processDefinition);
        wfSequDefService.saveSequDefInfo(wfSequDefDtos,isAdd);
        //保存外嵌子流程
        List<WfNodeDefDto> callActivityNodeDtos = DomUtils.parseCallActivityElements(proElement,NodeTypeEnum.CALLACTIVITY.code,wfProcDefDto.getId(),processDefinition);
        wfNodeDefService.saveNodeInfo(callActivityNodeDtos,isAdd);
        //保存service节点
        //如果编辑则要 修改camunda的流程定义xml
        if(StringUtils.isNotBlank(procDefId)) {
            updateXmlByDeploymentId(deploymentId, restIsChangeXml(xmlStr));
        }
    }

    /**
     * camunda 发布
     * @param xmlStr
     * @param proElement
     * @return
     */
    public String deploymentWithCmd(String xmlStr,Element proElement) {
        try {
            //流程名称
            Attribute porcNameAttr = proElement.attribute("name");
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(porcNameAttr.getStringValue());
            deploymentBuilder.addString(porcNameAttr.getStringValue() + ".bpmn",xmlStr);
            deploymentBuilder.source("Netwisd"); //设置数据来源
            String deploymentId = deploymentBuilder.deploy().getId();
            log.debug("bmpn.xml 文件发布成功！部署id为：" + deploymentId);
            return deploymentId;
            //Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        } catch (Exception e) {
            String message = e.getMessage();
            e.printStackTrace();
            throw new IncloudException(message);
        }
    }

    @Override
    @Transactional
    public void saveProcDefInfo(WfProcDefDto wfProcDefDto, boolean isAdd, Integer isNewVer, Integer isCurrentVer) {
        if(isAdd) {//是否新建
            //如果是创建新版本则 不验证流程定义key重复
            if(isNewVer != YesNo.YES.code) {
                LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ObjectUtil.isNotEmpty(wfProcDefDto.getCamundaProcdefKey()), WfProcDef::getCamundaProcdefKey, wfProcDefDto.getCamundaProcdefKey());
                List<WfProcDef> wfProcDefList = wfProcdefMapper.selectList(queryWrapper);
                if(CollectionUtil.isNotEmpty(wfProcDefList)) {
                    throw new IncloudException("存在重复的流程定义key！");
                }
            }
            //如果设置当前版本为 生效版本 则需要把其他版本都设置成false
            if(isCurrentVer  == YesNo.YES.code) {
                LambdaUpdateWrapper<WfProcDef>  updateWrapper = new LambdaUpdateWrapper();
                updateWrapper.eq(WfProcDef::getCamundaProcdefKey, wfProcDefDto.getCamundaProcdefKey());
                updateWrapper.set(WfProcDef::getCurrentVersion,YesNo.NO.code);
                super.update(updateWrapper);
            }
            //保存流程定义
            WfProcDef wfProcDef = dozerMapper.map(wfProcDefDto, WfProcDef.class);
            wfProcDef.setCurrentVersion(isCurrentVer);
            wfProcDef.setIsBizCenter(YesNo.YES.code);//是否业务中心展示 默认true
            this.save(wfProcDef);
            //保存流程定义 表单
            List<WfFormDefDto> wfFormDefDtos = wfProcDefDto.getWfFormDefDtos();
            if(CollectionUtil.isNotEmpty(wfFormDefDtos)) {
                wfFormDefService.saveXml2WfFormDefDef(wfFormDefDtos);
            }
            //保存流程定义 事件 以及事件参数
            List<WfEventDefDto> wfEventDefDtoList = wfProcDefDto.getWfEventDefDtoList();
            if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
                wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
            }
        } else { //如果为编辑
            if(wfProcDefDto.getIsChange() == YesNo.YES.code) {
                //修改流程定义
                WfProcDef wfProcDef = this.getById(wfProcDefDto.getId());
                wfProcDef.setProcdefName(wfProcDefDto.getProcdefName());
                wfProcDef.setProcdefTypeId(wfProcDefDto.getProcdefTypeId());
                wfProcDef.setProcdefTypeName(wfProcDefDto.getProcdefTypeName());
                wfProcDef.setVersionTag(wfProcDefDto.getVersionTag());
                wfProcDef.setRemark(wfProcDefDto.getRemark());
                wfProcDef.setIcon(wfProcDefDto.getIcon());
                wfProcDef.setProcdefNameAbbr(wfProcDefDto.getProcdefNameAbbr());
                //删除事件及参数
                wfEventDefService.delEventByNodeId(0L,wfProcDefDto.getCamundaProcdefId());
                //重新保存 事件 以及事件参数
                List<WfEventDefDto> wfEventDefDtoList = wfProcDefDto.getWfEventDefDtoList();
                if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
                    wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
                }
            }
        }
    }

    @Override
    @Transactional
    public boolean setStartableByCamundaProcdefKey(WfProcDefDto wfProcdefDto) {
        log.debug("方法：setCurrentVerByCamundaProcdefId。camundaProcdefKey：" + wfProcdefDto.getCamundaProcdefKey());
        if(StringUtils.isBlank(wfProcdefDto.getCamundaProcdefKey())) {
            throw new IncloudException("camundaProcdefKey 不能为空！");
        }
        if(null == wfProcdefDto.getStartable()) {
            throw new IncloudException("startable 不能为空！");
        }
        //把所有的版本设置为未生效
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefKey,wfProcdefDto.getCamundaProcdefKey());
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        if(CollectionUtil.isEmpty(wfProcDefListfProcDef)) {
            throw new IncloudException("没有查询出对应的流程信息。");
        }
        LambdaUpdateWrapper <WfProcDef>  updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefKey()), WfProcDef::getCamundaProcdefKey, wfProcdefDto.getCamundaProcdefKey());
        updateWrapper.set(ObjectUtil.isNotEmpty(wfProcdefDto.getStartable()),WfProcDef::getStartable,wfProcdefDto.getStartable());
        boolean result = super.update(updateWrapper);
        log.debug("方法：setCurrentVerByCamundaProcdefId。返回值:{}。",result);
        return result;
    }

    @Override
    @Transactional
    public boolean setBizCenterByCamundaProcdefKey(WfProcDefDto wfProcdefDto) {
        log.debug("方法：setCurrentVerByCamundaProcdefId。camundaProcdefKey：" + wfProcdefDto.getCamundaProcdefKey());
        if(StringUtils.isBlank(wfProcdefDto.getCamundaProcdefKey())) {
            throw new IncloudException("camundaProcdefKey 不能为空！");
        }
        if(null == wfProcdefDto.getIsBizCenter()) {
            throw new IncloudException("isBizCenter 不能为空！");
        }
        LambdaQueryWrapper<WfProcDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcDef::getCamundaProcdefKey,wfProcdefDto.getCamundaProcdefKey());
        List<WfProcDef> wfProcDefListfProcDef = this.list(queryWrapper);
        if(CollectionUtil.isEmpty(wfProcDefListfProcDef)) {
            throw new IncloudException("没有查询出对应的流程信息。");
        }
        //把所有的版本设置为加入业务中心
        LambdaUpdateWrapper <WfProcDef>  updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(ObjectUtil.isNotEmpty(wfProcdefDto.getCamundaProcdefKey()), WfProcDef::getCamundaProcdefKey, wfProcdefDto.getCamundaProcdefKey());
        updateWrapper.set(ObjectUtil.isNotEmpty(wfProcdefDto.getIsBizCenter()),WfProcDef::getIsBizCenter,wfProcdefDto.getIsBizCenter());
        boolean result = super.update(updateWrapper);
        log.debug("方法：setBizCenterByCamundaProcdefKey。返回值:{}。",result);
        return result;
    }

    public static String restIsChangeXml(String xml){
        String bpmn = xml.replace("netwisd:isChange=\"1\"", "netwisd:isChange=\"0\"");
        return bpmn;
    }
}
