package com.netwisd.base.wf.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.netwisd.base.common.constants.ExpreParamVarTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.dto.*;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Dom解析相关工具
 * @Author: XHL@netwisd.com
 * @Date: 2020/5/22 11:17 上午
 */
@Slf4j
@Component
public class DomUtils {

    //文件方式 加载dom对象
    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    //IO流方式 加载dom对象
    public static Document load(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(inputStream); // 读取文件流,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    //字符串方式加载dom对象
    public static Document loadStr(String xml) {
        if(StringUtils.isBlank(xml)) {
            throw new IncloudException("BPMN信息不能为空！");
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        }catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    //解析流程定义
    public static WfProcDefDto parseBpmnProcess(Element root, ProcessDefinition processDefinition) {
        //获取process 节点
        Element proElement = root.element("process");
        //流程定义Key
        Attribute procKeyAttribute = proElement.attribute("id");
        checkAttribute(procKeyAttribute,"流程定义key");
        //流程名称
        Attribute porcNameAttr = proElement.attribute("name");
        checkAttribute(porcNameAttr,"流程名称");
        //isExecutable
        Attribute porcIsExecutableAttr = proElement.attribute("isExecutable");
        //版本标签
        Attribute versionTagAttr = proElement.attribute("versionTag");
        checkAttribute(versionTagAttr,"版本标识");
        //流程分类id
        Attribute procdefTypeIdAttr = proElement.attribute("procdefTypeId");
        checkAttribute(procdefTypeIdAttr,"流程分类id");
        //流程分类名称
        Attribute procdefTypeNameAttr = proElement.attribute("procdefTypeName");
        checkAttribute(procdefTypeNameAttr,"流程分类名称");
        //流程定义icon
        Attribute iconNameAttr = proElement.attribute("icon");
        checkAttribute(iconNameAttr,"流程icon");
        //流程定义名称简写
        Attribute procdefNameAbbrAttr = proElement.attribute("procdefNameAbbr");
        checkAttribute(procdefNameAbbrAttr,"流程定义名称简写");
        //备注
        Attribute remarkAttribute = proElement.attribute("remark");
        Attribute isChangeAttribute = proElement.attribute("isChange");

        WfProcDefDto wfProcDefDto = new WfProcDefDto();
        wfProcDefDto.setCamundaProcdefId(processDefinition.getId());
        wfProcDefDto.setProcdefName(porcNameAttr.getValue());
        wfProcDefDto.setCamundaProcdefKey(procKeyAttribute.getValue());
        wfProcDefDto.setProcdefVersion(processDefinition.getVersion());
        wfProcDefDto.setDeploymentId(processDefinition.getDeploymentId());
        wfProcDefDto.setResourceName(processDefinition.getResourceName());
        wfProcDefDto.setSuspentionState(YesNo.NO.code);//0 否 1是
        wfProcDefDto.setTenantId(processDefinition.getTenantId());
        wfProcDefDto.setVersionTag(processDefinition.getVersionTag());
        wfProcDefDto.setStartable("true".equals(porcIsExecutableAttr.getValue()) ? YesNo.YES.code : YesNo.NO.code);
        wfProcDefDto.setDeployTime(LocalDateTime.now());
        wfProcDefDto.setDataSource("");
        wfProcDefDto.setIcon(iconNameAttr.getValue());
        //LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //wfProcDefDto.setCreateUserId(loginAppUser.getId());
        //wfProcDefDto.setCreateUserName(loginAppUser.getUsername());
        wfProcDefDto.setProcdefTypeId(Long.valueOf(procdefTypeIdAttr.getValue()));
        wfProcDefDto.setProcdefTypeName(procdefTypeNameAttr.getValue());
        wfProcDefDto.setRemindSign(YesNo.YES.code + "");//0 否 1是
        wfProcDefDto.setVersionTag(versionTagAttr.getValue());
        wfProcDefDto.setRemark(remarkAttribute.getValue());
        wfProcDefDto.setProcdefNameAbbr(procdefNameAbbrAttr.getValue());
        if(null  ==  isChangeAttribute) {
            wfProcDefDto.setIsChange(YesNo.NO.code);
        } else {
            wfProcDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
        }

        //解析事件
        Element extensionElementsElement = proElement.element("extensionElements");
        List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.NODE_DEF_EVENT.code, wfProcDefDto.getId(), 0L,null);
        if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
            wfProcDefDto.setWfEventDefDtoList(wfEventDefDto);
        }
        //解析表单及表单字段
        List<WfFormDefDto> wfFormDefDtos = parseFomsProcess(extensionElementsElement,wfProcDefDto.getId(), processDefinition,0L,null);
        if(CollectionUtils.isNotEmpty(wfFormDefDtos)) {
            wfProcDefDto.setWfFormDefDtos(wfFormDefDtos);
        }
        return wfProcDefDto;
    }

    //解析扩展元素
    public static List<WfEventDefDto> parseBpmnExtensionElements(Element extensionElementsElement, ProcessDefinition processDefinition, Integer eventTypeSign, Long procDefId, Long nodeDefId, String camundaNodeDefId) {
        List<WfEventDefDto> wfEventDefDtoList = new ArrayList<>();
        //判断是否存在扩展元素
        if (null != extensionElementsElement) {
            //获取 执行事件
            List<Element> executionElements = extensionElementsElement.elements("executionListener");
            //判断是否存在事件元素
            List<WfEventDefDto> exeEventDefDtoList = Listener(executionElements, processDefinition, eventTypeSign, procDefId, nodeDefId, camundaNodeDefId, EventTypeEnum.EXE_EVENT.code);
            if (CollectionUtils.isNotEmpty(exeEventDefDtoList)) {
                wfEventDefDtoList.addAll(exeEventDefDtoList);
            }
            //获取 任务事件
            List<Element> taskListenerElements = extensionElementsElement.elements("taskListener");
            List<WfEventDefDto> taskEventDefDtoList = Listener(taskListenerElements, processDefinition, eventTypeSign, procDefId, nodeDefId, camundaNodeDefId, EventTypeEnum.TASK_EVENT.code);
            if (CollectionUtils.isNotEmpty(taskEventDefDtoList)) {
                wfEventDefDtoList.addAll(taskEventDefDtoList);
            }
        }
        return wfEventDefDtoList;
    }

    //解析事件
    public static List<WfEventDefDto> Listener(List<Element> taskListenerElements, ProcessDefinition processDefinition, Integer eventTypeSign, Long procDefId, Long nodeDefId, String camundaNodeDefId, Integer eventType) {
        List<WfEventDefDto> wfEventDefDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(taskListenerElements)) {
            //循环所有Task事件
            for (Element taskListenerElement : taskListenerElements) {
                //event 触发类型
                Attribute eventAttribute = taskListenerElement.attribute("event");
                checkAttribute(eventAttribute,"事件触发类型");
                //eventId 事件id
                Attribute eventIdAttribute = taskListenerElement.attribute("eventId");
                checkAttribute(eventIdAttribute,"事件ID");
                //完成前事件影响提交标识
                Attribute eventSubmitSignAttribute = taskListenerElement.attribute("eventSubmitSign");
                checkAttribute(eventSubmitSignAttribute,"完成前事件影响提交标识");
                //事件排序
                Attribute orderAttribute = taskListenerElement.attribute("order");
                checkAttribute(orderAttribute,"事件排序");

                WfEventDefDto wfEventDefDto = new WfEventDefDto();
                wfEventDefDto.setEventBindType(eventAttribute.getValue());
                wfEventDefDto.setEventId(Long.valueOf(eventIdAttribute.getValue()));
                wfEventDefDto.setEventSubmitSign(StringUtils.isNotBlank(eventSubmitSignAttribute.getValue()) ? Integer.valueOf(eventSubmitSignAttribute.getValue()) : null);
                wfEventDefDto.setProcdefId(procDefId);
                wfEventDefDto.setNodeDefId(nodeDefId);
                wfEventDefDto.setEventTypeSign(eventTypeSign);
                wfEventDefDto.setEventType(eventType);
                wfEventDefDto.setCamundaProcdefId(processDefinition.getId());
                wfEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                wfEventDefDto.setCamundaNodeDefId(camundaNodeDefId);
                wfEventDefDto.setNetwisdOrder(StringUtils.isNotBlank(orderAttribute.getValue()) ? Integer.valueOf(orderAttribute.getValue()) : 0);
                //解析事件参数
                List<Element> fieldElements = taskListenerElement.elements("field");
                if (CollectionUtil.isNotEmpty(fieldElements)) {
                    List<WfEventParamDefDto> wfEventParamDefDtoList = new ArrayList<>();
                    for (Element fieldElement : fieldElements) {
                        WfEventParamDefDto wfEventParamDefDto = new WfEventParamDefDto();
                        wfEventParamDefDto.setEventDefId(wfEventDefDto.getId());
                        Attribute idAttribute = fieldElement.attribute("id");
                        checkAttribute(idAttribute,"事件参数ID");
                        Element stringElement = fieldElement.element("string");
                        Element expressionElement = fieldElement.element("expression");

                        String str = null != stringElement ? stringElement.getTextTrim() : null;
                        String expression = null != expressionElement ? expressionElement.getTextTrim() : null;
                        if (StringUtils.isNotBlank(str)) {
                            wfEventParamDefDto.setParamType("string");
                            wfEventParamDefDto.setParamValue(str);
                        } else {
                            wfEventParamDefDto.setParamType("expression");
                            wfEventParamDefDto.setParamValue(expression);
                        }
                        wfEventParamDefDto.setParamId(idAttribute.getValue());
                        wfEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfEventParamDefDto.setCamundaNodeDefId(camundaNodeDefId);
                        wfEventParamDefDto.setNodeDefId(nodeDefId);
                        wfEventParamDefDtoList.add(wfEventParamDefDto);
                    }
                    wfEventDefDto.setWfEventParamDefDto(wfEventParamDefDtoList);
                }
                wfEventDefDtoList.add(wfEventDefDto);
            }
        }
        return wfEventDefDtoList;
    }

    //解析开始节点
    public static List<WfNodeDefDto> parseStartEventElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition,String camundaSubProcessNodeDefId) {
        List<Element> startEventElements = proElement.elements("startEvent");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(startEventElements)) {
            for (Element startEventElement : startEventElements) {
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                //开始节点id
                Attribute idAttribute = startEventElement.attribute("id");
                checkAttribute(idAttribute,"开始节点id");
                //开始节点名称
                Attribute nameAttribute = startEventElement.attribute("name");
                checkAttribute(nameAttribute,"开始节点名称");
                //是否改动标识
                Attribute isChangeAttribute = startEventElement.attribute("isChange");
                startWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                startWfNodeDefDto.setNodeName(nameAttribute.getValue());
                startWfNodeDefDto.setNodeType(nodeType);
                startWfNodeDefDto.setDueDate(null);
                startWfNodeDefDto.setFollowUpDate(null);
                startWfNodeDefDto.setPriority(null);
                startWfNodeDefDto.setProcdefId(procDefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                if(null  ==  isChangeAttribute) {
                    startWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    startWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                //解析开始节点事件
                Element extensionElementsElement = startEventElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.NODE_DEF_EVENT.code, procDefId, startWfNodeDefDto.getId(),idAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                //解析表单及表单字段
                List<WfFormDefDto> wfFormDefDtos = parseFomsProcess(extensionElementsElement,procDefId, processDefinition,startWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtils.isNotEmpty(wfFormDefDtos)) {
                    startWfNodeDefDto.setWfFormDefDtos(wfFormDefDtos);
                }
                //解析按钮
                List<WfButtonDefDto> wfButtonDefDto = parseBpmnButtonsElements(extensionElementsElement,processDefinition,procDefId,startWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfButtonDefDto)) {
                    startWfNodeDefDto.setWfButtonDefDtoListDto(wfButtonDefDto);
                }
                wfNodeDefDtoList.add(startWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析结束节点
    public static List<WfNodeDefDto> parseEndEventElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition,String camundaSubProcessNodeDefId) {
        List<Element> endEventElements = proElement.elements("endEvent");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(endEventElements)) {
            for (Element endEventElement : endEventElements) {
                //结束节点id
                Attribute idAttribute = endEventElement.attribute("id");
                checkAttribute(idAttribute,"结束节点id");
                //结束节点名称
                Attribute nameAttribute = endEventElement.attribute("name");
                checkAttribute(nameAttribute,"结束节点名称");
                //是否改动标识
                Attribute isChangeAttribute = endEventElement.attribute("isChange");
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                startWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                startWfNodeDefDto.setNodeName(nameAttribute.getValue());
                startWfNodeDefDto.setNodeType(nodeType);
                startWfNodeDefDto.setDueDate(null);
                startWfNodeDefDto.setFollowUpDate(null);
                startWfNodeDefDto.setPriority(null);
                startWfNodeDefDto.setProcdefId(procDefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                if(null  ==  isChangeAttribute) {
                    startWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    startWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                //解析开始节点事件
                Element extensionElementsElement = endEventElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.NODE_DEF_EVENT.code, procDefId, startWfNodeDefDto.getId(),nameAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                //解析按钮
                List<WfButtonDefDto> wfButtonDefDto = parseBpmnButtonsElements(extensionElementsElement,processDefinition,procDefId,startWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfButtonDefDto)) {
                    startWfNodeDefDto.setWfButtonDefDtoListDto(wfButtonDefDto);
                }
                wfNodeDefDtoList.add(startWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析用户节点
    public static List<WfNodeDefDto> parseUserTaskEventElements(Element proElement, Integer nodeType, Long procDefId, ProcessDefinition processDefinition, String camundaSubProcessNodeDefId) {
        List<Element> userEventElements = proElement.elements("userTask");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(userEventElements)) {
            for (Element userEventElement : userEventElements) {
                WfNodeDefDto userTaskWfNodeDefDto = new WfNodeDefDto();
                //用户节点id
                Attribute idAttribute = userEventElement.attribute("id");
                checkAttribute(idAttribute,"用户节点id");
                //用户节点名称
                Attribute nameAttribute = userEventElement.attribute("name");
                checkAttribute(nameAttribute,"用户节点名称");
                //过期时间
                Attribute dueDayAttribute = userEventElement.attribute("dueDay");
                //重要程度
                Attribute priorityAttribute = userEventElement.attribute("priority");
                //选择人规则
                Attribute selectRuleAttribute = userEventElement.attribute("selectRule");
                //批量审批
                Attribute batchRuleAttribute = userEventElement.attribute("batchRule");
                //是否支持撤回
                Attribute cancelRuleAttribute = userEventElement.attribute("cancelRule");
                //是否支持驳回
                Attribute returnRuleAttribute = userEventElement.attribute("returnRule");
                //是否改动标识
                Attribute isChangeAttribute = userEventElement.attribute("isChange");
                if(null  ==  isChangeAttribute) {
                    userTaskWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    userTaskWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                userTaskWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                userTaskWfNodeDefDto.setNodeName(nameAttribute.getName());
                userTaskWfNodeDefDto.setNodeType(nodeType);
                if(null != dueDayAttribute) {
                    if(StringUtils.isNotBlank(dueDayAttribute.getValue())) {
                        Double douDueDay = Double.valueOf(dueDayAttribute.getValue());
                        if(null != douDueDay) {
                            userTaskWfNodeDefDto.setDueDate(douDueDay);
                        }
                    }
                }
                userTaskWfNodeDefDto.setPriority(null != priorityAttribute?Integer.valueOf(priorityAttribute.getValue()):null);
                userTaskWfNodeDefDto.setProcdefId(procDefId);
                userTaskWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                userTaskWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    userTaskWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                userTaskWfNodeDefDto.setSelectRule(null != selectRuleAttribute? Integer.valueOf(selectRuleAttribute.getValue()):null);
                userTaskWfNodeDefDto.setBatchRule(null != batchRuleAttribute? Integer.valueOf(batchRuleAttribute.getValue()):null);
                userTaskWfNodeDefDto.setCancelRule(null != cancelRuleAttribute ? Integer.valueOf(cancelRuleAttribute.getValue()):null);
                userTaskWfNodeDefDto.setReturnRule(null != returnRuleAttribute? Integer.valueOf(returnRuleAttribute.getValue()):null);

                //解析用户节点会签
                Element multiInstanceLoopElement = userEventElement.element("multiInstanceLoopCharacteristics");
                if(null != multiInstanceLoopElement) {
                    //passingHandle
                    Attribute passingHandleAttribute = multiInstanceLoopElement.attribute("passingHandle");
                    //通过率
                    Attribute passingRateAttribute = multiInstanceLoopElement.attribute("passingRate");
                    //不通过率
                    Attribute unpassingHandleAttribute = multiInstanceLoopElement.attribute("unpassingHandle");
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    userTaskWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCETASK.code);
                    userTaskWfNodeDefDto.setPassingRate(null != passingRateAttribute? BigDecimal.valueOf(Double.valueOf(passingRateAttribute.getValue())):null);
                    userTaskWfNodeDefDto.setPassingHandle(null != passingHandleAttribute? Integer.valueOf(passingHandleAttribute.getValue()):null);
                    userTaskWfNodeDefDto.setUnpassingHandle(null != unpassingHandleAttribute ? Integer.valueOf(unpassingHandleAttribute.getValue()):null);
                }
                //判断是否是 多任务节点
                if(null == multiInstanceLoopElement) {
                    userTaskWfNodeDefDto.setIsMultiTask(YesNo.NO.code);
                } else {
                    userTaskWfNodeDefDto.setIsMultiTask(YesNo.YES.code);
                }
                //解析用户节点事件
                Element extensionElementsElement = userEventElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.NODE_DEF_EVENT.code, procDefId, userTaskWfNodeDefDto.getId(),nameAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        userTaskWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                //解析表单及表单字段
                List<WfFormDefDto> wfFormDefDtos = parseFomsProcess(extensionElementsElement,procDefId, processDefinition,userTaskWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfFormDefDtos)) {
                    userTaskWfNodeDefDto.setWfFormDefDtos(wfFormDefDtos);
                }
                //解析按钮
                List<WfButtonDefDto> wfButtonDefDto = parseBpmnButtonsElements(extensionElementsElement,processDefinition,procDefId,userTaskWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfButtonDefDto)) {
                    userTaskWfNodeDefDto.setWfButtonDefDtoListDto(wfButtonDefDto);
                }
                //解析表达式
                List<WfExpreUserDefDto> wfExpreUserDefDtos =  parseHumanExpsElements(userEventElement,nodeType,procDefId,processDefinition,userTaskWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfExpreUserDefDtos)) {
                    userTaskWfNodeDefDto.setWfExpreUserDefDtoList(wfExpreUserDefDtos);
                }
                //解析变量
                List<WfVarDefDto> wfVarDefDtos = parseBpmnVarsElements(extensionElementsElement,processDefinition,procDefId,userTaskWfNodeDefDto.getId(),idAttribute.getValue(),false);
                if(CollectionUtil.isNotEmpty(wfVarDefDtos)) {
                    userTaskWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtos);
                }
                wfNodeDefDtoList.add(userTaskWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析表单
    public static List<WfFormDefDto> parseFomsProcess(Element extensionElementsElement, Long procDefId, ProcessDefinition processDefinition,Long nodeId, String camundaNodeId) {
        List<WfFormDefDto> wfFormDefDtos = new ArrayList<>();
        if(null != extensionElementsElement) {
            Element formsElement = extensionElementsElement.element("forms");
            if(null != formsElement) {
                List<Element> forms = formsElement.elements("form");
                if(CollectionUtils.isNotEmpty(forms)) {
                    for (Element form : forms) {
                        //表单id
                        Attribute idAttribute = form.attribute("id");
                        checkAttribute(idAttribute,"表单id");
                        //表单code
                        Attribute formNameAttribute = form.attribute("formName");
                        checkAttribute(formNameAttribute,"表单code");
                        //表单名称
                        Attribute formNameChAttribute = form.attribute("formNameCh");
                        checkAttribute(formNameChAttribute,"表单名称");
                        //外链表单url
                        //Attribute pageUrl = form.attribute("pageUrl");

                        WfFormDefDto wfFormDefDto = new WfFormDefDto();
                        //wfFormDefDto.setPageUrl(null != pageUrl ? pageUrl.getValue() : null);
                        wfFormDefDto.setFormId(Long.valueOf(idAttribute.getValue()));
                        wfFormDefDto.setFormName(formNameAttribute.getValue());
                        wfFormDefDto.setProcdefId(procDefId);
                        wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfFormDefDto.setFormNameCh(formNameChAttribute.getValue());
                        wfFormDefDto.setNodeDefId(nodeId);
                        wfFormDefDto.setCamundaNodeDefId(camundaNodeId);

                        //解析表单字段
                        List<Element> fieldElements = form.elements("field");
                        List<WfFormFieldsDefDto> wfFormFieldsDefDtoList = new ArrayList<>();
                        List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
                        if(CollectionUtil.isNotEmpty(fieldElements)) {
                            for (Element fieldElement : fieldElements) {
                                //模型字段id
                                Attribute modelFieldIdAttribute = fieldElement.attribute("modelFieldId");
                                checkAttribute(modelFieldIdAttribute,"模型字段id");
                                //字段名(驼峰)
                                Attribute javaNameAttribute = fieldElement.attribute("javaName");
                                checkAttribute(javaNameAttribute,"表单字段名");
                                //字段中文名
                                Attribute nameChAttribute = fieldElement.attribute("nameCh");
                                //checkAttribute(nameChAttribute,"表单字段中文名");
                                //是否多行
                                Attribute isMoreRowAttribute = fieldElement.attribute("isMoreRow");
                                //checkAttribute(isMoreRowAttribute,"是否多行");
                                //数据库类型
                                Attribute dbTypeAttribute = fieldElement.attribute("dbType");
                                checkAttribute(dbTypeAttribute,"表单字段数据库类型");
                                //是否表单映射变量
                                Attribute isOrmAttribute = fieldElement.attribute("isOrm");
                                checkAttribute(isOrmAttribute,"表单映射变量");
                                String isOrm = isOrmAttribute.getValue();
                                //如果是表单映射变量 则保存变量表中
                                if(Integer.valueOf(isOrm) == YesNo.YES.code) {
                                    WfVarDefDto wfVarDefDto = buildFromVars(procDefId,nodeId,Long.valueOf(modelFieldIdAttribute.getValue()),Long.valueOf(idAttribute.getValue()),formNameAttribute.getValue(),
                                            YesNo.YES.code,processDefinition,camundaNodeId,javaNameAttribute.getValue(),null,Integer.valueOf(isOrmAttribute.getValue()),null != nameChAttribute?nameChAttribute.getValue():null);
                                    wfVarDefDtoList.add(wfVarDefDto);
                                }
                                WfFormFieldsDefDto wfFormFieldsDefDto = new WfFormFieldsDefDto();
                                wfFormFieldsDefDto.setIsOrm(Integer.valueOf(isOrm));
                                wfFormFieldsDefDto.setModelFieldId(Long.valueOf(modelFieldIdAttribute.getValue()));
                                wfFormFieldsDefDto.setFormId(Long.valueOf(idAttribute.getValue()));
                                wfFormFieldsDefDto.setFormName(formNameAttribute.getValue());
                                wfFormFieldsDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfFormFieldsDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                wfFormFieldsDefDto.setCamundaNodeDefId(camundaNodeId);
                                wfFormFieldsDefDto.setNodeDefId(nodeId);
                                wfFormFieldsDefDto.setJavaName(javaNameAttribute.getValue());
                                if(0L != nodeId) { //流程定义节点不需要权限字段
                                    //权限标识
                                    Attribute powerCodeAttribute = fieldElement.attribute("powerCode");
                                    checkAttribute(powerCodeAttribute,"表单字段权限标识");
                                    wfFormFieldsDefDto.setPowerCode(powerCodeAttribute.getValue());
                                }
                                wfFormFieldsDefDto.setDbType(dbTypeAttribute.getValue());
                                wfFormFieldsDefDto.setNameCh(null != nameChAttribute?nameChAttribute.getValue():null);
                                //wfFormFieldsDefDto.setIsMoreRow(StringUtils.isNotBlank(isMoreRowAttribute.getValue())?Integer.valueOf(isMoreRowAttribute.getValue()):null);
                                wfFormFieldsDefDtoList.add(wfFormFieldsDefDto);
                            }
                            wfFormDefDto.setWfFormFieldsDefDtoList(wfFormFieldsDefDtoList);
                            if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                                wfFormDefDto.setWfVarDefDtoList(wfVarDefDtoList);
                            }
                        }
                        wfFormDefDtos.add(wfFormDefDto);
                    }
                }
            }
        }
        return wfFormDefDtos;
    }

    //解析按钮
    public static List<WfButtonDefDto> parseBpmnButtonsElements(Element extensionElementsElement, ProcessDefinition processDefinition, Long procDefId, Long nodeDefId, String camundaNodeDefId) {
        List<WfButtonDefDto> wfButtonDefDtoList = new ArrayList<>();
        //判断是否存在扩展元素
        if (null != extensionElementsElement) {
            //获取buttons
            Element buttonsElement = extensionElementsElement.element("buttons");
            if(null != buttonsElement) {
                List<Element> buttonElements = buttonsElement.elements("button");
                if(CollectionUtil.isNotEmpty(buttonElements)) {
                    for (Element buttonElement : buttonElements) {
                        //按钮名称
                        Attribute nameAttribute = buttonElement.attribute("name");
                        checkAttribute(nameAttribute,"按钮名称");
                        //按钮Code
                        Attribute codeAttribute = buttonElement.attribute("code");
                        checkAttribute(codeAttribute,"按钮Code");
                        WfButtonDefDto wfButtonDefDto = new WfButtonDefDto();
                        wfButtonDefDto.setProcdefId(procDefId);
                        wfButtonDefDto.setNodeDefId(nodeDefId);
                        wfButtonDefDto.setButtonName(nameAttribute.getValue());
                        wfButtonDefDto.setButtonCode(codeAttribute.getValue());
                        wfButtonDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfButtonDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfButtonDefDto.setCamundaNodeDefId(camundaNodeDefId);
                        wfButtonDefDtoList.add(wfButtonDefDto);
                    }
                }
            }
        }
        return wfButtonDefDtoList;
    }

    //解析序列流  -现在路由表达式直接写 sequenceFlow上 参数是有前端拼接
    public static List<WfSequDefDto> parseSequenceFlowElements(Element proElement, Long procDefId, ProcessDefinition processDefinition) {
        List<Element> sequenceFlowElements = proElement.elements("sequenceFlow");
        List<WfSequDefDto> wfSequDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(sequenceFlowElements)) {
            for (Element sequenceFlowElement : sequenceFlowElements) {
                //序列流id
                Attribute idAttribute = sequenceFlowElement.attribute("id");
                checkAttribute(idAttribute,"序列流id");
                WfSequDefDto wfSequDefDto = new WfSequDefDto();
                //序列流名称
                Attribute nameAttribute = sequenceFlowElement.attribute("name");
                Element conditionExpressionElement = sequenceFlowElement.element("conditionExpression");
                if(null != conditionExpressionElement) {
                    //表达式
                    String expression = conditionExpressionElement.getTextTrim();
                    wfSequDefDto.setExpression(expression);
                    if(StringUtils.isBlank(expression)) {
                        throw new IncloudException("表达式内容不能为空！");
                    }
                }
                //是否改动标识
                Attribute isChangeAttribute = sequenceFlowElement.attribute("isChange");
                if(null  ==  isChangeAttribute) {
                    wfSequDefDto.setIsChange(YesNo.NO.code);
                } else {
                    wfSequDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                wfSequDefDto.setCamundaProcdefId(processDefinition.getId());
                wfSequDefDto.setCamundaProcdefKey(processDefinition.getKey());
                wfSequDefDto.setCamundaSequId(idAttribute.getValue());
                wfSequDefDto.setSequName(null !=nameAttribute ? nameAttribute.getValue() : null);
                wfSequDefDto.setProcdefId(procDefId);
                //解析事件
                Element extensionElementsElement = sequenceFlowElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<Element> executionListenersElement = sequenceFlowElement.elements("executionListener");
                    List<WfSequEventDefDto> wfSequEventDefDtos = sequenceListener(executionListenersElement,processDefinition, procDefId , wfSequDefDto.getId(),idAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfSequEventDefDtos)) {
                        wfSequDefDto.setWfSequEventDefDtoList(wfSequEventDefDtos);
                    }
                }
                //解析变量
                List<WfVarDefDto> wfVarDefDtos = parseBpmnVarsElements(extensionElementsElement,processDefinition,procDefId,wfSequDefDto.getId(),idAttribute.getValue(),true);
                if(CollectionUtil.isNotEmpty(wfVarDefDtos)) {
                    wfSequDefDto.setWfVarDefDtoList(wfVarDefDtos);
                }
                wfSequDefDtoList.add(wfSequDefDto);
            }
        }
        return wfSequDefDtoList;
    }

    //解析序列流事件
    public static List<WfSequEventDefDto> sequenceListener(List<Element> exeListenerElements, ProcessDefinition processDefinition,Long procDefId, Long seqDefId, String camundaSeqDefId) {
        List<WfSequEventDefDto> wfSequDefDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(exeListenerElements)) {
            //循环所有Task事件
            for (Element exeListenerElement : exeListenerElements) {
                //event 触发类型
                Attribute eventAttribute = exeListenerElement.attribute("event");
                checkAttribute(eventAttribute,"序列流事件触发类型");
                //eventId 事件id
                Attribute eventIdAttribute = exeListenerElement.attribute("eventId");
                checkAttribute(eventIdAttribute,"序列流事件id");
                //完成前事件影响提交标识
                Attribute eventSubmitSignAttribute = exeListenerElement.attribute("eventSubmitSign");
                //排序
                Attribute orderAttribute = exeListenerElement.attribute("order");
                WfSequEventDefDto wfSequEventDefDto = new WfSequEventDefDto();
                wfSequEventDefDto.setEventBindType(eventAttribute.getValue());
                wfSequEventDefDto.setEventId(Long.valueOf(eventIdAttribute.getValue()));
                wfSequEventDefDto.setProcdefId(procDefId);
                wfSequEventDefDto.setCamundaProcdefId(processDefinition.getId());
                wfSequEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                wfSequEventDefDto.setSequDefId(seqDefId);
                wfSequEventDefDto.setCamundaProcdefId(processDefinition.getId());
                wfSequEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                wfSequEventDefDto.setCamundaSequDefId(camundaSeqDefId);
                //解析事件参数
                List<Element> fieldElements = exeListenerElement.elements("field");
                if (CollectionUtil.isNotEmpty(fieldElements)) {
                    List<WfSequEventParamDefDto> wfSequEventParamDefDtoList = new ArrayList<>();
                    for (Element fieldElement : fieldElements) {
                        WfSequEventParamDefDto wfSequEventParamDefDto = new WfSequEventParamDefDto();
                        wfSequEventParamDefDto.setEventDefId(wfSequEventDefDto.getId());
                        Attribute idAttribute = fieldElement.attribute("id");
                        Attribute stringAttribute = fieldElement.attribute("string");
                        Attribute expressionAttribute = fieldElement.attribute("expression");

                        String str = null != stringAttribute ? stringAttribute.getValue() : null;
                        String expression = null != expressionAttribute ? expressionAttribute.getValue() : null;
                        if (StringUtils.isNotBlank(str)) {
                            wfSequEventParamDefDto.setParamType("string");
                            wfSequEventParamDefDto.setParamDefalutValue(str);
                        } else {
                            wfSequEventParamDefDto.setParamType("expression");
                            wfSequEventParamDefDto.setParamDefalutValue(expression);
                        }
                        wfSequEventParamDefDto.setParamId(idAttribute.getValue());
                        wfSequEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfSequEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfSequEventParamDefDto.setCamundaSequDefId(camundaSeqDefId);
                        wfSequEventParamDefDtoList.add(wfSequEventParamDefDto);
                    }
                    wfSequEventDefDto.setWfSequEventParamDefDtoList(wfSequEventParamDefDtoList);
                }
                wfSequDefDtoList.add(wfSequEventDefDto);
            }
        }
        return wfSequDefDtoList;
    }

    //解析排它网关
    public static List<WfNodeDefDto> parseExclusiveGatewayElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition) {
        List<Element> exclusiveGatewayElements = proElement.elements("exclusiveGateway");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(exclusiveGatewayElements)) {
            for (Element exclusiveGatewayElement : exclusiveGatewayElements) {
                //网关id
                Attribute idAttribute = exclusiveGatewayElement.attribute("id");
                checkAttribute(idAttribute,"排它网关id");
                //网关名称
                Attribute nameAttribute = exclusiveGatewayElement.attribute("name");
                //是否改动标识
                Attribute isChangeAttribute = exclusiveGatewayElement.attribute("isChange");
                checkAttribute(nameAttribute,"排它网关名称");
                WfNodeDefDto gatewayWfNodeDefDto = new WfNodeDefDto();
                if(null  ==  isChangeAttribute) {
                    gatewayWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    gatewayWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                gatewayWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                gatewayWfNodeDefDto.setNodeName(nameAttribute.getValue());
                gatewayWfNodeDefDto.setNodeType(nodeType);
                gatewayWfNodeDefDto.setDueDate(null);
                gatewayWfNodeDefDto.setFollowUpDate(null);
                gatewayWfNodeDefDto.setPriority(null);
                gatewayWfNodeDefDto.setProcdefId(procDefId);
                gatewayWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                gatewayWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //解析事件
                Element extensionElementsElement = proElement.element("extensionElements");
                List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.GATEWAY_DEF_EVENT.code, procDefId , gatewayWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                    gatewayWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                }
                wfNodeDefDtoList.add(gatewayWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析人员表达式
    public static List<WfExpreUserDefDto> parseHumanExpsElements(Element userElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition, Long nodeDefId, String camundaNodeDefId) {
        Element extensionElementsElement = userElement.element("extensionElements");
        List<WfExpreUserDefDto> wfExpreUserDefDtoList = new ArrayList<>();
        if(null != extensionElementsElement) {
            Element humanExpsElement = extensionElementsElement.element("humanExps");
            if(null != humanExpsElement) {
                List<Element> humanExpElements = humanExpsElement.elements("humanExp");//表达式
                if(CollectionUtil.isNotEmpty(humanExpElements)) {
                    for (Element humanExpElement : humanExpElements) {
                        //表达式名称
                        Attribute expreNameAttribute = humanExpElement.attribute("expreName");
                        checkAttribute(expreNameAttribute,"表达式名称");
                        //业务基础类型
                        Attribute bizTypeAttribute = humanExpElement.attribute("bizType");
                        WfExpreUserDefDto wfExpreUserDefDto = new WfExpreUserDefDto();
                        //各个业务类型id
                        Attribute bizIdAttribute = humanExpElement.attribute("bizId");
                        //如果是表达式 则正常处理
                        //表达式
                        Attribute expreExpressionAttribute = humanExpElement.attribute("expreExpression");
                        checkAttribute(expreExpressionAttribute,"人员表达式");
                        wfExpreUserDefDto.setNodeDefId(nodeDefId);
                        wfExpreUserDefDto.setNodeType(nodeType);
                        wfExpreUserDefDto.setProcdefId(procDefId);
                        wfExpreUserDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfExpreUserDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfExpreUserDefDto.setCamundaNodeDefId(camundaNodeDefId);
                        wfExpreUserDefDto.setExpressionName(expreNameAttribute.getValue());
                        wfExpreUserDefDto.setExpression(expreExpressionAttribute.getValue());
                        wfExpreUserDefDto.setBizType(bizTypeAttribute.getValue());
                        //处理参数
                        List<Element> humanExpParamElements = humanExpElement.elements("humanExpParam");
                        List<WfExpreUserParamDefDto> wfExpreUserParamDefDtoList = new ArrayList<>();
                        if(CollectionUtil.isNotEmpty(humanExpParamElements)) {
                            for (Element expParmElement : humanExpParamElements) {
                                //表达式参数字段名
                                Attribute expreParamNameAttribute = expParmElement.attribute("expreParamName");
                                checkAttribute(expreParamNameAttribute,"表达式参数字段名");
                                //表达式参数描述
                                Attribute expreParamDescAttribute = expParmElement.attribute("expreParamDesc");
                                checkAttribute(expreParamDescAttribute,"表达式参数描述");
                                //参数值
                                Attribute expreParamValueAttribute = expParmElement.attribute("expreParamValue");
                                checkAttribute(expreParamValueAttribute,"表达式参数值");
                                //表达式名称
                                //Attribute expreParamValueTextAttribute = expParmElement.attribute("expreParamValueText");
                                //checkAttribute(expreParamValueTextAttribute,"表达式参数名称");
                                //参数来源
                                Attribute expreParamSourceAttribute = expParmElement.attribute("expreParamSource");
                                checkAttribute(expreParamSourceAttribute,"表达式参数来源");
                                //参数类型
                                Attribute expreParamVarTypeAttribute = expParmElement.attribute("expreParamVarType");
                                checkAttribute(expreParamVarTypeAttribute,"表达式参数类型");
                                //表单id 暂时无用
                                Attribute formIdAttribute = expParmElement.attribute("formId");
                                //checkAttribute(formIdAttribute,"表达式参数表单id");
                                WfExpreUserParamDefDto wfExpreUserParamDefDto = new WfExpreUserParamDefDto();
                                wfExpreUserParamDefDto.setExpreParamName(expreParamNameAttribute.getValue());
                                wfExpreUserParamDefDto.setExpreParamDesc(expreParamDescAttribute.getValue());
                                wfExpreUserParamDefDto.setExpreParamValue(expreParamValueAttribute.getValue());
                                wfExpreUserParamDefDto.setExpreParamSource(expreParamSourceAttribute.getValue());
                                wfExpreUserParamDefDto.setNodeDefId(nodeDefId);
                                wfExpreUserParamDefDto.setNodeType(nodeType);
                                //wfExpreUserParamDefDto.setFormId(null !=formIdAttribute ?Long.valueOf(formIdAttribute.getValue()):null);
                                wfExpreUserParamDefDto.setExpreUserDefId(wfExpreUserDefDto.getId());
                                wfExpreUserParamDefDto.setExpreParamVarType(expreParamVarTypeAttribute.getValue());
                                wfExpreUserParamDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfExpreUserParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                wfExpreUserParamDefDto.setCamundaNodeDefId(camundaNodeDefId);
                                wfExpreUserParamDefDtoList.add(wfExpreUserParamDefDto);
                            }
                        }
                        if(CollectionUtil.isNotEmpty(wfExpreUserParamDefDtoList)) {
                            //如果不是 内置表达式
                            if(!String.valueOf(ExpressionBizTypeEnum.INNER_EXPRESSION.getType()).equals(bizTypeAttribute.getValue())) {
                                StringBuffer sb = new StringBuffer();
                                sb.append("${");
                                String _expre = expreExpressionAttribute.getValue();
                                String expre = specialCharsCheck(_expre);
                                expre = expre.substring(0,expre.length() - 1);
                                sb.append(expre);
                                List<HandleExpressionParam> argList = new ArrayList<>();
                                for (WfExpreUserParamDefDto wfExpreUserParamDefDto : wfExpreUserParamDefDtoList) {
                                    HandleExpressionParam handleExpressionParam = new HandleExpressionParam();
                                    handleExpressionParam.setParamId(wfExpreUserParamDefDto.getExpreParamName());
                                    handleExpressionParam.setParamValue(wfExpreUserParamDefDto.getExpreParamValue());
                                    handleExpressionParam.setParamType(wfExpreUserParamDefDto.getExpreParamSource()); //数据来源
                                    argList.add(handleExpressionParam);
                                }
                                String paramStr = JSON.toJSONString(argList);
                                sb.append(",'");
                                sb.append(paramStr);
                                sb.append("'");
                                sb.append(","+camundaNodeDefId+"");
                                sb.append(")}");
                                wfExpreUserDefDto.setExpression(sb.toString());
                                log.debug("解析用户节点-人员【表达式】为：{}", sb.toString());
                                wfExpreUserDefDto.setWfExpreUserParamDefDtoList(wfExpreUserParamDefDtoList);
                            } else {  //如果为内置表达式
                                StringBuffer sb = new StringBuffer();
                                sb.append("${");
                                String _expre = expreExpressionAttribute.getValue();
                                String expre = specialCharsCheck(_expre);
                                expre = expre.substring(0,expre.length() - 1);
                                sb.append(expre);
                                //内置表达式  有且只有一个参数
                                WfExpreUserParamDefDto wfExpreUserParamDefDto = wfExpreUserParamDefDtoList.get(0);
                                sb.append("'");
                                sb.append(wfExpreUserParamDefDto.getExpreParamValue());
                                sb.append("'");
                                sb.append(")}");
                                wfExpreUserDefDto.setExpression(sb.toString());
                                log.debug("解析用户节点-人员【表达式】为：{}", sb.toString());
                                wfExpreUserDefDto.setWfExpreUserParamDefDtoList(wfExpreUserParamDefDtoList);
                            }
                        }
                        wfExpreUserDefDtoList.add(wfExpreUserDefDto);
                    }
                }
            }
        }
        return wfExpreUserDefDtoList;
    }

    //解析变量
    public static List<WfVarDefDto> parseBpmnVarsElements(Element extensionElementsElement, ProcessDefinition processDefinition, Long procDefId, Long nodeDefId, String camundaNodeDefId, boolean isSeq) {
        List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
        if(null != extensionElementsElement) {
            Element varsElement = extensionElementsElement.element("vars");
            if(null != varsElement) {
                List<Element> varElements = varsElement.elements("var");
                if(CollectionUtil.isNotEmpty(varElements)) {
                    for (Element varElement : varElements) {
                        //变量字段名（驼峰）
                        Attribute javaNameAttribute = varElement.attribute("javaName");
                        checkAttribute(javaNameAttribute,"变量id");
                        Attribute expreJavaNameAttribute = varElement.attribute("expreJavaName");
                        checkAttribute(expreJavaNameAttribute,"表达式映射字段");
                        Attribute nameChAttribute = varElement.attribute("nameCh");
                        checkAttribute(nameChAttribute,"变量中文名");
                        //作用域
                        Attribute actionScopeAttribute = varElement.attribute("actionScope");
                        checkAttribute(actionScopeAttribute,"变量作用域");
                        //模型字段id
                        Attribute varIdAttribute = varElement.attribute("modelFieldId");
                        checkAttribute(varIdAttribute,"变量表单字段id");
                        //Attribute javaTypeAttribute = varElement.attribute("javaType");
                        //是否多行
                        Attribute isMoreRowAttribute = varElement.attribute("isMoreRow");
                        //表单id
                        Attribute formIdAttribute = varElement.attribute("formId");

                        Attribute formNameAttribute = varElement.attribute("formName");
                        //checkAttribute(formIdAttribute,"表达式参数表单id");

                        //表单映射变量
                        Attribute isOrmAttribute = varElement.attribute("isOrm");
                        checkAttribute(isOrmAttribute,"表单映射变量[isOrm]");
                        WfVarDefDto wfVarDefDto = new WfVarDefDto();
                        if(isSeq) {
                            wfVarDefDto.setSequDefId(nodeDefId);
                            wfVarDefDto.setCamundaSequDefId(camundaNodeDefId);
                        } else {
                            wfVarDefDto.setNodeDefId(nodeDefId);
                            wfVarDefDto.setCamundaNodeDefId(camundaNodeDefId);
                        }
                        wfVarDefDto.setProcdefId(procDefId);
                        wfVarDefDto.setModelFieldId(Long.valueOf(varIdAttribute.getValue()));
                        wfVarDefDto.setFormId(Long.valueOf(formIdAttribute.getValue()));
                        wfVarDefDto.setFormName(formNameAttribute.getValue());
                        wfVarDefDto.setActionScope(Integer.valueOf(actionScopeAttribute.getValue()));


                        wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfVarDefDto.setJavaName(javaNameAttribute.getValue());
                        wfVarDefDto.setExpreJavaName(expreJavaNameAttribute.getValue());
                        wfVarDefDto.setNameCh(nameChAttribute.getValue());
                        wfVarDefDto.setIsMoreRow(Integer.valueOf(isMoreRowAttribute.getValue()));
                        wfVarDefDto.setIsOrm(null != isOrmAttribute ? Integer.valueOf(isOrmAttribute.getValue()):null);
                        wfVarDefDtoList.add(wfVarDefDto);
                    }
                }
            }
        }
        return wfVarDefDtoList;
    }

    //解析外部子流程
    public static List<WfNodeDefDto> parseCallActivityElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition) {
        List<Element> callActivityElements = proElement.elements("callActivity");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(callActivityElements)) {
            for (Element callActivityElement : callActivityElements) {
                //id
                Attribute idAttribute = callActivityElement.attribute("id");
                checkAttribute(idAttribute,"外链子流程id");
                //名称
                Attribute nameAttribute = callActivityElement.attribute("name");
                checkAttribute(nameAttribute,"外链子流程名称");
                //calledElement
                Attribute calledElementAttribute = callActivityElement.attribute("calledElement");
                checkAttribute(calledElementAttribute,"外链子流程calledElement");
                //外部流程版本
                Attribute calledElementVersionAttribute = callActivityElement.attribute("calledElementVersion");
                checkAttribute(calledElementVersionAttribute,"外部流程版本");
                //cmd子流程流程定义
                Attribute cmdChildLogProcdefIdAttribute = callActivityElement.attribute("cmdChildLogProcdefId");
                checkAttribute(cmdChildLogProcdefIdAttribute,"camunda子流程流程定义");
                //子流程流程定义
                Attribute ctmChildLogProcdefIdAttribute = callActivityElement.attribute("ctmChildLogProcdefId");
                checkAttribute(ctmChildLogProcdefIdAttribute,"子流程流程定义");
                //子流程流程定义名称
                Attribute childProcdefNameAttribute = callActivityElement.attribute("childProcdefName");
                checkAttribute(childProcdefNameAttribute,"子流程流程定义名称");
                //是否改动标识
                Attribute isChangeAttribute = callActivityElement.attribute("isChange");
                WfNodeDefDto callActivityWfNodeDefDto = new WfNodeDefDto();//call activity 节点信息
                if(null  ==  isChangeAttribute) {
                    callActivityWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    callActivityWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                callActivityWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                callActivityWfNodeDefDto.setNodeName(nameAttribute.getValue());
                callActivityWfNodeDefDto.setNodeType(nodeType);
                callActivityWfNodeDefDto.setDueDate(null);
                callActivityWfNodeDefDto.setFollowUpDate(null);
                callActivityWfNodeDefDto.setPriority(null);
                callActivityWfNodeDefDto.setProcdefId(procDefId);
                callActivityWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                callActivityWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                callActivityWfNodeDefDto.setCamundaParentNodeDefId(null); //子流程节点的父级子流程信息

                //主流程和子流程 关系
                WfProcDefRelDto wfProcDefRelDto = new WfProcDefRelDto();
                wfProcDefRelDto.setMainCamundaProcdefId(processDefinition.getId());
                wfProcDefRelDto.setMainProcdefId(procDefId);
                wfProcDefRelDto.setMainProcdefName(processDefinition.getName());
                wfProcDefRelDto.setMainCamundaNodeDefId(idAttribute.getValue());
                wfProcDefRelDto.setMainNodeName(nameAttribute.getValue());
                wfProcDefRelDto.setMainNodeDefId(callActivityWfNodeDefDto.getId());//nodeDefId 数据库id
                if(StringUtils.isBlank(calledElementAttribute.getValue())) {
                    throw new IncloudException("callActivity节点"+ calledElementAttribute.getValue()+".选择的子流程内容不能为空！");
                }
                wfProcDefRelDto.setChildCamundaProcdefKey(calledElementAttribute.getValue());
                if(StringUtils.isBlank(calledElementVersionAttribute.getValue())) {
                    throw new IncloudException("callActivity节点"+ calledElementVersionAttribute.getValue()+".选择的子流程版本不能为空！");
                }
                wfProcDefRelDto.setChildProcdefVersion(Integer.valueOf(calledElementVersionAttribute.getValue()));
                wfProcDefRelDto.setChildProcdefId(Long.valueOf(ctmChildLogProcdefIdAttribute.getValue()));
                wfProcDefRelDto.setChildProcdefName(childProcdefNameAttribute.getValue());
                wfProcDefRelDto.setChildCamundaProcdefId(cmdChildLogProcdefIdAttribute.getValue());
                callActivityWfNodeDefDto.setWfProcDefRel(wfProcDefRelDto);

                //解析表达式
                List<WfExpreUserDefDto> wfExpreUserDefDtos =  parseHumanExpsElements(callActivityElement,nodeType,procDefId,processDefinition,callActivityWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfExpreUserDefDtos)) {
                    callActivityWfNodeDefDto.setWfExpreUserDefDtoList(wfExpreUserDefDtos);
                }
                //解析用户节点事件
                Element extensionElementsElement = callActivityElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.CALLACTIVITY_DEF_EVENT.code, procDefId, callActivityWfNodeDefDto.getId(),nameAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        callActivityWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                //解析变量
                List<WfVarDefDto> wfVarDefDtos = parseBpmnVarsElements(extensionElementsElement,processDefinition,procDefId,callActivityWfNodeDefDto.getId(),idAttribute.getValue(),false);
                if(CollectionUtil.isNotEmpty(wfVarDefDtos)) {
                    callActivityWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtos);
                }
                //是否多实例
                Element multiInstanceLoopCharacteristicsElement = callActivityElement.element("multiInstanceLoopCharacteristics");
                if(null != multiInstanceLoopCharacteristicsElement) {
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    callActivityWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code);
                }
                wfNodeDefDtoList.add(callActivityWfNodeDefDto);
            }

        }
        return wfNodeDefDtoList;
    }

    //解析并行网关
    public static List<WfNodeDefDto> parseParallelGatewayElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition) {
        List<Element> exclusiveGatewayElements = proElement.elements("parallelGateway");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(exclusiveGatewayElements)) {
            for (Element exclusiveGatewayElement : exclusiveGatewayElements) {
                //网关id
                Attribute idAttribute = exclusiveGatewayElement.attribute("id");
                checkAttribute(idAttribute,"并行网关id");
                //网关名称
                Attribute nameAttribute = exclusiveGatewayElement.attribute("name");
                //是否改动标识
                Attribute isChangeAttribute = exclusiveGatewayElement.attribute("isChange");
                checkAttribute(nameAttribute,"并行网关名称");
                WfNodeDefDto gatewayWfNodeDefDto = new WfNodeDefDto();
                if(null  ==  isChangeAttribute) {
                    gatewayWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    gatewayWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                gatewayWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                gatewayWfNodeDefDto.setNodeName(nameAttribute.getValue());
                gatewayWfNodeDefDto.setNodeType(nodeType);
                gatewayWfNodeDefDto.setDueDate(null);
                gatewayWfNodeDefDto.setFollowUpDate(null);
                gatewayWfNodeDefDto.setPriority(null);
                gatewayWfNodeDefDto.setProcdefId(procDefId);
                gatewayWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                gatewayWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //解析事件
                Element extensionElementsElement = proElement.element("extensionElements");
                List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.GATEWAY_DEF_EVENT.code, procDefId , gatewayWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                    gatewayWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                }
                wfNodeDefDtoList.add(gatewayWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析合并网关
    public static List<WfNodeDefDto> parseparInclusiveGatewayElements(Element proElement, Integer nodeType,Long procDefId, ProcessDefinition processDefinition) {
        List<Element> exclusiveGatewayElements = proElement.elements("parallelGateway");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(exclusiveGatewayElements)) {
            for (Element parseparInclusiveGatewayElement : exclusiveGatewayElements) {
                //网关id
                Attribute idAttribute = parseparInclusiveGatewayElement.attribute("id");
                checkAttribute(idAttribute,"合并网关id");
                //网关名称
                Attribute nameAttribute = parseparInclusiveGatewayElement.attribute("name");
                checkAttribute(nameAttribute,"合并网关名称");
                //是否改动标识
                Attribute isChangeAttribute = parseparInclusiveGatewayElement.attribute("isChange");
                WfNodeDefDto gatewayWfNodeDefDto = new WfNodeDefDto();
                if(null  ==  isChangeAttribute) {
                    gatewayWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    gatewayWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                gatewayWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                gatewayWfNodeDefDto.setNodeName(nameAttribute.getValue());
                gatewayWfNodeDefDto.setNodeType(nodeType);
                gatewayWfNodeDefDto.setDueDate(null);
                gatewayWfNodeDefDto.setFollowUpDate(null);
                gatewayWfNodeDefDto.setPriority(null);
                gatewayWfNodeDefDto.setProcdefId(procDefId);
                gatewayWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                gatewayWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //解析事件
                Element extensionElementsElement = proElement.element("extensionElements");
                List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.GATEWAY_DEF_EVENT.code, procDefId , gatewayWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                    gatewayWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                }
                wfNodeDefDtoList.add(gatewayWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //解析服务节点
    public static List<WfNodeDefDto> parseServiceTaskEventElements(Element proElement, Integer nodeType, Long procDefId, ProcessDefinition processDefinition, String camundaSubProcessNodeDefId) {
        List<Element> userEventElements = proElement.elements("serviceTask");
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(userEventElements)) {
            for (Element userEventElement : userEventElements) {
                WfNodeDefDto serviceWfNodeDefDto = new WfNodeDefDto();
                //服务节点id
                Attribute idAttribute = userEventElement.attribute("id");
                checkAttribute(idAttribute,"服务节点id");
                //服务节点名称
                Attribute nameAttribute = userEventElement.attribute("name");
                checkAttribute(idAttribute,"服务节点名称");
                //过期时间
                Attribute dueDayAttribute = userEventElement.attribute("dueDay");
                //重要程度
                Attribute priorityAttribute = userEventElement.attribute("priority");
                //选择人规则
                Attribute selectRuleAttribute = userEventElement.attribute("selectRule");
                //批量审批
                Attribute batchRuleAttribute = userEventElement.attribute("batchRule");
                //是否支持撤回
                Attribute cancelRuleAttribute = userEventElement.attribute("cancelRule");
                //是否支持驳回
                Attribute returnRuleAttribute = userEventElement.attribute("returnRule");
                //是否改动标识
                Attribute isChangeAttribute = userEventElement.attribute("isChange");
                if(null  ==  isChangeAttribute) {
                    serviceWfNodeDefDto.setIsChange(YesNo.NO.code);
                } else {
                    serviceWfNodeDefDto.setIsChange(StringUtils.isNotBlank(isChangeAttribute.getValue())?  Integer.valueOf(isChangeAttribute.getValue()) : YesNo.NO.code);
                }
                serviceWfNodeDefDto.setCamundaNodeDefId(idAttribute.getValue());
                serviceWfNodeDefDto.setNodeName(nameAttribute.getName());
                serviceWfNodeDefDto.setNodeType(nodeType);
                if(null != dueDayAttribute) {
                    if(StringUtils.isNotBlank(dueDayAttribute.getValue())) {
                        Double douDueDay = Double.valueOf(dueDayAttribute.getValue());
                        if(null != douDueDay) {
                            serviceWfNodeDefDto.setDueDate(douDueDay);
                        }
                    }
                }
                serviceWfNodeDefDto.setPriority(null != priorityAttribute?Integer.valueOf(priorityAttribute.getValue()):null);
                serviceWfNodeDefDto.setProcdefId(procDefId);
                serviceWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                serviceWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    serviceWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                serviceWfNodeDefDto.setSelectRule(null != selectRuleAttribute? Integer.valueOf(selectRuleAttribute.getValue()):null);
                serviceWfNodeDefDto.setBatchRule(null != batchRuleAttribute? Integer.valueOf(batchRuleAttribute.getValue()):null);
                serviceWfNodeDefDto.setCancelRule(null != cancelRuleAttribute ? Integer.valueOf(cancelRuleAttribute.getValue()):null);
                serviceWfNodeDefDto.setReturnRule(null != returnRuleAttribute? Integer.valueOf(returnRuleAttribute.getValue()):null);

                //解析用户节点会签
                Element multiInstanceLoopElement = userEventElement.element("multiInstanceLoopCharacteristics");
                if(null != multiInstanceLoopElement) {
                    //passingHandle
                    Attribute passingHandleAttribute = multiInstanceLoopElement.attribute("passingHandle");
                    //通过率
                    Attribute passingRateAttribute = multiInstanceLoopElement.attribute("passingRate");
                    //不通过率
                    Attribute unpassingHandleAttribute = multiInstanceLoopElement.attribute("unpassingHandle");
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    serviceWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCETASK.code);
                    serviceWfNodeDefDto.setPassingRate(null != passingRateAttribute? BigDecimal.valueOf(Double.valueOf(passingRateAttribute.getValue())):null);
                    serviceWfNodeDefDto.setPassingHandle(null != passingHandleAttribute? Integer.valueOf(passingHandleAttribute.getValue()):null);
                    serviceWfNodeDefDto.setUnpassingHandle(null != unpassingHandleAttribute ? Integer.valueOf(unpassingHandleAttribute.getValue()):null);
                }
                //判断是否是 多任务节点
                if(null == multiInstanceLoopElement) {
                    serviceWfNodeDefDto.setIsMultiTask(YesNo.NO.code);
                } else {
                    serviceWfNodeDefDto.setIsMultiTask(YesNo.YES.code);
                }
                //解析用户节点事件
                Element extensionElementsElement = userEventElement.element("extensionElements");
                if(null != extensionElementsElement) {
                    List<WfEventDefDto> wfEventDefDto = parseBpmnExtensionElements(extensionElementsElement,processDefinition,EventTypeSignEnum.NODE_DEF_EVENT.code, procDefId, serviceWfNodeDefDto.getId(),nameAttribute.getValue());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        serviceWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                //解析表单及表单字段
                Element formsElement = extensionElementsElement.element("forms");
                List<WfFormDefDto> wfFormDefDtos = parseFomsProcess(formsElement,procDefId, processDefinition,serviceWfNodeDefDto.getId(),idAttribute.getValue());
                if(CollectionUtil.isNotEmpty(wfFormDefDtos)) {
                    serviceWfNodeDefDto.setWfFormDefDtos(wfFormDefDtos);
                }
                //解析变量
                List<WfVarDefDto> wfVarDefDtos = parseBpmnVarsElements(extensionElementsElement,processDefinition,procDefId,serviceWfNodeDefDto.getId(),idAttribute.getValue(),false);
                if(CollectionUtil.isNotEmpty(wfVarDefDtos)) {
                    serviceWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtos);
                }
                wfNodeDefDtoList.add(serviceWfNodeDefDto);
            }
        }
        return wfNodeDefDtoList;
    }

    //组装表单映射变量
    public static WfVarDefDto buildFromVars(Long procDefId, Long nodeDefId, Long modelFieldId, Long formId,String formName,Integer actionScope,
                    ProcessDefinition processDefinition, String camundaNodeDefId,String javaName, Integer isMoreRow, Integer isOrm, String nameCh) {
        WfVarDefDto wfVarDefDto = new WfVarDefDto();
        wfVarDefDto.setSequDefId(null);
        wfVarDefDto.setProcdefId(procDefId);
        wfVarDefDto.setNodeDefId(nodeDefId);
        wfVarDefDto.setModelFieldId(modelFieldId);
        wfVarDefDto.setFormId(formId);
        wfVarDefDto.setFormName(formName);
        wfVarDefDto.setActionScope(actionScope);
        wfVarDefDto.setCamundaSequDefId(null);
        wfVarDefDto.setCamundaNodeDefId(camundaNodeDefId);
        wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
        wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
        wfVarDefDto.setJavaName(javaName);
        wfVarDefDto.setNameCh(nameCh);
        //wfVarDefDto.setIsMoreRow(isMoreRow);
        wfVarDefDto.setIsOrm(isOrm);
        return wfVarDefDto;
    }

    /**
     * 处理表达式的特殊字符
     * @param xml
     * @return
     */
    public static String specialCharsCheck(String xml){
        String bpmn = xml.replace("&#38;", "&")
                .replace("&#39;", "'")

                .replace("&#40;","(")
                .replace("&#41;",")")
                .replace("&#42;","*") //声明
                .replace("&#43;","+")
                .replace("&#44;",",")
                .replace("&#45;","-")
                .replace("&#46;",".")
                .replace("&#47;","/")

                .replace("&#60;","<")
                .replace("&#61;","=")
                .replace("&#62;",">");
        return bpmn;
    }

    @SneakyThrows
    public static String convertXml(MultipartFile data){
        InputStream inputStream = data.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String xml = new String(bytes);
        return xml;
    }

    /**
     * 检查某个元素不能为空
     * @param attribute
     * @param msg
     */
    public static void checkAttribute(Attribute attribute, String msg) {
        if(null == attribute) {
            throw new IncloudException(msg + ".元素为空！");
        } else {
            if(StringUtils.isBlank(attribute.getValue())) {
                throw new IncloudException(msg + ".元素不能为空！");
            }
        }
    }

}

