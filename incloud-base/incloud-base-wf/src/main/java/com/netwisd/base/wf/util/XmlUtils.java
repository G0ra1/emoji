package com.netwisd.base.wf.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.netwisd.base.common.constants.ExpreParamVarTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.dto.*;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.xml.*;
import com.netwisd.common.core.exception.IncloudException;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: XML解析相关工具
 * @Author: XHL@netwisd.com
 * @Date: 2020/5/22 11:17 上午
 */
@Slf4j
@Component
public class XmlUtils {

    public static Bpmn xmltoDoc(String xml){
        xml = checkXml(xml);
        /*JSONObject xmlJSONObj = XML.toJSONObject(xml);
        //设置缩进
        String json = xmlJSONObj.toString(4);
        //输出格式化后的json
        System.out.println(json);*/
        JSONObject jsonObject = JSONUtil.parseFromXml(xml);
        Bpmn bpmn = JSONUtil.toBean(jsonObject, Bpmn.class);
        return bpmn;
    }

    public static Object xmlToBean(String xmlPath,Class<?> load) throws JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new File(xmlPath));
        return object;
    }

    /**
     * 清除bpmn 对象所有的dbId
     * @param bpmn
     * @return
     */
    public static Bpmn clearXmlDocDbId(Bpmn bpmn){
        bpmn.getBpmndefinitions().getBpmnprocess().setNetwisddbId(null);
        bpmn.getBpmndefinitions().getBpmnprocess().setNetwisddbFormId(null);
        BpmnextensionElements bpmnextensionElements = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnextensionElements();
        //清除扩展
        clearBpmnExtensionElements(bpmnextensionElements);
        //清除开始节点
        List<BpmnstartEvent> bpmnstartEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnstartEvent();
        if(CollectionUtil.isNotEmpty(bpmnstartEventList)) {
            for (BpmnstartEvent event : bpmnstartEventList) {
                event.setNetwisddbId(null);
                BpmnextensionElements startBpmnextensionElements = event.getBpmnextensionElements();
                clearBpmnExtensionElements(startBpmnextensionElements);
            }
        }
        //清除序列流节点
        List<BpmnsequenceFlow> bpmnsequenceFlow = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsequenceFlow();
        if(CollectionUtil.isNotEmpty(bpmnsequenceFlow)) {
            for (BpmnsequenceFlow flow : bpmnsequenceFlow) {
                flow.setNetwisddbId(null);
                BpmnextensionElements sequBpmnextensionElements = flow.getBpmnextensionElements();
                clearBpmnExtensionElements(sequBpmnextensionElements);
                BpmnconditionExpression bpmnconditionExpression = flow.getBpmnconditionExpression();
                if(null != bpmnconditionExpression) {
                    String content = bpmnconditionExpression.getContent();
                    if(StringUtils.isNotBlank(content)) {
                       content = tagContentCheck(content);
                        bpmnconditionExpression.setContent(content);
                    }
                }
            }
        }
        //清除用户节点
        List<BpmnuserTask> bpmnuserTaskList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnuserTask();
        if(CollectionUtil.isNotEmpty(bpmnuserTaskList)) {
            for (BpmnuserTask bpmnuserTask : bpmnuserTaskList) {
                bpmnuserTask.setNetwisddbId(null);
                BpmnextensionElements userTaskBpmnextensionElements = bpmnuserTask.getBpmnextensionElements();
                clearBpmnExtensionElements(userTaskBpmnextensionElements);
            }
        }
        //清除网关
        List<BpmnexclusiveGateway> bpmnexclusiveGatewayList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnexclusiveGateway();
        if(CollectionUtil.isNotEmpty(bpmnexclusiveGatewayList)) {
            for (BpmnexclusiveGateway bpmnexclusiveGateway : bpmnexclusiveGatewayList) {
                if(null != bpmnexclusiveGateway) {
                    bpmnexclusiveGateway.setNetwisddbId(null);
                    BpmnextensionElements gatewayBpmnextensionElements = bpmnexclusiveGateway.getBpmnextensionElements();
                    clearBpmnExtensionElements(gatewayBpmnextensionElements);
                }
            }
        }
        //清除结束节点
        List<BpmnendEvent> bpmnendEventList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnendEvent();
        if(CollectionUtil.isNotEmpty(bpmnendEventList)) {
            for (BpmnendEvent bpmnendEvent : bpmnendEventList) {
                bpmnendEvent.setNetwisddbId(null);
                BpmnextensionElements endBpmnextensionElements = bpmnendEvent.getBpmnextensionElements();
                clearBpmnExtensionElements(endBpmnextensionElements);
            }
        }
        //清除 子流程
        List<BpmnsubProcess> bpmnsubProcessList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmnsubProcess();
        if(CollectionUtil.isNotEmpty(bpmnsubProcessList)) {
            for (BpmnsubProcess bpmnsubProcess : bpmnsubProcessList) {
                clearBpmnsubProcess(bpmnsubProcess);
            }
        }
        //清除 callActivity
        List<BpmncallActivity> bpmncallActivityList = bpmn.getBpmndefinitions().getBpmnprocess().getBpmncallActivity();
        if(CollectionUtils.isNotEmpty(bpmncallActivityList)) {
            for (BpmncallActivity bpmncallActivity : bpmncallActivityList) {
                clearBpmnCallActivity(bpmncallActivity);
            }
        }
        return bpmn;
    }
    /**
     * 清除 callactivity
     * @param bpmncallActivity
     */
    public static void clearBpmnCallActivity(BpmncallActivity bpmncallActivity){
        bpmncallActivity.setNetwisddbId(null);
        //清除扩展
        BpmnextensionElements bpmnextensionElements = bpmncallActivity.getBpmnextensionElements();
        clearBpmnExtensionElements(bpmnextensionElements);
    }

    /**
     * 清除子流程的dbId
     * @param bpmnsubProcess
     */
    public static void clearBpmnsubProcess(BpmnsubProcess bpmnsubProcess){
        bpmnsubProcess.setNetwisddbId(null);
        //清除扩展
        BpmnextensionElements bpmnextensionElements = bpmnsubProcess.getBpmnextensionElements();
        clearBpmnExtensionElements(bpmnextensionElements);
        //清除开始节点
        List<BpmnstartEvent> bpmnstartEventList = bpmnsubProcess.getBpmnstartEvent();
        if(CollectionUtil.isNotEmpty(bpmnstartEventList)) {
            for (BpmnstartEvent event : bpmnstartEventList) {
                event.setNetwisddbId(null);
                BpmnextensionElements startBpmnextensionElements = event.getBpmnextensionElements();
                clearBpmnExtensionElements(startBpmnextensionElements);
            }
        }
        //清除序列流节点
        List<BpmnsequenceFlow> bpmnsequenceFlow = bpmnsubProcess.getBpmnsequenceFlow();
        if(CollectionUtil.isNotEmpty(bpmnsequenceFlow)) {
            for (BpmnsequenceFlow flow : bpmnsequenceFlow) {
                flow.setNetwisddbId(null);
                BpmnextensionElements sequBpmnextensionElements = flow.getBpmnextensionElements();
                clearBpmnExtensionElements(sequBpmnextensionElements);
            }
        }
        //清除用户节点
        List<BpmnuserTask> bpmnuserTaskList = bpmnsubProcess.getBpmnuserTask();
        if(CollectionUtil.isNotEmpty(bpmnuserTaskList)) {
            for (BpmnuserTask bpmnuserTask : bpmnuserTaskList) {
                bpmnuserTask.setNetwisddbId(null);
                BpmnextensionElements userTaskBpmnextensionElements = bpmnuserTask.getBpmnextensionElements();
                clearBpmnExtensionElements(userTaskBpmnextensionElements);
            }
        }
        //清除网关
        List<BpmnexclusiveGateway> bpmnexclusiveGatewayList = bpmnsubProcess.getBpmnexclusiveGateway();
        if(CollectionUtil.isNotEmpty(bpmnexclusiveGatewayList)) {
            for (BpmnexclusiveGateway bpmnexclusiveGateway : bpmnexclusiveGatewayList) {
                if(null != bpmnexclusiveGateway) {
                    bpmnexclusiveGateway.setNetwisddbId(null);
                    BpmnextensionElements gatewayBpmnextensionElements = bpmnexclusiveGateway.getBpmnextensionElements();
                    clearBpmnExtensionElements(gatewayBpmnextensionElements);
                }
            }
        }
        //清除结束节点
        List<BpmnendEvent> bpmnendEventList = bpmnsubProcess.getBpmnendEvent();
        if(CollectionUtil.isNotEmpty(bpmnendEventList)) {
            for (BpmnendEvent bpmnendEvent : bpmnendEventList) {
                bpmnendEvent.setNetwisddbId(null);
                BpmnextensionElements endBpmnextensionElements = bpmnendEvent.getBpmnextensionElements();
                clearBpmnExtensionElements(endBpmnextensionElements);
            }
        }
        //清除子流程
        List<BpmnsubProcess> bpmnsubProcessList = bpmnsubProcess.getBpmnsubProcess();
        if(CollectionUtil.isNotEmpty(bpmnsubProcessList)) {
            for (BpmnsubProcess process : bpmnsubProcessList) {
                if(null != process) {
                    clearBpmnsubProcess(process);
                }
            }
        }
        //清除 callActivity
        List<BpmncallActivity> bpmncallActivityList = bpmnsubProcess.getBpmncallActivity();
        if(CollectionUtils.isNotEmpty(bpmncallActivityList)) {
            for (BpmncallActivity bpmncallActivity : bpmncallActivityList) {
                clearBpmnCallActivity(bpmncallActivity);
            }
        }
    }

    /**
     * 清除扩展节点的dbId
     * @param bpmnextensionElements
     */
    public static void clearBpmnExtensionElements(BpmnextensionElements bpmnextensionElements) {
        if(null != bpmnextensionElements) {
            //camunda executionListener
            List<CamundaexecutionListener> camundaexecutionListenerList = bpmnextensionElements.getCamundaexecutionListener();
            if(CollectionUtil.isNotEmpty(camundaexecutionListenerList)) {
                for (CamundaexecutionListener camundaexecutionListener : camundaexecutionListenerList) {
                    if(null != camundaexecutionListener) {
                        camundaexecutionListener.setNetwisddbId(null);
                        List<Camundafield> camundafieldList = camundaexecutionListener.getCamundafield();
                        if(CollectionUtil.isNotEmpty(camundafieldList)) {
                            for (Camundafield camundafield : camundafieldList) {
                                camundafield.setNetwisddbId(null);
                            }
                        }
                    }
                }
            }
            //netwisd executionListener
            List<NetwisdexecutionListener> netwisdexecutionListenerList = bpmnextensionElements.getNetwisdexecutionListener();
            if(CollectionUtil.isNotEmpty(netwisdexecutionListenerList)) {
                for (NetwisdexecutionListener netwisdexecutionListener : netwisdexecutionListenerList) {
                    if(null != netwisdexecutionListener) {
                        netwisdexecutionListener.setDbId(null);
                        List<Netwisdfields> netwisdfieldsList = netwisdexecutionListener.getNetwisdfield();
                        if(CollectionUtil.isNotEmpty(netwisdfieldsList)) {
                            for (Netwisdfields netwisdfields : netwisdfieldsList) {
                                netwisdfields.setDbId(null);
                            }
                        }
                    }
                }
            }
            //Camunda taskListener
            List<CamundataskListener> camundataskListenerList = bpmnextensionElements.getCamundataskListener();
            if(CollectionUtil.isNotEmpty(camundataskListenerList)) {
                for (CamundataskListener camundataskListener : camundataskListenerList) {
                    if(null != camundataskListener) {
                        camundataskListener.setNetwisddbId(null);
                        List<Camundafield> camundafieldList = camundataskListener.getCamundafield();
                        if(CollectionUtil.isNotEmpty(camundafieldList)) {
                            for (Camundafield camundafield : camundafieldList) {
                                if(null != camundafield) {
                                    camundafield.setNetwisddbId(null);
                                }
                            }
                        }
                    }
                }
            }
            //netwisd taskListener
            List<NetwisdtaskListener> netwisdtaskListenerList = bpmnextensionElements.getNetwisdtaskListener();
            if(CollectionUtil.isNotEmpty(netwisdtaskListenerList)) {
                for (NetwisdtaskListener netwisdtaskListener : netwisdtaskListenerList) {
                    if(null != netwisdtaskListener) {
                        netwisdtaskListener.setDbId(null);
                        List<Netwisdfields> netwisdfieldsList = netwisdtaskListener.getNetwisdfield();
                        if(CollectionUtil.isNotEmpty(netwisdfieldsList)) {
                            for (Netwisdfields netwisdfields : netwisdfieldsList) {
                                if(null != netwisdfields) {
                                    netwisdfields.setDbId(null);
                                }
                            }
                        }
                    }
                }
            }

            NetwisdhumanExps netwisdhumanExps = bpmnextensionElements.getNetwisdhumanExps();
            if(null != netwisdhumanExps) {
                List<NetwisdhumanExp> netwisdhumanExp = netwisdhumanExps.getNetwisdhumanExp();
                if(CollectionUtil.isNotEmpty(netwisdhumanExp)) {
                    for (NetwisdhumanExp exp : netwisdhumanExp) {
                        exp.setDbId(null);
                        List<NetwisdhumanExpParam> netwisdhumanExpParam = exp.getNetwisdhumanExpParam();
                        if(CollectionUtil.isNotEmpty(netwisdhumanExpParam)) {
                            for (NetwisdhumanExpParam expParam : netwisdhumanExpParam) {
                                expParam.setDbId(null);
                            }
                        }
                    }
                }
            }
            NetwisdNodeForm netwisdnodeForm = bpmnextensionElements.getNetwisdnodeForm();
            if(null != netwisdnodeForm) {
                netwisdnodeForm.setDbId(null);
                List<NetwisdField> netwisdfieldList = netwisdnodeForm.getNetwisdfield();
                if(CollectionUtil.isNotEmpty(netwisdfieldList)) {
                    for (NetwisdField netwisdField : netwisdfieldList) {
                        netwisdField.setDbId(null);
                    }
                }
            }

            NetwisdnodeButtons netwisdnodeButtons = bpmnextensionElements.getNetwisdnodeButtons();
            if(null != netwisdnodeButtons) {
                List<NetwisdnodeButton> netwisdnodeButtonList = netwisdnodeButtons.getNetwisdnodeButton();
                if (CollectionUtil.isNotEmpty(netwisdnodeButtonList)) {
                    for (NetwisdnodeButton netwisdnodeButton : netwisdnodeButtonList) {
                        if (null != netwisdnodeButton) {
                            netwisdnodeButton.setDbId(null);
                        }
                    }
                }
            }
            NetwisdVars netwisdvars = bpmnextensionElements.getNetwisdvars();
            if(null != netwisdvars) {
                List<NetwisdVar> netwisdvarList = netwisdvars.getNetwisdvar();
                if(CollectionUtil.isNotEmpty(netwisdvarList)) {
                    for (NetwisdVar netwisdVar : netwisdvarList) {
                        if(null != netwisdVar) {
                            netwisdVar.setDbId(null);
                        }
                    }
                }
            }
            List<NetwisdsequExps> netwisdsequExps = bpmnextensionElements.getNetwisdsequExps();
            if(CollectionUtil.isNotEmpty(netwisdsequExps)) {
                for (NetwisdsequExps netwisdsequExp : netwisdsequExps) {
                    if(null != netwisdsequExp) {
                        List<NetwisdsequExp> netwisdsequExpList = netwisdsequExp.getNetwisdsequExp();
                        if(CollectionUtil.isNotEmpty(netwisdsequExpList)) {
                            for (NetwisdsequExp exp : netwisdsequExpList) {
                                if(null != exp) {
                                    exp.setDbId(null);
                                    List<NetwisdsequExpParam> netwisdsequExpParamList = exp.getNetwisdsequExpParam();
                                    if(CollectionUtil.isNotEmpty(netwisdsequExpParamList)) {
                                        for (NetwisdsequExpParam netwisdsequExpParam : netwisdsequExpParamList) {
                                            if(null != netwisdsequExpParam) {
                                                netwisdsequExpParam.setDbId(null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            String netwisdsequExpText = bpmnextensionElements.getNetwisdsequExpText();
            if(StringUtils.isNotBlank(netwisdsequExpText)) {
                netwisdsequExpText = tagContentCheck(netwisdsequExpText);
                bpmnextensionElements.setNetwisdsequExpText(netwisdsequExpText);
            }
        }
    }

    public static String checkXml(String xml){
        String bpmn = xml.replace("bpmn:", "bpmn")
                .replace("camunda:","camunda")
                .replace("xsi:","xsi")

                .replace("xmlns:","xmlns")
                .replace("bpmndi:","bpmndi")
                .replace("di:","di")
                .replace("dc:","dc")
                .replace("class","clazz")

                .replace("netwisd:","netwisd");

        return bpmn;
    }

    public static String unCheckXml(String xml){
        String bpmn = xml.replace("<bpmn", "<bpmn:")
                .replace("</bpmn", "</bpmn:")

                .replace("camunda","camunda:")
                .replace("xmlns","xmlns:")
                .replace("xmlns:camunda:","xmlns:camunda") //声明
                .replace("camunda:.org","camunda.org")
                .replace("<bpmn:di","<bpmndi:")
                .replace("</bpmn:di","</bpmndi:")
                .replace("<di","<di:")
                .replace("<dc","<dc:")

                .replace("netwisd","netwisd:")
                .replace("netwisd:.org","netwisd.org")
                .replace(".netwisd:",".netwisd")
                .replace("xmlns:netwisd:","xmlns:netwisd") //声明
                .replace("xsitype","xsi:type")
                .replace("clazz","class")
                .replace("clazz","class")
                .replace("bpmntFormalExpression","bpmn:tFormalExpression");
                //.replace("&amp;","&");
                //.replace("&#38;","&");
        return bpmn;
    }

    /**
     * 保存时
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

    public static String objectToXml(Object t,Class<?>... classes) throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(classes);
        Marshaller ms = jbc.createMarshaller();
        StringWriter stringWriter = new StringWriter();

        // 不进行转义字符的处理
        ms.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                    public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer) throws IOException {
                        writer.write(ch, start, length);
                    }
        });
        ms.marshal(t,stringWriter);
        return stringWriter.toString();
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
     * //解析流程定义 基础信息
     * @param bpmnprocess
     * @param deployment
     * @param processDefinition
     * @return
     */
    public static WfProcDefDto resolveProcessDef(Bpmnprocess bpmnprocess, Deployment deployment, ProcessDefinition processDefinition,WfProcdefService wfProcdefService) {
        WfProcDefDto wfProcdefDto = new WfProcDefDto();
        wfProcdefDto.setCamundaProcdefId(processDefinition.getId());
        wfProcdefDto.setProcdefName(bpmnprocess.getName());
        wfProcdefDto.setCamundaProcdefKey(processDefinition.getKey());
        wfProcdefDto.setProcdefVersion(processDefinition.getVersion());
        wfProcdefDto.setDeploymentId(processDefinition.getDeploymentId());
        wfProcdefDto.setResourceName(processDefinition.getResourceName());
        wfProcdefDto.setSuspentionState(YesNo.NO.code);//0 否 1是
        wfProcdefDto.setTenantId(processDefinition.getTenantId());
        wfProcdefDto.setVersionTag(processDefinition.getVersionTag());
        wfProcdefDto.setStartable(bpmnprocess.getIsExecutable().equals("true")?YesNo.YES.code:YesNo.NO.code);
        LocalDateTime ldt = deployment.getDeploymentTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        wfProcdefDto.setDeployTime(ldt);
        wfProcdefDto.setDataSource("");
        LoginAppUser loginAppUser =  AppUserUtil.getLoginAppUser();
        wfProcdefDto.setCreateUserId(loginAppUser.getId());
        wfProcdefDto.setCreateUserName(loginAppUser.getUserNameCh());
        wfProcdefDto.setProcdefTypeId(StringUtils.isNoneBlank(bpmnprocess.getNetwisdprocdefTypeId())?Long.valueOf(bpmnprocess.getNetwisdprocdefTypeId()):null);
        wfProcdefDto.setProcdefTypeName(bpmnprocess.getNetwisdprocdefTypeName());
        wfProcdefDto.setRemindSign(YesNo.YES.code + "");//0 否 1是
        wfProcdefDto.setRemark(bpmnprocess.getNetwisdremark());
        //设置子流程def id
        List<BpmncallActivity> bpmncallActivityList = bpmnprocess.getBpmncallActivity();
        if(CollectionUtils.isNotEmpty(bpmncallActivityList)) {
            StringBuilder camunda_sb = new StringBuilder();
            StringBuilder custom_sb = new StringBuilder();
            for (BpmncallActivity bpmncallActivity : bpmncallActivityList) {
                String netwisdisLookMe = bpmncallActivity.getNetwisdisLookMe();
                if(StringUtils.isNotBlank(netwisdisLookMe)) {
                    Integer isLookMeInt = Integer.valueOf(netwisdisLookMe);
                    if(isLookMeInt == YesNo.YES.code) {
                        camunda_sb.append(bpmncallActivity.getNetwisdcmdChildLogProcdefId());
                        camunda_sb.append(",");
                        custom_sb.append(bpmncallActivity.getNetwisdctmChildLogProcdefId());
                        custom_sb.append(",");
                    }
                }
            }
//           String camundaStr = camunda_sb.toString();
//           String customStr = custom_sb.toString();
//           if(StringUtils.isNotBlank(camundaStr)) {
//               wfProcdefDto.setCamundaChildLogProcdefId(camundaStr.substring(0,camundaStr.length()-1));
//           }
//           if(StringUtils.isNotBlank(customStr)) {
//               wfProcdefDto.setChildLogProcdefId(customStr.substring(0,customStr.length()-1));
//           }
        }

//        String netwisdchildLogCallActivityId = bpmnprocess.getNetwisdchildLogProcdefId();
//        if(StringUtils.isNotBlank(netwisdchildLogCallActivityId)) {
//           String [] childArr =  netwisdchildLogCallActivityId.split(",");
//           StringBuilder camunda_sb = new StringBuilder();
//           StringBuilder custom_sb = new StringBuilder();
//           if(childArr.length > 0) {
//               for (String c : childArr) {
//                   String [] singleArr = c.split("__and__");
//                   if(singleArr.length == 2) {
//                      String camundaChildLogProcdefId =  singleArr[1];
//                      if(StringUtils.isNotBlank(camundaChildLogProcdefId)) {
//                          camunda_sb.append(camundaChildLogProcdefId);
//                          camunda_sb.append(",");
//                          WfProcDef wfProcDef = wfProcdefService.getProcDefByCamundaProcdefId(camundaChildLogProcdefId);
//                          if(ObjectUtil.isNotNull(wfProcDef)) {
//                              custom_sb.append(wfProcDef.getId());
//                              custom_sb.append(",");
//                          }
//                      }
//                   }
//               }
//           }
//           String camundaStr = camunda_sb.toString();
//           String customStr = custom_sb.toString();
//           if(StringUtils.isNotBlank(camundaStr)) {
//               wfProcdefDto.setCamundaChildLogProcdefId(camundaStr.substring(0,camundaStr.length()-1));
//           }
//            if(StringUtils.isNotBlank(customStr)) {
//                wfProcdefDto.setChildLogProcdefId(customStr.substring(0,customStr.length()-1));
//            }
//        }


        if(StringUtils.isBlank(bpmnprocess.getNetwisdprocdefTypeId())) {
            throw new IncloudException("没有关联流程分类信息！");
        }

        if(StringUtils.isBlank(bpmnprocess.getNetwisdformId())) {
            throw new IncloudException("没有关联表单信息！");
        }
//        wfProcdefDto.setFormId(Long.valueOf(bpmnprocess.getNetwisdformId()));
//        wfProcdefDto.setFormName(bpmnprocess.getNetwisdformName());

        //如果编辑的时候 还存在dbId 则直接赋值 如果没有则使用DTO自己生成
        if(StringUtils.isNotBlank(bpmnprocess.getNetwisddbId())) {
            bpmnprocess.setNetwisddbId(bpmnprocess.getNetwisddbId()); //设置流程定义dbId
            wfProcdefDto.setId(Long.valueOf(bpmnprocess.getNetwisddbId()));
        } else {
            log.debug("BPMN解析-xml编辑：流程定义dbId不存在,使用DTO生成.");
            bpmnprocess.setNetwisddbId(String.valueOf(wfProcdefDto.getId()));
        }

//        if(StringUtils.isNotBlank(bpmnprocess.getNetwisddbFormId())) {
//            bpmnprocess.setNetwisddbFormId(bpmnprocess.getNetwisddbFormId()); //设置表单dbId
//            wfProcdefDto.setFormId(Long.valueOf(bpmnprocess.getNetwisddbFormId()));
//        } else {
//            log.debug("BPMN解析-xml编辑：流程定义DbFormId不存在,使用DTO生成.");
//            bpmnprocess.setNetwisddbFormId(String.valueOf(new IDto().getId())); //设置表单dbId
//        }

        return wfProcdefDto;
    }

    /**
     * 解析事件信息
     * @param bpmnextensionElements bpmn对选哪个
     * @param procDefId 流程定义id
     * @param nodeDefId 节点定义id
     * @param eventTypeSign 事件所属类型
     * @return
     */
    public static List<WfEventDefDto> resolveEventDef(BpmnextensionElements bpmnextensionElements, Long procDefId,Long nodeDefId, Integer eventTypeSign, ProcessDefinition processDefinition, String camundaNodeDefId) {
        List<WfEventDefDto> wfEventDefDtoList = new ArrayList<>();
        if(null != bpmnextensionElements) {
            //camunda executionListener
            List<CamundaexecutionListener> camundaexecutionListener = bpmnextensionElements.getCamundaexecutionListener();
            if(CollectionUtil.isNotEmpty(camundaexecutionListener)) {
                //循环所有执行事件
                for (CamundaexecutionListener listener : camundaexecutionListener) {
                    WfEventDefDto wfEventDefDto = new WfEventDefDto();
                    wfEventDefDto.setEventBindType(listener.getEvent());
                    wfEventDefDto.setEventId(Long.valueOf(listener.getNetwisdeventId()));
                    wfEventDefDto.setEventSubmitSign(StringUtils.isNotBlank(listener.getNetwisdeventSubmitSign())?Integer.valueOf(listener.getNetwisdeventSubmitSign()):null);
                    wfEventDefDto.setProcdefId(procDefId);
                    wfEventDefDto.setNodeDefId(nodeDefId);
                    wfEventDefDto.setEventTypeSign(eventTypeSign);
                    wfEventDefDto.setEventType(EventTypeEnum.EXE_EVENT.code);

                    wfEventDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    wfEventDefDto.setCamundaNodeDefId(camundaNodeDefId);
                    wfEventDefDto.setNetwisdOrder(StringUtils.isNotBlank(listener.getNetwisdorder())?Integer.valueOf(listener.getNetwisdorder()):0);

                    if(StringUtils.isNotBlank(listener.getNetwisddbId())) {
                        wfEventDefDto.setId(Long.valueOf(listener.getNetwisddbId()));//设置事件 dbId
                    } else {
                        log.debug("BPMN解析-xml编辑：事件dbId不存在,使用DTO生成." + listener.toString());
                        listener.setNetwisddbId(String.valueOf(wfEventDefDto.getId()));//设置事件 dbId
                    }

                    //解析事件参数
                    List<Camundafield> camundafieldList = listener.getCamundafield();
                    if(CollectionUtil.isNotEmpty(camundafieldList)) {
                        List<WfEventParamDefDto> wfEventParamDefDtoList = new ArrayList<>();
                        for (Camundafield camundafield : camundafieldList) {
                            WfEventParamDefDto wfEventParamDefDto = new WfEventParamDefDto();
                            wfEventParamDefDto.setEventDefId(wfEventDefDto.getId());
                            wfEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfEventParamDefDto.setCamundaNodeDefId(camundaNodeDefId);
                            wfEventParamDefDto.setNodeDefId(nodeDefId);
                            String str = camundafield.getCamundastring();
                            String expression = camundafield.getCamundaexpression();
                            if(StringUtils.isNotBlank(str)) {
                                wfEventParamDefDto.setParamType("String");
                                wfEventParamDefDto.setParamValue(str);
                            } else {
                                wfEventParamDefDto.setParamType("expression");
                                wfEventParamDefDto.setParamValue(expression);
                            }
                            if(StringUtils.isBlank(str)&&StringUtils.isBlank(expression)) {
                                throw new IncloudException("事件参数必须赋值");
                            }
                            wfEventParamDefDto.setParamId(camundafield.getNetwisdid());
                            wfEventParamDefDtoList.add(wfEventParamDefDto);

                            if(StringUtils.isNotBlank(camundafield.getNetwisddbId())) {
                                wfEventParamDefDto.setId(Long.valueOf(camundafield.getNetwisddbId()));//设置事件参数 dbId
                            } else {
                                log.debug("BPMN解析-xml编辑：事件-参数 dbId不存在,使用DTO生成." + camundafield.toString());
                                camundafield.setNetwisddbId(String.valueOf(wfEventParamDefDto.getId()));//设置事件参数 dbId
                            }
                        }
                        wfEventDefDto.setWfEventParamDefDto(wfEventParamDefDtoList);
                    }
                    wfEventDefDtoList.add(wfEventDefDto);
                }
            }
            //camunda taskListener
            List<CamundataskListener> camundataskListener = bpmnextensionElements.getCamundataskListener();
            if(CollectionUtil.isNotEmpty(camundataskListener)) {
                //循环所有Task事件
                for (CamundataskListener listener : camundataskListener) {
                    WfEventDefDto wfEventDefDto = new WfEventDefDto();
                    wfEventDefDto.setEventBindType(listener.getEvent());
                    wfEventDefDto.setEventId(Long.valueOf(listener.getNetwisdeventId()));
                    wfEventDefDto.setEventSubmitSign(StringUtils.isNotBlank(listener.getNetwisdeventSubmitSign())?Integer.valueOf(listener.getNetwisdeventSubmitSign()):null);
                    wfEventDefDto.setProcdefId(procDefId);
                    wfEventDefDto.setNodeDefId(nodeDefId);
                    wfEventDefDto.setEventTypeSign(eventTypeSign);
                    wfEventDefDto.setEventType(EventTypeEnum.TASK_EVENT.code);
                    wfEventDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    wfEventDefDto.setCamundaNodeDefId(camundaNodeDefId);
                    wfEventDefDto.setNetwisdOrder(StringUtils.isNotBlank(listener.getNetwisdorder())?Integer.valueOf(listener.getNetwisdorder()):0);
                    if(StringUtils.isNotBlank(listener.getNetwisddbId())) {
                        wfEventDefDto.setId(Long.valueOf(listener.getNetwisddbId()));//设置事件 dbId
                    } else {
                        log.debug("BPMN解析-xml编辑：事件dbId不存在,使用DTO生成." + listener.toString());
                        listener.setNetwisddbId(String.valueOf(wfEventDefDto.getId()));//设置事件 dbId
                    }
                    //解析事件参数
                    List<Camundafield> camundafieldList = listener.getCamundafield();
                    if(CollectionUtil.isNotEmpty(camundafieldList)) {
                        List<WfEventParamDefDto> wfEventParamDefDtoList = new ArrayList<>();
                        for (Camundafield camundafield : camundafieldList) {
                            WfEventParamDefDto wfEventParamDefDto = new WfEventParamDefDto();
                            wfEventParamDefDto.setEventDefId(wfEventDefDto.getId());
                            String str = camundafield.getCamundastring();
                            String expression = camundafield.getCamundaexpression();
                            if(StringUtils.isNotBlank(str)) {
                                wfEventParamDefDto.setParamType("string");
                                wfEventParamDefDto.setParamValue(str);
                            } else {
                                wfEventParamDefDto.setParamType("expression");
                                wfEventParamDefDto.setParamValue(expression);
                            }
                            if(StringUtils.isBlank(str)&&StringUtils.isBlank(expression)) {
                                throw new IncloudException("事件参数必须赋值");
                            }
                            wfEventParamDefDto.setParamId(camundafield.getNetwisdid());
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

            //netwisd executionListener
            List<NetwisdexecutionListener> netwisdexecutionListener = bpmnextensionElements.getNetwisdexecutionListener();
            if(CollectionUtil.isNotEmpty(netwisdexecutionListener)) {
                //循环所有执行事件
                for (NetwisdexecutionListener listener : netwisdexecutionListener) {
                    WfEventDefDto wfEventDefDto = new WfEventDefDto();
                    wfEventDefDto.setEventBindType(listener.getEvent());
                    wfEventDefDto.setEventId(Long.valueOf(listener.getEventId()));
                    wfEventDefDto.setEventSubmitSign(StringUtils.isNotBlank(listener.getEventSubmitSign())?Integer.valueOf(listener.getEventSubmitSign()):null);
                    wfEventDefDto.setProcdefId(procDefId);
                    wfEventDefDto.setNodeDefId(nodeDefId);
                    wfEventDefDto.setEventTypeSign(eventTypeSign);
                    wfEventDefDto.setEventType(EventTypeEnum.EXE_EVENT.code);

                    wfEventDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    wfEventDefDto.setCamundaNodeDefId(camundaNodeDefId);
                    wfEventDefDto.setNetwisdOrder(StringUtils.isNotBlank(listener.getOrder())?Integer.valueOf(listener.getOrder()):0);

                    if(StringUtils.isNotBlank(listener.getDbId())) {
                        wfEventDefDto.setId(Long.valueOf(listener.getDbId()));//设置事件 dbId
                    } else {
                        log.debug("BPMN解析-xml编辑：事件dbId不存在,使用DTO生成." + listener.toString());
                        listener.setDbId(String.valueOf(wfEventDefDto.getId()));//设置事件 dbId
                    }

                    //解析事件参数
                    List<Netwisdfields> netwisdfieldsList = listener.getNetwisdfield();
                    if(CollectionUtil.isNotEmpty(netwisdfieldsList)) {
                        List<WfEventParamDefDto> wfEventParamDefDtoList = new ArrayList<>();
                        for (Netwisdfields netwisdfields : netwisdfieldsList) {
                            WfEventParamDefDto wfEventParamDefDto = new WfEventParamDefDto();
                            wfEventParamDefDto.setEventDefId(wfEventDefDto.getId());
                            wfEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfEventParamDefDto.setCamundaNodeDefId(camundaNodeDefId);
                            wfEventParamDefDto.setNodeDefId(nodeDefId);
                            String str = netwisdfields.getNetwisdstring();
                            String expression = netwisdfields.getNetwisdexpression();
                            if(StringUtils.isNotBlank(str)) {
                                wfEventParamDefDto.setParamType("String");
                                wfEventParamDefDto.setParamValue(str);
                            } else {
                                wfEventParamDefDto.setParamType("expression");
                                wfEventParamDefDto.setParamValue(expression);
                            }
                            if(StringUtils.isBlank(str)&&StringUtils.isBlank(expression)) {
                                throw new IncloudException("事件参数必须赋值");
                            }
                            wfEventParamDefDto.setParamId(netwisdfields.getId());
                            wfEventParamDefDtoList.add(wfEventParamDefDto);

                            if(StringUtils.isNotBlank(netwisdfields.getDbId())) {
                                wfEventParamDefDto.setId(Long.valueOf(netwisdfields.getDbId()));//设置事件参数 dbId
                            } else {
                                log.debug("BPMN解析-xml编辑：事件-参数 dbId不存在,使用DTO生成." + netwisdfields.toString());
                                netwisdfields.setDbId(String.valueOf(wfEventParamDefDto.getId()));//设置事件参数 dbId
                            }

                        }
                        wfEventDefDto.setWfEventParamDefDto(wfEventParamDefDtoList);
                    }
                    wfEventDefDtoList.add(wfEventDefDto);
                }
            }

            //netwisd taskListener
            List<NetwisdtaskListener> netwisdtaskListener = bpmnextensionElements.getNetwisdtaskListener();
            if(CollectionUtil.isNotEmpty(netwisdtaskListener)) {
                //循环所有Task事件
                for (NetwisdtaskListener listener : netwisdtaskListener) {
                    WfEventDefDto wfEventDefDto = new WfEventDefDto();
                    wfEventDefDto.setEventBindType(listener.getEvent());
                    wfEventDefDto.setEventId(Long.valueOf(listener.getEventId()));
                    wfEventDefDto.setEventSubmitSign(StringUtils.isNotBlank(listener.getEventSubmitSign())?Integer.valueOf(listener.getEventSubmitSign()):null);
                    wfEventDefDto.setProcdefId(procDefId);
                    wfEventDefDto.setNodeDefId(nodeDefId);
                    wfEventDefDto.setEventTypeSign(eventTypeSign);
                    wfEventDefDto.setEventType(EventTypeEnum.TASK_EVENT.code);
                    wfEventDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    wfEventDefDto.setCamundaNodeDefId(camundaNodeDefId);
                    wfEventDefDto.setNetwisdOrder(StringUtils.isNotBlank(listener.getOrder())?Integer.valueOf(listener.getOrder()):0);

                    if(StringUtils.isNotBlank(listener.getDbId())) {
                        wfEventDefDto.setId(Long.valueOf(listener.getDbId()));//设置事件 dbId
                    } else {
                        log.debug("BPMN解析-xml编辑：事件dbId不存在,使用DTO生成." + listener.toString());
                        listener.setDbId(String.valueOf(wfEventDefDto.getId()));//设置事件 dbId
                    }

                    //解析事件参数
                    List<Netwisdfields> netwisdfield = listener.getNetwisdfield();
                    if(CollectionUtil.isNotEmpty(netwisdfield)) {
                        List<WfEventParamDefDto> wfEventParamDefDtoList = new ArrayList<>();
                        for (Netwisdfields netwisdfields : netwisdfield) {
                            WfEventParamDefDto wfEventParamDefDto = new WfEventParamDefDto();
                            wfEventParamDefDto.setEventDefId(wfEventDefDto.getId());
                            String str = netwisdfields.getNetwisdstring();
                            String expression = netwisdfields.getNetwisdexpression();
                            if(StringUtils.isNotBlank(str)) {
                                wfEventParamDefDto.setParamType("String");
                                wfEventParamDefDto.setParamValue(str);
                            } else {
                                wfEventParamDefDto.setParamType("expression");
                                wfEventParamDefDto.setParamValue(expression);
                            }
                            if(StringUtils.isBlank(str)&&StringUtils.isBlank(expression)) {
                                throw new IncloudException("事件参数必须赋值");
                            }
                            wfEventParamDefDto.setParamId(netwisdfields.getId());
                            wfEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfEventParamDefDto.setCamundaNodeDefId(camundaNodeDefId);
                            wfEventParamDefDto.setNodeDefId(nodeDefId);
                            wfEventParamDefDtoList.add(wfEventParamDefDto);

                            if(StringUtils.isNotBlank(netwisdfields.getDbId())) {
                                wfEventParamDefDto.setId(Long.valueOf(netwisdfields.getDbId()));//设置事件参数 dbId
                            } else {
                                log.debug("BPMN解析-xml编辑：事件-参数 dbId不存在,使用DTO生成." + netwisdfields.toString());
                                netwisdfields.setDbId(String.valueOf(wfEventParamDefDto.getId()));//设置事件参数 dbId
                            }
                        }
                        wfEventDefDto.setWfEventParamDefDto(wfEventParamDefDtoList);
                    }
                    wfEventDefDtoList.add(wfEventDefDto);
                }
            }
        }
        return  wfEventDefDtoList;
    }

    /**
     * xml 解析dto 对象  按钮相关
     * @param bpmnextensionElements  xml 转的 bpmn 对象
     * @param nodefId 节点定义id
     * @param procdefId 流程定义id
     * @return
     */
    public static List<WfButtonDefDto> resolveButtonDef(BpmnextensionElements bpmnextensionElements,Long nodefId, Long procdefId, ProcessDefinition processDefinition) {
        NetwisdnodeButtons netwisdnodeButtons = bpmnextensionElements.getNetwisdnodeButtons();
        List<WfButtonDefDto> wfButtonDefDtoList = new ArrayList<>();
        if(null != netwisdnodeButtons) {
            List<NetwisdnodeButton> netwisdnodeButtonList = bpmnextensionElements.getNetwisdnodeButtons().getNetwisdnodeButton();
            if(CollectionUtil.isNotEmpty(netwisdnodeButtonList)) {
                for (NetwisdnodeButton netwisdnodeButton : netwisdnodeButtonList) {
                    WfButtonDefDto wfButtonDefDto = new WfButtonDefDto();
                    //wfButtonDefDto.setButtonId(netwisdnodeButton.getId());
                    wfButtonDefDto.setButtonName(netwisdnodeButton.getName());
                    //wfButtonDefDto.setIsEnable(Integer.valueOf(netwisdnodeButton.getIsEnable()));
                    wfButtonDefDto.setNodeDefId(nodefId);
                    wfButtonDefDto.setProcdefId(procdefId);
                    wfButtonDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfButtonDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    wfButtonDefDto.setCamundaNodeDefId(null);
                    wfButtonDefDto.setButtonCode(netwisdnodeButton.getCode());
                    wfButtonDefDtoList.add(wfButtonDefDto);

                    if(StringUtils.isNotBlank(netwisdnodeButton.getDbId())) {
                        wfButtonDefDto.setId(Long.valueOf(netwisdnodeButton.getDbId()));//设置按钮 dbId
                    } else {
                        log.debug("BPMN解析-xml编辑：按钮dbId不存在,使用DTO生成." + netwisdnodeButton.toString());
                        netwisdnodeButton.setDbId(String.valueOf(wfButtonDefDto.getId()));//设置按钮 dbId
                    }

                }
            }
        }
        return wfButtonDefDtoList;
    }

    /**
     * 解析开始节点 对应的dto 信息
     * @param bpmnstartEventList  xml bpmn 对象
     * @param nodeType 节点类型
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @return
     */
    public static List<WfNodeDefDto> resolveStartNodeDef(List<BpmnstartEvent> bpmnstartEventList, Integer nodeType,Long baseProcdefId, ProcessDefinition processDefinition,String camundaSubProcessNodeDefId) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnstartEventList)) {
            for (BpmnstartEvent bpmnstartEvent : bpmnstartEventList) {
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                startWfNodeDefDto.setCamundaNodeDefId(bpmnstartEvent.getId());
                if(StringUtils.isBlank(bpmnstartEvent.getName())) {
                    throw new IncloudException("开始节点名称不能为空！");
                }
                startWfNodeDefDto.setNodeName(bpmnstartEvent.getName());
                startWfNodeDefDto.setNodeType(nodeType);
                startWfNodeDefDto.setDueDate(null);
                startWfNodeDefDto.setFollowUpDate(null);
                startWfNodeDefDto.setPriority(null);
                startWfNodeDefDto.setProcdefId(baseProcdefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                if(StringUtils.isNotBlank(bpmnstartEvent.getNetwisddbId())) {
                    startWfNodeDefDto.setId(Long.valueOf(bpmnstartEvent.getNetwisddbId()));//设置开始节点 dbId
                } else {
                    log.debug("BPMN解析-xml编辑：开始节点dbId不存在,使用DTO生成." + bpmnstartEvent.toString());
                    bpmnstartEvent.setNetwisddbId(String.valueOf(startWfNodeDefDto.getId()));//设置开始节点 dbId
                }

                //解析开始节点事件
                BpmnextensionElements bpmnextensionElements = bpmnstartEvent.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    List<WfEventDefDto> wfEventDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,startWfNodeDefDto.getId(), EventTypeSignEnum.NODE_DEF_EVENT.code,processDefinition,bpmnstartEvent.getId());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                wfNodeDefDtoList.add(startWfNodeDefDto);
            }
        }
        return  wfNodeDefDtoList;
    }

    /**
     * 解析结束节点 对应的dto 信息
     * @param bpmnendEventList  xml bpmn 对象
     * @param nodeType 节点类型
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @return
     */
    public static List<WfNodeDefDto> resolveEndNodeDef(List<BpmnendEvent> bpmnendEventList, Integer nodeType,Long baseProcdefId, ProcessDefinition processDefinition,String camundaSubProcessNodeDefId) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnendEventList)) {
            for (BpmnendEvent bpmnendEvent : bpmnendEventList) {
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                startWfNodeDefDto.setCamundaNodeDefId(bpmnendEvent.getId());
                if(StringUtils.isBlank(bpmnendEvent.getName())) {
                    throw new IncloudException("结束节点名称不能为空！");
                }
                startWfNodeDefDto.setNodeName(bpmnendEvent.getName());
                startWfNodeDefDto.setNodeType(nodeType);
                startWfNodeDefDto.setDueDate(null);
                startWfNodeDefDto.setFollowUpDate(null);
                startWfNodeDefDto.setPriority(null);
                startWfNodeDefDto.setProcdefId(baseProcdefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                if(StringUtils.isNotBlank(bpmnendEvent.getNetwisddbId())) {
                    startWfNodeDefDto.setId(Long.valueOf(bpmnendEvent.getNetwisddbId())); //读xml中的数据库Id
                } else {
                    log.debug("BPMN解析-xml编辑：结束节点dbId不存在,使用DTO生成." + bpmnendEvent.toString());
                    bpmnendEvent.setNetwisddbId(String.valueOf(startWfNodeDefDto.getId()));//设置结束节点 dbId
                }

                //解析开始节点事件
                BpmnextensionElements bpmnextensionElements = bpmnendEvent.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    List<WfEventDefDto> wfEventDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,startWfNodeDefDto.getId(), EventTypeSignEnum.NODE_DEF_EVENT.code,processDefinition,bpmnendEvent.getId());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                }
                wfNodeDefDtoList.add(startWfNodeDefDto);
            }
        }
        return  wfNodeDefDtoList;
    }

    /**
     * 解析用户节点 对应的dto 信息
     * @param bpmnuserTaskList  xml bpmn 对象
     * @param nodeType 节点类型
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @return
     */
    public static List<WfNodeDefDto> resolveUserTaskNodeDef(List<BpmnuserTask> bpmnuserTaskList, Integer nodeType,Long baseProcdefId, ProcessDefinition processDefinition,Long formId,String camundaSubProcessNodeDefId) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnuserTaskList)) {
            //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (BpmnuserTask bpmnuserTask : bpmnuserTaskList) {
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                startWfNodeDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
                if(StringUtils.isBlank(bpmnuserTask.getName())) {
                    throw new IncloudException("用户节点名称不能为空！");
                }
                startWfNodeDefDto.setNodeName(bpmnuserTask.getName());
                startWfNodeDefDto.setNodeType(nodeType);
                //                if(StringUtils.isNotBlank(bpmnuserTask.getCamundadueDate())) {
                //                    LocalDateTime ldt = LocalDateTime.parse(bpmnuserTask.getCamundadueDate(),df);
                //                    startWfNodeDefDto.setDueDate(ldt);
                //                }
                //                if(StringUtils.isNotBlank(bpmnuserTask.getCamundafollowUpDate())) {
                //                    LocalDateTime ldt = LocalDateTime.parse(bpmnuserTask.getCamundadueDate(),df);
                //                    startWfNodeDefDto.setFollowUpDate(ldt);
                //                }
                if(StringUtils.isNotBlank(bpmnuserTask.getNetwisddueDay())) {
                    Double douDueDay = Double.valueOf(bpmnuserTask.getNetwisddueDay());
                    if(null != douDueDay) {
                        startWfNodeDefDto.setDueDate(douDueDay);
                    }
                }
                startWfNodeDefDto.setPriority(StringUtils.isNotBlank(bpmnuserTask.getCamundapriority())?Integer.valueOf(bpmnuserTask.getCamundapriority()):null);
                startWfNodeDefDto.setProcdefId(baseProcdefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }


                if(StringUtils.isNotBlank(bpmnuserTask.getNetwisddbId())) {
                    startWfNodeDefDto.setId(Long.valueOf(bpmnuserTask.getNetwisddbId()));
                } else {
                    log.debug("BPMN解析-xml编辑：用户节点dbId不存在,使用DTO生成." + bpmnuserTask.toString());
                    bpmnuserTask.setNetwisddbId(String.valueOf(startWfNodeDefDto.getId()));//设置用户节点 dbId
                }

                //解析node detail
                //WfNodeDetailDefDto wfNodeDetailDefDto = new WfNodeDetailDefDto();
                startWfNodeDefDto.setSelectRule(StringUtils.isNotBlank(bpmnuserTask.getNetwisdselectRule())? Integer.valueOf(bpmnuserTask.getNetwisdselectRule()):null);
                startWfNodeDefDto.setBatchRule(StringUtils.isNotBlank(bpmnuserTask.getNetwisdbatchRule())? Integer.valueOf(bpmnuserTask.getNetwisdbatchRule()):null);
                startWfNodeDefDto.setCancelRule(StringUtils.isNotBlank(bpmnuserTask.getNetwisdcancelRule())? Integer.valueOf(bpmnuserTask.getNetwisdcancelRule()):null);
                startWfNodeDefDto.setReturnRule(StringUtils.isNotBlank(bpmnuserTask.getNetwisdreturnRule())? Integer.valueOf(bpmnuserTask.getNetwisdreturnRule()):null);

                //startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                //startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());


//                if(StringUtils.isNotBlank(bpmnuserTask.getNetwisddbDetailId())) {
//                    bpmnuserTask.setNetwisddbDetailId(bpmnuserTask.getNetwisddbDetailId());//设置用户节点详情 dbDetailId
//                    log.debug("BPMN解析-xml编辑：用户节点dbId不存在,使用DTO生成." + bpmnuserTask.toString());
//                } else {
//                    bpmnuserTask.setNetwisddbDetailId(String.valueOf(wfNodeDetailDefDto.getId()));//设置用户节点详情 dbDetailId
//                }

                BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics = bpmnuserTask.getBpmnmultiInstanceLoopCharacteristics();
                if(null != bpmnmultiInstanceLoopCharacteristics) {
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    startWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCETASK.code);
                    startWfNodeDefDto.setPassingRate(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? BigDecimal.valueOf(Double.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())):null);
                    startWfNodeDefDto.setPassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingHandle()):null);
                    startWfNodeDefDto.setUnpassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle()):null);
                }

                //wfNodeDetailDefDto.setNodeDefId(startWfNodeDefDto.getId());
                //startWfNodeDefDto.setWfNodeDetailDefDto(wfNodeDetailDefDto);
                //判断是否是 多任务节点
                if(null == bpmnmultiInstanceLoopCharacteristics) {
                    startWfNodeDefDto.setIsMultiTask(YesNo.NO.code);
                } else {
                    startWfNodeDefDto.setIsMultiTask(YesNo.YES.code);
                }
                //解析人员信息
                //NetwisdhumanExps netwisdhumanExps = bpmnuserTask.getBpmnextensionElements().getNetwisdhumanExps();
//                if(null != netwisdhumanExps) {
//                    List<NetwisdhumanExp> netwisdhumanExp = netwisdhumanExps.getNetwisdhumanExp();
//                    List<WfExpreUserDefDto> wfExpreUserDefDtoList = new ArrayList<>();
//                    if(CollectionUtil.isNotEmpty(netwisdhumanExp)) {
//                        for (NetwisdhumanExp exp : netwisdhumanExp) {
//                            WfExpreUserDefDto wfExpreUserDefDto = new WfExpreUserDefDto();
//                            //如果是表达式 则正常处理
//                            if(String.valueOf(ExpressionBizTypeEnum.EXPRESSION.getType()).equals(exp.getBizType())) {
//                                wfExpreUserDefDto.setExpreId(StringUtils.isNotBlank(exp.getExpreId())?Long.valueOf(exp.getExpreId()):null);
//                                wfExpreUserDefDto.setNodeDefId(startWfNodeDefDto.getId());
//                                wfExpreUserDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
//                                wfExpreUserDefDto.setProcdefId(baseProcdefId);
//                                wfExpreUserDefDto.setCamundaProcdefId(processDefinition.getId());
//                                wfExpreUserDefDto.setCamundaProcdefKey(processDefinition.getKey());
//                                wfExpreUserDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
//                                wfExpreUserDefDto.setExpressionName(exp.getExpreName());
//                                wfExpreUserDefDto.setBizType(Integer.valueOf(exp.getBizType()));
//
//                                if(StringUtils.isNotBlank(exp.getDbId())) {
//                                    exp.setDbId(exp.getDbId());//设置表达式 dbId
//                                    log.debug("BPMN解析-xml编辑：用户节点-人员表达式 dbId不存在,使用DTO生成." + exp.toString());
//                                } else {
//                                    exp.setDbId(String.valueOf(wfExpreUserDefDto.getId()));//设置表达式 dbId
//                                }
//
//                                //处理参数
//                                List<NetwisdhumanExpParam> netwisdhumanExpParm = exp.getNetwisdhumanExpParam();
//                                List<WfExpreUserParamDefDto> wfExpreUserParamDefDtoList = new ArrayList<>();
//                                if(CollectionUtil.isNotEmpty(netwisdhumanExpParm)) {
//                                    for (NetwisdhumanExpParam expParm : netwisdhumanExpParm) {
//                                        WfExpreUserParamDefDto wfExpreUserParamDefDto = new WfExpreUserParamDefDto();
//                                        wfExpreUserParamDefDto.setExpreParamId(Long.valueOf(expParm.getExpreParamId()));
//                                        wfExpreUserParamDefDto.setExpreParamValue(expParm.getExpreParamValue());
//                                        wfExpreUserParamDefDto.setExpreParamSource(Integer.valueOf(expParm.getExpreParamSource()));
//                                        wfExpreUserParamDefDto.setNodeDefId(startWfNodeDefDto.getId());
//                                        wfExpreUserParamDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
//                                        wfExpreUserParamDefDto.setExpreUserDefId(wfExpreUserDefDto.getId());
//                                        wfExpreUserParamDefDto.setExpreParamVarType(expParm.getExpreParamVarType());
//                                        if(StringUtils.isBlank(expParm.getExpreParamVarType())) {
//                                            throw new IncloudException("人员表达式的参数类型不能为空！");
//                                        }
//                                        if(StringUtils.isNotBlank(expParm.getDbId())) {
//                                            expParm.setDbId(expParm.getDbId());//设置表达式参数 dbId
//                                            log.debug("BPMN解析-xml编辑：用户节点-人员表达式-参数 dbId不存在,使用DTO生成." + expParm.toString());
//                                        } else {
//                                            expParm.setDbId(String.valueOf(wfExpreUserParamDefDto.getId()));//设置表达式参数 dbId
//                                        }
//
//                                        wfExpreUserParamDefDto.setCamundaProcdefId(processDefinition.getId());
//                                        wfExpreUserParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
//                                        wfExpreUserParamDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
//                                        wfExpreUserParamDefDtoList.add(wfExpreUserParamDefDto);
//                                    }
//                                }
//                                if(CollectionUtil.isNotEmpty(wfExpreUserParamDefDtoList)) {
//                                    StringBuffer sb = new StringBuffer();
//                                    sb.append("${");
//                                    String _expre = exp.getExpreExpression();
//                                    String expre = specialCharsCheck(_expre);
//                                    expre = expre.substring(0,expre.length() - 1);
//                                    sb.append(expre);
//                                    //sb.append(",");
//                                    for (WfExpreUserParamDefDto wfExpreUserParamDefDto : wfExpreUserParamDefDtoList) {
//                                        Integer TypeVal = Integer.valueOf(wfExpreUserParamDefDto.getExpreParamVarType());
//                                        if(ExpreParamVarTypeEnum.USER_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.ROLE_EXPRE.code == TypeVal
//                                                || ExpreParamVarTypeEnum.POST_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.JOB_EXPRE.code == TypeVal
//                                                || ExpreParamVarTypeEnum.DEPT_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.MECHANISM_EXPRE.code == TypeVal
//                                                || ExpreParamVarTypeEnum.BUILTINROLE_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.STRING_EXPRE.code == TypeVal
//                                                || ExpreParamVarTypeEnum.DATE_EXPRE.code == TypeVal) {
//                                            sb.append("'");
//                                            sb.append(wfExpreUserParamDefDto.getExpreParamValue());
//                                            sb.append("'");
//                                            sb.append(",");
//                                        }
//                                        if(ExpreParamVarTypeEnum.BOOLEAN_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.INTEGER_EXPRE.code == TypeVal
//                                            ||ExpreParamVarTypeEnum.LONG_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.DOUBLE_EXPRE.code == TypeVal) {
//                                            sb.append(wfExpreUserParamDefDto.getExpreParamValue()+",");
//                                        }
//                                        //TODO List 暂时没有场景 暂时不处理
//                                    }
//                                    sb.setLength(sb.length()-1);
//                                    sb.append(")}");
//                                    wfExpreUserDefDto.setExpression(sb.toString());
//                                    log.debug("解析用户节点-人员【表达式】为：{}", sb.toString());
//                                    wfExpreUserDefDto.setWfExpreUserParamDefDtoList(wfExpreUserParamDefDtoList);
//                                }
//                                wfExpreUserDefDtoList.add(wfExpreUserDefDto);
//                            } else {
//                                // 如果不是表达式 则特殊处理(岗位、职位、人员、机构、部门、人员、人员角色、资源组)
//                                wfExpreUserDefDto.setExpreId(0L);
//                                wfExpreUserDefDto.setNodeDefId(startWfNodeDefDto.getId());
//                                wfExpreUserDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
//                                wfExpreUserDefDto.setProcdefId(baseProcdefId);
//                                wfExpreUserDefDto.setCamundaProcdefId(processDefinition.getId());
//                                wfExpreUserDefDto.setCamundaProcdefKey(processDefinition.getKey());
//                                wfExpreUserDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
//                                wfExpreUserDefDto.setExpressionName(exp.getExpreName());
//                                if(StringUtils.isNotBlank(exp.getBizId())) {
//                                    wfExpreUserDefDto.setBizId(exp.getBizId());
//                                    wfExpreUserDefDto.setBizType(Integer.valueOf(exp.getBizType()));
//                                }
//
//                                if(StringUtils.isNotBlank(exp.getDbId())) {
//                                    exp.setDbId(exp.getDbId());//设置表达式 dbId
//                                    log.debug("BPMN解析-xml编辑：用户节点-人员表达式 dbId不存在,使用DTO生成." + exp.toString());
//                                } else {
//                                    exp.setDbId(String.valueOf(wfExpreUserDefDto.getId()));//设置表达式 dbId
//                                }
//
//                                String bizId = exp.getBizId();
//                                StringBuilder sb = new StringBuilder();
//                                sb.append("${wfUserExpression.");//invokeMethod('
//                                if(String.valueOf(ExpressionBizTypeEnum.EMPL.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_USER_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.ROLE.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_ROLE_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.JOB.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_JOB_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.POST.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_POST_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.DEPT.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_DEPT_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.ORGAN.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_MECHANISM_ID);
//                                }
//                                if(String.valueOf(ExpressionBizTypeEnum.RESOURCE_GROUP.getType()).equals(exp.getBizType())){
//                                    sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_POST_RULES_ID);
//
//                                }
//                                sb.append("('");
//                                if(StringUtils.isNotBlank(bizId)) {
//                                    sb.append(bizId);
//                                }
//                                //sb.setLength(sb.length()-1);
//                                sb.append("')}");
//                                wfExpreUserDefDto.setExpression(sb.toString());
//                                log.debug("解析用户节点-人员表达式【基础业务选择】为：{}", sb.toString());
//                                wfExpreUserDefDtoList.add(wfExpreUserDefDto);
//                            }
//                        }
//                    }
//                    if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
//                        startWfNodeDefDto.setWfExpreUserDefDtoList(wfExpreUserDefDtoList);
//                    }
//                }

                //解析节点事件
                BpmnextensionElements bpmnextensionElements = bpmnuserTask.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    List<WfEventDefDto> wfEventDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,startWfNodeDefDto.getId(), EventTypeSignEnum.NODE_DEF_EVENT.code,processDefinition, bpmnuserTask.getId());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                    //解析人员表达式
                    List<WfExpreUserDefDto> wfExpreUserDefDtoList = resolveHumanExps(bpmnuserTask.getBpmnextensionElements().getNetwisdhumanExps(),startWfNodeDefDto,baseProcdefId,processDefinition);
                    if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
                        startWfNodeDefDto.setWfExpreUserDefDtoList(wfExpreUserDefDtoList);
                    }
                }

                //解析表单信息
                NetwisdNodeForm netwisdNodeForm = bpmnuserTask.getBpmnextensionElements().getNetwisdnodeForm();
                if(null != netwisdNodeForm) {
                    List<NetwisdField> netwisdfieldList = netwisdNodeForm.getNetwisdfield();
                    if(CollectionUtil.isNotEmpty(netwisdfieldList)) {
                        List<WfFormFieldsDefDto> wfFormFieldsDefDtoList = new ArrayList<>();
                        for (NetwisdField netwisdField : netwisdfieldList) {
                            WfFormFieldsDefDto wfFormFieldsDefDto = new WfFormFieldsDefDto();
                            wfFormFieldsDefDto.setNodeDefId(startWfNodeDefDto.getId());
                            //wfFormFieldsDefDto.setFormVarId(Long.valueOf(netwisdField.getId()));
                            //wfFormFieldsDefDto.setVarId(netwisdField.getVarId());
                            //wfFormFieldsDefDto.setIsView(Integer.valueOf(netwisdField.getIsView()));
                            //wfFormFieldsDefDto.setIsWrite(Integer.valueOf(netwisdField.getIsWrite()));
                            //wfFormFieldsDefDto.setPowerCode(Integer.valueOf(netwisdField.getPowerCode()));
                            wfFormFieldsDefDto.setFormId(formId);
                            wfFormFieldsDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfFormFieldsDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfFormFieldsDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
                            wfFormFieldsDefDto.setJavaType(netwisdField.getJavaType());
                            //wfFormFieldsDefDto.setVarName(netwisdField.getVarName());
                            //wfFormFieldsDefDto.setVarType(netwisdField.getVarType());
                            if(StringUtils.isNotBlank(netwisdField.getIsMoreRow())) {
                                wfFormFieldsDefDto.setIsMoreRow(Integer.valueOf(netwisdField.getIsMoreRow()));
                            }
                            wfFormFieldsDefDtoList.add(wfFormFieldsDefDto);

                            if(StringUtils.isNotBlank(netwisdField.getDbId())) {
                                netwisdField.setDbId(netwisdField.getDbId());//设置表单字段 dbId
                                log.debug("BPMN解析-xml编辑：用户节点-表单字段 dbId不存在,使用DTO生成." + netwisdField.toString());
                            } else {
                                netwisdField.setDbId(String.valueOf(wfFormFieldsDefDto.getId()));//设置表单字段 dbId
                            }
                        }
                        //startWfNodeDefDto.setWfFormFieldsDefDtoList(wfFormFieldsDefDtoList);
                    }
                }
                //解析按钮信息
                NetwisdnodeButtons netwisdnodeButtons = bpmnuserTask.getBpmnextensionElements().getNetwisdnodeButtons();
                if(null != netwisdnodeButtons) {
                    List<NetwisdnodeButton> netwisdnodeButton = netwisdnodeButtons.getNetwisdnodeButton();
                    if(CollectionUtil.isNotEmpty(netwisdnodeButton)) {
                        List<WfButtonDefDto> wfButtonDefDtoList = new ArrayList<>();
                        for (NetwisdnodeButton buttion : netwisdnodeButton) {
                            WfButtonDefDto wfButtonDefDto = new WfButtonDefDto();
                            wfButtonDefDto.setProcdefId(baseProcdefId);
                            wfButtonDefDto.setNodeDefId(startWfNodeDefDto.getId());
                            //wfButtonDefDto.setIsEnable(Integer.valueOf(buttion.getIsEnable()));
                            //wfButtonDefDto.setButtonId(buttion.getId());
                            wfButtonDefDto.setButtonName(buttion.getName());
                            wfButtonDefDto.setButtonCode(buttion.getCode());
                            wfButtonDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfButtonDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfButtonDefDto.setCamundaNodeDefId(bpmnuserTask.getId());

                            if(StringUtils.isNotBlank(buttion.getDbId())) {
                                buttion.setDbId(buttion.getDbId());//设置按钮 dbId
                                log.debug("BPMN解析-xml编辑：用户节点-按钮 dbId不存在,使用DTO生成." + buttion.toString());
                            } else {
                                buttion.setDbId(String.valueOf(wfButtonDefDto.getId()));//设置按钮 dbId
                            }
                            wfButtonDefDtoList.add(wfButtonDefDto);
                        }
                        startWfNodeDefDto.setWfButtonDefDtoListDto(wfButtonDefDtoList);
                    }
                }
                // 解析变量信息
                NetwisdVars netwisdVars = bpmnuserTask.getBpmnextensionElements().getNetwisdvars();
                if(null != netwisdVars) {
                    List<NetwisdVar> netwisdVar = netwisdVars.getNetwisdvar();
                    if(CollectionUtil.isNotEmpty(netwisdVar)) {
                        List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
                        for (NetwisdVar var : netwisdVar) {
                            WfVarDefDto wfVarDefDto = new WfVarDefDto();
                            wfVarDefDto.setSequDefId(null);
                            wfVarDefDto.setProcdefId(baseProcdefId);
                            wfVarDefDto.setNodeDefId(startWfNodeDefDto.getId());
                            //wfVarDefDto.setVarId(Long.valueOf(var.getVarId()));
                            wfVarDefDto.setFormId(formId);
                            wfVarDefDto.setActionScope(Integer.valueOf(var.getActionScope()));
                            wfVarDefDto.setCamundaSequDefId(null);
                            wfVarDefDto.setCamundaNodeDefId(bpmnuserTask.getId());
                            wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            //wfVarDefDto.setFormVarId(var.getFormVarId());
                            if(StringUtils.isNotBlank(var.getIsMoreRow())) {
                                wfVarDefDto.setIsMoreRow(Integer.valueOf(var.getIsMoreRow()));
                            }
                            wfVarDefDtoList.add(wfVarDefDto);

                            if(StringUtils.isNotBlank(var.getDbId())) {
                                var.setDbId(var.getDbId());//设置变量 dbId
                                log.debug("BPMN解析-xml编辑：用户节点-变量 dbId不存在,使用DTO生成." + var.toString());
                            } else {
                                var.setDbId(String.valueOf(wfVarDefDto.getId()));//设置变量 dbId
                            }
                        }
                        startWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtoList);
                    }
                }
                wfNodeDefDtoList.add(startWfNodeDefDto);
            }
        }
        return  wfNodeDefDtoList;
    }


    /**
     * 解析Gateway 对应的dto 信息
     * @param bpmnexclusiveGatewayList  xml bpmn 对象
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @return
     */
    public static List<WfNodeDefDto> resolveGatewayDef(List<BpmnexclusiveGateway> bpmnexclusiveGatewayList, Long baseProcdefId, ProcessDefinition processDefinition,String camundaSubProcessNodeDefId) {
        List<WfNodeDefDto> wfGatewayDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnexclusiveGatewayList)) {
            for (BpmnexclusiveGateway bpmnexclusiveGateway : bpmnexclusiveGatewayList) {
                WfNodeDefDto startWfNodeDefDto = new WfNodeDefDto();
                startWfNodeDefDto.setCamundaNodeDefId(bpmnexclusiveGateway.getId());
                if(StringUtils.isBlank(bpmnexclusiveGateway.getName())) {
                    throw new IncloudException("网关节点名称不能为空！");
                }
                startWfNodeDefDto.setNodeName(bpmnexclusiveGateway.getName());
                startWfNodeDefDto.setNodeType(NodeTypeEnum.EXCLUSIVEGATEWAY.code);
                startWfNodeDefDto.setDueDate(null);
                startWfNodeDefDto.setFollowUpDate(null);
                startWfNodeDefDto.setPriority(null);
                startWfNodeDefDto.setProcdefId(baseProcdefId);
                startWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                startWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    startWfNodeDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }

                if(StringUtils.isNotBlank(bpmnexclusiveGateway.getNetwisddbId())) {
                    startWfNodeDefDto.setId(Long.valueOf(bpmnexclusiveGateway.getNetwisddbId()));//设置网关 dbId
                    log.debug("BPMN解析-xml编辑：用户节点-变量 dbId不存在,使用DTO生成." + bpmnexclusiveGateway.toString());
                } else {
                    bpmnexclusiveGateway.setNetwisddbId(String.valueOf(startWfNodeDefDto.getId()));//设置网关 dbId
                }

                //解析网关事件
                BpmnextensionElements bpmnextensionElements = bpmnexclusiveGateway.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    List<WfEventDefDto> wfGatewayDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,startWfNodeDefDto.getId(), EventTypeSignEnum.GATEWAY_DEF_EVENT.code,processDefinition,bpmnexclusiveGateway.getId());
                    if(CollectionUtil.isNotEmpty(wfGatewayDefDto)) {
                        startWfNodeDefDto.setWfEventDefDtoList(wfGatewayDefDto);
                    }
                }
                wfGatewayDtoList.add(startWfNodeDefDto);
            }
        }
        return  wfGatewayDtoList;
    }


    /**
     * 解析equenceFlow 对应的dto 信息
     * @param bpmnsequenceFlowList  xml bpmn 对象
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @param formId 表单id
     * @return
     */
    public static List<WfSequDefDto> resolveSequenceFlowDef(List<BpmnsequenceFlow> bpmnsequenceFlowList, Long baseProcdefId, ProcessDefinition processDefinition,Long formId,String camundaSubProcessNodeDefId) {
        List<WfSequDefDto> wfSequDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnsequenceFlowList)) {
            for (BpmnsequenceFlow bpmnsequenceFlow : bpmnsequenceFlowList) {
                //seq def 基础信息
                WfSequDefDto wfSequDefDto = new WfSequDefDto();
                wfSequDefDto.setCamundaProcdefId(processDefinition.getId());
                wfSequDefDto.setCamundaProcdefKey(processDefinition.getKey());
                wfSequDefDto.setCamundaSequId(bpmnsequenceFlow.getId());
                wfSequDefDto.setSequName(bpmnsequenceFlow.getName());
                wfSequDefDto.setExpression(bpmnsequenceFlow.getNetwisdexpression());
                wfSequDefDto.setExpressionName(bpmnsequenceFlow.getNetwisdexpressionName());
                wfSequDefDto.setProcdefId(baseProcdefId);
                //设置子流程的 父级nodeId
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    wfSequDefDto.setCamundaParentNodeDefId(camundaSubProcessNodeDefId);
                }
                if(StringUtils.isNotBlank(bpmnsequenceFlow.getNetwisddbId())) {
                    wfSequDefDto.setId(Long.valueOf(bpmnsequenceFlow.getNetwisddbId()));//设置序列流 dbId
                    log.debug("BPMN解析-xml编辑：用户节点-变量 dbId不存在,使用DTO生成." + bpmnsequenceFlow.toString());
                } else {
                    bpmnsequenceFlow.setNetwisddbId(String.valueOf(wfSequDefDto.getId()));//设置序列流 dbId
                }

                //seq expre 表达式信息
                BpmnextensionElements bpmnextensionElements = bpmnsequenceFlow.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    //处理特殊字符
                    String netwisdsequExpText = bpmnextensionElements.getNetwisdsequExpText();
                    if(StringUtils.isNotBlank(netwisdsequExpText)) {
                        netwisdsequExpText = tagContentCheck(netwisdsequExpText);
                        bpmnextensionElements.setNetwisdsequExpText(netwisdsequExpText);
                    }
                    List<NetwisdsequExps> netwisdsequExps = bpmnextensionElements.getNetwisdsequExps();
                    if(CollectionUtil.isNotEmpty(netwisdsequExps)) {
                        for (NetwisdsequExps exps : netwisdsequExps) {
                            List<NetwisdsequExp> netwisdsequExp = exps.getNetwisdsequExp();
                            if(CollectionUtil.isNotEmpty(netwisdsequExp)) {
                                List<WfExpreSequDefDto> wfExpreSequDefDtoList = new ArrayList<>();
                                for (NetwisdsequExp exp : netwisdsequExp) {//表达式
                                    WfExpreSequDefDto wfExpreSequDefDto = new WfExpreSequDefDto();
                                    wfExpreSequDefDto.setExpreId(Long.valueOf(exp.getExpreId()));
                                    wfExpreSequDefDto.setExpression(exp.getExpression()); //TODO 后台解析表达式
                                    wfExpreSequDefDto.setProcdefId(baseProcdefId);
                                    wfExpreSequDefDto.setSequDefId(wfSequDefDto.getId());
                                    wfExpreSequDefDto.setCamundaSequId(bpmnsequenceFlow.getId());
                                    wfExpreSequDefDto.setCamundaProcdefId(processDefinition.getId());
                                    wfExpreSequDefDto.setCamundaProcdefKey(processDefinition.getKey());

                                    if(StringUtils.isNotBlank(exp.getDbId())) {
                                        wfExpreSequDefDto.setId(Long.valueOf(exp.getDbId()));//设置序列流表达式 dbId
                                        log.debug("BPMN解析-xml编辑：序列流-表达式 dbId不存在,使用DTO生成." + exp.toString());
                                    } else {
                                        exp.setDbId(String.valueOf(wfExpreSequDefDto.getId()));//设置序列流表达式 dbId
                                    }

                                    //表达式参数
                                    List<NetwisdsequExpParam> netwisdsequExpParam = exp.getNetwisdsequExpParam();
                                    if(CollectionUtil.isNotEmpty(netwisdsequExpParam)) {
                                        List<WfExpreSequParamDefDto> wfExpreSequParamDefDtoList = new ArrayList<>();
                                        for (NetwisdsequExpParam expParm : netwisdsequExpParam) {
                                            WfExpreSequParamDefDto wfExpreSequParamDefDto = new WfExpreSequParamDefDto();
                                            wfExpreSequParamDefDto.setExpreParamId(Long.valueOf(expParm.getExpreParamId()));
                                            wfExpreSequParamDefDto.setExpreParamSource(Integer.valueOf(expParm.getExpreParamSource()));
                                            wfExpreSequParamDefDto.setExpreParamValue(expParm.getExpreParamValue());
                                            wfExpreSequParamDefDto.setExpreSequDefId(wfSequDefDto.getId());
                                            wfExpreSequParamDefDto.setCamundaSequId(bpmnsequenceFlow.getId());
                                            wfExpreSequParamDefDto.setCamundaProcdefId(processDefinition.getId());
                                            wfExpreSequParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                            wfExpreSequParamDefDtoList.add(wfExpreSequParamDefDto);

                                            if(StringUtils.isNotBlank(expParm.getDbId())) {
                                                wfExpreSequParamDefDto.setId(Long.valueOf(expParm.getDbId()));//设置序列流表达式参数 dbId
                                                log.debug("BPMN解析-xml编辑：序列流-表达式-参数 dbId不存在,使用DTO生成." + expParm.toString());
                                            } else {
                                                expParm.setDbId(String.valueOf(wfExpreSequParamDefDto.getId()));//设置序列流表达式参数 dbId
                                            }

                                        }
                                        wfExpreSequDefDto.setWfExpreSequParamDefDtoList(wfExpreSequParamDefDtoList);
                                    }
                                    wfExpreSequDefDtoList.add(wfExpreSequDefDto);
                                }
                                wfSequDefDto.setWfExpreSequDefDtoList(wfExpreSequDefDtoList);
                            }
                        }
                    }
                    //解析变量
                    NetwisdVars netwisdVars = bpmnsequenceFlow.getBpmnextensionElements().getNetwisdvars();
                    if(null != netwisdVars) {
                        List<NetwisdVar> netwisdVar = netwisdVars.getNetwisdvar();
                        if(CollectionUtil.isNotEmpty(netwisdVar)) {
                            List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
                            for (NetwisdVar var : netwisdVar) {
                                WfVarDefDto wfVarDefDto = new WfVarDefDto();
                                wfVarDefDto.setSequDefId(wfSequDefDto.getId());
                                wfVarDefDto.setProcdefId(baseProcdefId);
                                //wfVarDefDto.setVarId(Long.valueOf(var.getVarId()));
                                wfVarDefDto.setFormId(formId);
                                wfVarDefDto.setActionScope(Integer.valueOf(var.getActionScope()));
                                wfVarDefDto.setCamundaNodeDefId(null);
                                wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                wfVarDefDto.setCamundaSequDefId(bpmnsequenceFlow.getId());
                                //wfVarDefDto.setFormVarId(var.getFormVarId());
                                if(StringUtils.isNotBlank(var.getIsMoreRow())) {
                                    wfVarDefDto.setIsMoreRow(Integer.valueOf(var.getIsMoreRow()));
                                }
                                wfVarDefDtoList.add(wfVarDefDto);

                                if(StringUtils.isNotBlank(var.getDbId())) {
                                    wfVarDefDto.setId(Long.valueOf(var.getDbId()));//设置变量 dbId
                                    log.debug("BPMN解析-xml编辑：序列流-变量 dbId不存在,使用DTO生成." + var.toString());
                                } else {
                                    var.setDbId(String.valueOf(wfVarDefDto.getId()));//设置变量 dbId
                                }

                            }
                            wfSequDefDto.setWfVarDefDtoList(wfVarDefDtoList);
                        }
                    }

                    //序列流 事件
                    List<CamundaexecutionListener> camundaexecutionListener =  bpmnsequenceFlow.getBpmnextensionElements().getCamundaexecutionListener();
                    if(CollectionUtil.isNotEmpty(camundaexecutionListener)) {
                        List<WfSequEventDefDto> wfSequEventDefDtoList = new ArrayList<>();
                        for (CamundaexecutionListener listener : camundaexecutionListener) {
                            WfSequEventDefDto wfSequEventDefDto = new WfSequEventDefDto();
                            wfSequEventDefDto.setEventBindType(listener.getEvent());
                            wfSequEventDefDto.setEventId(Long.valueOf(listener.getNetwisdeventId()));
                            wfSequEventDefDto.setProcdefId(baseProcdefId);
                            wfSequEventDefDto.setSequDefId(wfSequDefDto.getId());
                            wfSequEventDefDto.setCamundaProcdefId(processDefinition.getId());
                            wfSequEventDefDto.setCamundaProcdefKey(processDefinition.getKey());
                            wfSequEventDefDto.setCamundaSequDefId(bpmnsequenceFlow.getId());

                            if(StringUtils.isNotBlank(listener.getNetwisddbId())) {
                                wfSequEventDefDto.setId(Long.valueOf(listener.getNetwisddbId()));//设置序列流事件 dbId
                                log.debug("BPMN解析-xml编辑：序列流-事件 dbId不存在,使用DTO生成." + listener.toString());
                            } else {
                                listener.setNetwisddbId(String.valueOf(wfSequEventDefDto.getId()));//设置序列流事件 dbId
                            }

                            //解析事件参数
                            List<Camundafield> camundafieldList = listener.getCamundafield();
                            if(CollectionUtil.isNotEmpty(camundafieldList)) {
                                List<WfSequEventParamDefDto> wfSequEventParamDefDtoList = new ArrayList<>();
                                for (Camundafield camundafield : camundafieldList) {
                                    WfSequEventParamDefDto wfSequEventParamDefDto = new WfSequEventParamDefDto();
                                    wfSequEventParamDefDto.setEventDefId(wfSequEventDefDto.getId());
                                    String str = camundafield.getCamundastring();
                                    if(StringUtils.isNotBlank(str)) {
                                        wfSequEventParamDefDto.setParamType("String");
                                        wfSequEventParamDefDto.setParamDefalutValue(str);
                                    } else {
                                        String expression = camundafield.getCamundaexpression();
                                        wfSequEventParamDefDto.setParamType("expression");
                                        wfSequEventParamDefDto.setParamDefalutValue(expression);
                                    }
                                    wfSequEventParamDefDto.setParamId(camundafield.getName());
                                    wfSequEventParamDefDto.setParamName(camundafield.getName());

                                    wfSequEventParamDefDto.setCamundaProcdefId(processDefinition.getId());
                                    wfSequEventParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                    wfSequEventParamDefDto.setCamundaSequDefId(bpmnsequenceFlow.getId());

                                    wfSequEventParamDefDtoList.add(wfSequEventParamDefDto);

                                    if(StringUtils.isNotBlank(camundafield.getNetwisddbId())) {
                                        wfSequEventParamDefDto.setId(Long.valueOf(camundafield.getNetwisddbId()));//设置序列流事件参数 dbId
                                        log.debug("BPMN解析-xml编辑：序列流-事件 dbId不存在,使用DTO生成." + camundafield.toString());
                                    } else {
                                        camundafield.setNetwisddbId(String.valueOf(wfSequEventParamDefDto.getId()));//设置序列流事件参数 dbId
                                    }

                                }
                                wfSequEventDefDto.setWfSequEventParamDefDtoList(wfSequEventParamDefDtoList);
                            }
                            wfSequEventDefDtoList.add(wfSequEventDefDto);
                        }
                        wfSequDefDto.setWfSequEventDefDtoList(wfSequEventDefDtoList);
                    }
                }
                BpmnconditionExpression bpmnconditionExpression = bpmnsequenceFlow.getBpmnconditionExpression();
                if(null != bpmnconditionExpression) {
                    String content = bpmnconditionExpression.getContent();
                    if(StringUtils.isNotBlank(content)) {
                        content = tagContentCheck(content);
                        bpmnconditionExpression.setContent(content);
                    }
                }
                wfSequDefDtoList.add(wfSequDefDto);
            }
        }
        return  wfSequDefDtoList;
    }

    /**
     * 解析子流程 对应的dto 信息
     * @param bpmnsubProcessList  xml bpmn 对象
     * @param nodeType 节点类型
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @Param subParentId 父级 子流程信息
     * @return
     */
    public static List<WfNodeDefDto> resolveSubProcess(List<BpmnsubProcess> bpmnsubProcessList, Integer nodeType,Long baseProcdefId,ProcessDefinition processDefinition, String subParentId,Long formId) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmnsubProcessList)) {
            log.debug("BPMN解析-子流程 BMPN参数：" + bpmnsubProcessList.toString());
            for (BpmnsubProcess bpmnsubProcess : bpmnsubProcessList) {
                WfNodeDefDto subProcessWfNodeDefDto = new WfNodeDefDto();
                subProcessWfNodeDefDto.setCamundaNodeDefId(bpmnsubProcess.getId());
                if(StringUtils.isBlank(bpmnsubProcess.getName())) {
                    throw new IncloudException("子流程名称不能为空！");
                }
                if(StringUtils.isBlank(bpmnsubProcess.getNetwisdformId())) {
                    throw new IncloudException("子流程关联的表单不能为空！");
                }
                subProcessWfNodeDefDto.setNodeName(bpmnsubProcess.getName());
                subProcessWfNodeDefDto.setNodeType(nodeType);
                subProcessWfNodeDefDto.setDueDate(null);
                subProcessWfNodeDefDto.setFollowUpDate(null);
                subProcessWfNodeDefDto.setPriority(null);
                subProcessWfNodeDefDto.setProcdefId(baseProcdefId);
                subProcessWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                subProcessWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                subProcessWfNodeDefDto.setCamundaParentNodeDefId(subParentId); //子流程节点的父级子流程信息

                if(StringUtils.isNotBlank(bpmnsubProcess.getNetwisddbId())) {
                    subProcessWfNodeDefDto.setId(Long.valueOf(bpmnsubProcess.getNetwisddbId()));//设置开始节点 dbId
                } else {
                    log.debug("BPMN解析-xml编辑：子流程节点dbId不存在,使用DTO生成." + bpmnsubProcess.toString());
                    bpmnsubProcess.setNetwisddbId(String.valueOf(subProcessWfNodeDefDto.getId()));//设置开始节点 dbId
                }

                //解析 子流程 流程定义事件
                BpmnextensionElements bpmnextensionElements = bpmnsubProcess.getBpmnextensionElements();
                if(null != bpmnextensionElements) {
                    log.debug("BPMN解析-xml编辑：解析子流程定义事件." + bpmnextensionElements.toString());
                    List<WfEventDefDto> wfEventDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,subProcessWfNodeDefDto.getId(), EventTypeSignEnum.SUB_PROC_DEF_EVENT.code,processDefinition,bpmnsubProcess.getId());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        subProcessWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }

                    //解析按钮信息
                    NetwisdnodeButtons netwisdnodeButtons = bpmnsubProcess.getBpmnextensionElements().getNetwisdnodeButtons();
                    if(null != netwisdnodeButtons) {
                        log.debug("BPMN解析-xml编辑：解析子流程按钮信息." + netwisdnodeButtons.toString());
                        List<NetwisdnodeButton> netwisdnodeButton = netwisdnodeButtons.getNetwisdnodeButton();
                        if(CollectionUtil.isNotEmpty(netwisdnodeButton)) {
                            List<WfButtonDefDto> wfButtonDefDtoList = new ArrayList<>();
                            for (NetwisdnodeButton buttion : netwisdnodeButton) {
                                WfButtonDefDto wfButtonDefDto = new WfButtonDefDto();
                                wfButtonDefDto.setProcdefId(baseProcdefId);
                                wfButtonDefDto.setNodeDefId(subProcessWfNodeDefDto.getId());
                                //wfButtonDefDto.setButtonId(buttion.getId());
                                wfButtonDefDto.setButtonName(buttion.getName());
                                wfButtonDefDto.setButtonCode(buttion.getCode());
                                wfButtonDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfButtonDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                wfButtonDefDto.setCamundaNodeDefId(bpmnsubProcess.getId());

                                if(StringUtils.isNotBlank(buttion.getDbId())) {
                                    buttion.setDbId(buttion.getDbId());//设置按钮 dbId
                                    log.debug("BPMN解析-xml编辑：用户节点-按钮 dbId不存在,使用DTO生成." + buttion.toString());
                                } else {
                                    buttion.setDbId(String.valueOf(wfButtonDefDto.getId()));//设置按钮 dbId
                                }
                                wfButtonDefDtoList.add(wfButtonDefDto);
                            }
                            subProcessWfNodeDefDto.setWfButtonDefDtoListDto(wfButtonDefDtoList);
                        }
                    }

                    //解析人员信息
                    List<WfExpreUserDefDto> WfExpreUserDefDtoList = resolveHumanExps(bpmnextensionElements.getNetwisdhumanExps(),subProcessWfNodeDefDto,baseProcdefId,processDefinition);
                    if(CollectionUtil.isNotEmpty(WfExpreUserDefDtoList)) {
                        subProcessWfNodeDefDto.setWfExpreUserDefDtoList(WfExpreUserDefDtoList);
                    }

                    // 解析变量信息
                    NetwisdVars netwisdVars = bpmnextensionElements.getNetwisdvars();
                    if(null != netwisdVars) {
                        List<NetwisdVar> netwisdVar = netwisdVars.getNetwisdvar();
                        if(CollectionUtil.isNotEmpty(netwisdVar)) {
                            List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
                            for (NetwisdVar var : netwisdVar) {
                                WfVarDefDto wfVarDefDto = new WfVarDefDto();
                                wfVarDefDto.setSequDefId(null);
                                wfVarDefDto.setProcdefId(baseProcdefId);
                                wfVarDefDto.setNodeDefId(subProcessWfNodeDefDto.getId());
                                //wfVarDefDto.setVarId(Long.valueOf(var.getVarId()));
                                wfVarDefDto.setFormId(formId);
                                wfVarDefDto.setActionScope(Integer.valueOf(var.getActionScope()));
                                wfVarDefDto.setCamundaSequDefId(null);
                                wfVarDefDto.setCamundaNodeDefId(bpmnsubProcess.getId());
                                wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                //wfVarDefDto.setFormVarId(var.getFormVarId());
                                if(StringUtils.isNotBlank(var.getIsMoreRow())) {
                                    wfVarDefDto.setIsMoreRow(Integer.valueOf(var.getIsMoreRow()));
                                }
                                wfVarDefDtoList.add(wfVarDefDto);

                                if(StringUtils.isNotBlank(var.getDbId())) {
                                    var.setDbId(var.getDbId());//设置变量 dbId
                                    log.debug("BPMN解析-xml编辑：用户节点-变量 dbId不存在,使用DTO生成." + var.toString());
                                } else {
                                    var.setDbId(String.valueOf(wfVarDefDto.getId()));//设置变量 dbId
                                }
                            }
                            subProcessWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtoList);
                        }
                    }
                }

                BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics = bpmnsubProcess.getBpmnmultiInstanceLoopCharacteristics();
                if(null != bpmnmultiInstanceLoopCharacteristics) {
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    subProcessWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCESUBPROCESS.code);
                    //subProcessWfNodeDefDto.setPassingRate(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? BigDecimal.valueOf(Double.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())):null);
                    //subProcessWfNodeDefDto.setPassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingHandle()):null);
                    //subProcessWfNodeDefDto.setUnpassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle()):null);
                }

                //解析 子流程 表单数据
                WfFormDefDto wfFormDefDto = new WfFormDefDto();
                wfFormDefDto.setCamundaProcdefId(processDefinition.getId());
                wfFormDefDto.setCamundaProcdefKey(processDefinition.getKey());
                Long subFormId = Long.valueOf(bpmnsubProcess.getNetwisdformId());
                wfFormDefDto.setFormId(subFormId);
                wfFormDefDto.setFormName(bpmnsubProcess.getNetwisdformName());
                wfFormDefDto.setProcdefId(baseProcdefId);
                wfFormDefDto.setCamundaNodeDefId(bpmnsubProcess.getId());
                wfFormDefDto.setNodeDefId(Long.valueOf(bpmnsubProcess.getNetwisddbId()));
                log.debug("BPMN解析-xml编辑：解析子流程表单信息." + wfFormDefDto.toString());
                //subProcessWfNodeDefDto.setWfFormDefDto(wfFormDefDto);

                //解析 子流程 开始节点
                List<BpmnstartEvent> bpmnstartEventList = bpmnsubProcess.getBpmnstartEvent();
                log.debug("BPMN解析-xml编辑：解析子流程 开始节点信息." + bpmnstartEventList.toString());
                List<WfNodeDefDto> startNodeDefDtoList = resolveStartNodeDef(bpmnstartEventList,NodeTypeEnum.STARTEVENT.code,baseProcdefId,processDefinition,bpmnsubProcess.getId());
                subProcessWfNodeDefDto.setStartNodeDefDtoList(startNodeDefDtoList);

                //解析 子流程 结束节点
                List<BpmnendEvent> bpmnendEventList = bpmnsubProcess.getBpmnendEvent();
                log.debug("BPMN解析-xml编辑：解析子流程 结束节点信息." + bpmnstartEventList.toString());
                List<WfNodeDefDto> endNodeDefDtoList = resolveEndNodeDef(bpmnendEventList,NodeTypeEnum.ENDEVENT.code,baseProcdefId,processDefinition,bpmnsubProcess.getId());
                subProcessWfNodeDefDto.setEndNodeDefDtoList(endNodeDefDtoList);

                //解析 子流程 用户节点
                List<BpmnuserTask> bpmnuserTaskList = bpmnsubProcess.getBpmnuserTask();
                log.debug("BPMN解析-xml编辑：解析子流程 用户节点信息." + bpmnstartEventList.toString());
                List<WfNodeDefDto> userTaskNodeDefDtoList = resolveUserTaskNodeDef(bpmnuserTaskList,NodeTypeEnum.USERTASK.code,baseProcdefId,processDefinition,subFormId,bpmnsubProcess.getId());
                subProcessWfNodeDefDto.setUserTaskNodeDefDtoList(userTaskNodeDefDtoList);

                //解析 子流程 网关
                List<BpmnexclusiveGateway> bpmnexclusiveGatewayList = bpmnsubProcess.getBpmnexclusiveGateway();
                log.debug("BPMN解析-xml编辑：解析子流程 网关信息." + bpmnstartEventList.toString());
                List<WfNodeDefDto> gatewayNodeDefDtoList = resolveGatewayDef(bpmnexclusiveGatewayList,baseProcdefId,processDefinition,bpmnsubProcess.getId());
                subProcessWfNodeDefDto.setGatewayNodeDefDtoList(gatewayNodeDefDtoList);

                //解析 子流程 序列流
                List<BpmnsequenceFlow> bpmnsequenceFlowList = bpmnsubProcess.getBpmnsequenceFlow();
                log.debug("BPMN解析-xml编辑：解析子流程 序列流信息." + bpmnstartEventList.toString());
                List<WfSequDefDto> sequenceFlowDtoList = resolveSequenceFlowDef(bpmnsequenceFlowList,baseProcdefId,processDefinition,subFormId,bpmnsubProcess.getId());
                subProcessWfNodeDefDto.setSequenceFlowDtoList(sequenceFlowDtoList);

                //解析 子流程 callActivity
                List<BpmncallActivity> bpmncallActivityList = bpmnsubProcess.getBpmncallActivity();
                List<WfNodeDefDto> callActivityNodeDefDtoList = XmlUtils.resolveCallActivity(bpmncallActivityList,NodeTypeEnum.CALLACTIVITY.code,baseProcdefId,processDefinition,bpmnsubProcess.getId(),formId);
                subProcessWfNodeDefDto.setCallActivityNodeDefDtoList(callActivityNodeDefDtoList);
                //解析 子流程 内嵌子流程
                List<BpmnsubProcess> _bpmnsubProcessList = bpmnsubProcess.getBpmnsubProcess();
                if(CollectionUtil.isNotEmpty(_bpmnsubProcessList)) {
                    log.debug("BPMN解析-子流程内嵌子流程，参数：" + bpmnsubProcessList.toString());
                    List<WfNodeDefDto> _wfNodeDefDtoList = resolveSubProcess(_bpmnsubProcessList,nodeType,baseProcdefId,processDefinition,bpmnsubProcess.getId(),formId);
                    if(CollectionUtil.isNotEmpty(_wfNodeDefDtoList)) {
                        wfNodeDefDtoList.addAll(_wfNodeDefDtoList);
                    }
                }
                wfNodeDefDtoList.add(subProcessWfNodeDefDto);
                log.debug("BPMN解析-子流程解析完成。");
            }
        }
        return  wfNodeDefDtoList;
    }

    /**
     * 解析子流程(call activity) 对应的dto 信息
     * @param bpmncallActivityList  xml bpmn 对象
     * @param nodeType 节点类型
     * @param baseProcdefId wf系统流程定义id
     * @param processDefinition camunda 流程定义对象
     * @Param subParentId 父级 子流程信息
     * @return
     */
    public static List<WfNodeDefDto> resolveCallActivity(List<BpmncallActivity> bpmncallActivityList, Integer nodeType,Long baseProcdefId,ProcessDefinition processDefinition, String subParentId,Long formId) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(bpmncallActivityList)) {
            log.debug("BPMN解析-子流程 BMPN参数：" + bpmncallActivityList.toString());
            for (BpmncallActivity bpmncallActivity : bpmncallActivityList) {
                WfNodeDefDto callActivityWfNodeDefDto = new WfNodeDefDto();//call activity 节点信息
                callActivityWfNodeDefDto.setCamundaNodeDefId(bpmncallActivity.getId());
                if(StringUtils.isBlank(bpmncallActivity.getName())) {
                    throw new IncloudException("子流程名称不能为空！");
                }
                callActivityWfNodeDefDto.setNodeName(bpmncallActivity.getName());
                callActivityWfNodeDefDto.setNodeType(nodeType);
                callActivityWfNodeDefDto.setDueDate(null);
                callActivityWfNodeDefDto.setFollowUpDate(null);
                callActivityWfNodeDefDto.setPriority(null);
                callActivityWfNodeDefDto.setProcdefId(baseProcdefId);
                callActivityWfNodeDefDto.setCamundaProcdefId(processDefinition.getId());
                callActivityWfNodeDefDto.setCamundaProcdefKey(processDefinition.getKey());
                callActivityWfNodeDefDto.setCamundaParentNodeDefId(subParentId); //子流程节点的父级子流程信息
                //callActivityWfNodeDefDto.setIsLookOver(Integer.valueOf(bpmncallActivity.getNetwisdisLookOver()));

                //主流程和子流程 关系
                WfProcDefRelDto wfProcDefRelDto = new WfProcDefRelDto();
                wfProcDefRelDto.setMainCamundaProcdefId(processDefinition.getId());
                wfProcDefRelDto.setMainProcdefId(baseProcdefId);
                wfProcDefRelDto.setMainProcdefName(processDefinition.getName());
                wfProcDefRelDto.setMainCamundaNodeDefId(bpmncallActivity.getId());
                wfProcDefRelDto.setMainNodeName(bpmncallActivity.getName());
                wfProcDefRelDto.setMainNodeDefId(callActivityWfNodeDefDto.getId());//nodeDefId 数据库id
                if(StringUtils.isBlank(bpmncallActivity.getCalledElement())) {
                    throw new IncloudException("callActivity节点"+ bpmncallActivity.getName()+".选择的子流程内容不能为空！");
                }
                wfProcDefRelDto.setChildCamundaProcdefKey(bpmncallActivity.getCamundacalledElementVersion());
                if(StringUtils.isBlank(bpmncallActivity.getCamundacalledElementVersion())) {
                    throw new IncloudException("callActivity节点"+ bpmncallActivity.getName()+".选择的子流程版本不能为空！");
                }
                wfProcDefRelDto.setChildProcdefVersion(Integer.valueOf(bpmncallActivity.getCamundacalledElementVersion()));
                wfProcDefRelDto.setChildProcdefId(Long.valueOf(bpmncallActivity.getNetwisdctmChildLogProcdefId()));
                wfProcDefRelDto.setChildProcdefName(bpmncallActivity.getNetwisdchildProcdefName());
                wfProcDefRelDto.setChildCamundaProcdefId(bpmncallActivity.getNetwisdcmdChildLogProcdefId());
                callActivityWfNodeDefDto.setWfProcDefRel(wfProcDefRelDto);

                if(StringUtils.isNotBlank(bpmncallActivity.getNetwisddbId())) {
                    callActivityWfNodeDefDto.setId(Long.valueOf(bpmncallActivity.getNetwisddbId()));//设置开始节点 dbId
                } else {
                    log.debug("BPMN解析-xml编辑：子流程节点dbId不存在,使用DTO生成." + bpmncallActivity.toString());
                    bpmncallActivity.setNetwisddbId(String.valueOf(callActivityWfNodeDefDto.getId()));//设置开始节点 dbId
                }

                //解析 子流程 流程定义事件
                BpmnextensionElements bpmnextensionElements = bpmncallActivity.getBpmnextensionElements();
                log.debug("BPMN解析-xml编辑：解析子流程定义事件." + bpmnextensionElements.toString());
                if(null != bpmnextensionElements) {
                    List<WfEventDefDto> wfEventDefDto = XmlUtils.resolveEventDef(bpmnextensionElements,baseProcdefId,callActivityWfNodeDefDto.getId(), EventTypeSignEnum.SUB_PROC_DEF_EVENT.code,processDefinition,bpmncallActivity.getId());
                    if(CollectionUtil.isNotEmpty(wfEventDefDto)) {
                        callActivityWfNodeDefDto.setWfEventDefDtoList(wfEventDefDto);
                    }
                    //解析人员信息
                    List<WfExpreUserDefDto> WfExpreUserDefDtoList = resolveHumanExps(bpmnextensionElements.getNetwisdhumanExps(),callActivityWfNodeDefDto,baseProcdefId,processDefinition);
                    if(CollectionUtil.isNotEmpty(WfExpreUserDefDtoList)) {
                        callActivityWfNodeDefDto.setWfExpreUserDefDtoList(WfExpreUserDefDtoList);
                    }

                    // 解析变量信息
                    NetwisdVars netwisdVars = bpmnextensionElements.getNetwisdvars();
                    if(null != netwisdVars) {
                        List<WfVarDefDto> wfVarDefDtoList  = resolveVars(netwisdVars,callActivityWfNodeDefDto,baseProcdefId,processDefinition,formId);
                        if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                            callActivityWfNodeDefDto.setWfVarDefDtoList(wfVarDefDtoList);
                        }
                    }
                }

                BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics = bpmncallActivity.getBpmnmultiInstanceLoopCharacteristics();
                if(null != bpmnmultiInstanceLoopCharacteristics) {
                    // 判断是否是多实例节点 如果是多实例节点则要设置对应的节点类型为多实例
                    callActivityWfNodeDefDto.setNodeType(NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code);
                    //subProcessWfNodeDefDto.setPassingRate(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? BigDecimal.valueOf(Double.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())):null);
                    //subProcessWfNodeDefDto.setPassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingRate())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdpassingHandle()):null);
                    //subProcessWfNodeDefDto.setUnpassingHandle(StringUtils.isNotBlank(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle())? Integer.valueOf(bpmnmultiInstanceLoopCharacteristics.getNetwisdunpassingHandle()):null);
                }
                wfNodeDefDtoList.add(callActivityWfNodeDefDto);
                log.debug("BPMN解析-子流程(callActivity)解析完成。");
            }
        }
        return  wfNodeDefDtoList;
    }

    /**
     * 解析人员表达式信息
     * @param netwisdhumanExps
     * @param startWfNodeDefDto
     * @param baseProcdefId
     * @param processDefinition
     * @return
     */
    public static List<WfExpreUserDefDto> resolveHumanExps(NetwisdhumanExps netwisdhumanExps,WfNodeDefDto startWfNodeDefDto,Long baseProcdefId,ProcessDefinition processDefinition) {
        //解析人员信息
        List<WfExpreUserDefDto> wfExpreUserDefDtoList = new ArrayList<>();
        //NetwisdhumanExps netwisdhumanExps = bpmnuserTask.getBpmnextensionElements().getNetwisdhumanExps();
        if(null != netwisdhumanExps) {
            List<NetwisdhumanExp> netwisdhumanExp = netwisdhumanExps.getNetwisdhumanExp();
            if(CollectionUtil.isNotEmpty(netwisdhumanExp)) {
                for (NetwisdhumanExp exp : netwisdhumanExp) {
                    WfExpreUserDefDto wfExpreUserDefDto = new WfExpreUserDefDto();
                    //如果是表达式 则正常处理
                    if(String.valueOf(ExpressionBizTypeEnum.MDM_EXPRESSION.getType()).equals(exp.getBizType())) {
                        //wfExpreUserDefDto.setExpreId(StringUtils.isNotBlank(exp.getExpreId())?Long.valueOf(exp.getExpreId()):null);
                        wfExpreUserDefDto.setNodeDefId(startWfNodeDefDto.getId());
                        wfExpreUserDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
                        wfExpreUserDefDto.setProcdefId(baseProcdefId);
                        wfExpreUserDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfExpreUserDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfExpreUserDefDto.setCamundaNodeDefId(startWfNodeDefDto.getCamundaNodeDefId());
                        wfExpreUserDefDto.setExpressionName(exp.getExpreName());
                        //wfExpreUserDefDto.setBizType(Integer.valueOf(exp.getBizType()));

                        if(StringUtils.isNotBlank(exp.getDbId())) {
                            exp.setDbId(exp.getDbId());//设置表达式 dbId
                            log.debug("BPMN解析-xml编辑：用户节点-人员表达式 dbId不存在,使用DTO生成." + exp.toString());
                        } else {
                            exp.setDbId(String.valueOf(wfExpreUserDefDto.getId()));//设置表达式 dbId
                        }

                        //处理参数
                        List<NetwisdhumanExpParam> netwisdhumanExpParm = exp.getNetwisdhumanExpParam();
                        List<WfExpreUserParamDefDto> wfExpreUserParamDefDtoList = new ArrayList<>();
                        if(CollectionUtil.isNotEmpty(netwisdhumanExpParm)) {
                            for (NetwisdhumanExpParam expParm : netwisdhumanExpParm) {
                                WfExpreUserParamDefDto wfExpreUserParamDefDto = new WfExpreUserParamDefDto();
                                //wfExpreUserParamDefDto.setExpreParamId(Long.valueOf(expParm.getExpreParamId()));
                                wfExpreUserParamDefDto.setExpreParamValue(expParm.getExpreParamValue());
                                //wfExpreUserParamDefDto.setExpreParamSource(Integer.valueOf(expParm.getExpreParamSource()));
                                wfExpreUserParamDefDto.setNodeDefId(startWfNodeDefDto.getId());
                                wfExpreUserParamDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
                                wfExpreUserParamDefDto.setExpreUserDefId(wfExpreUserDefDto.getId());
                                wfExpreUserParamDefDto.setExpreParamVarType(expParm.getExpreParamVarType());
                                if(StringUtils.isBlank(expParm.getExpreParamVarType())) {
                                    throw new IncloudException("人员表达式的参数类型不能为空！");
                                }
                                if(StringUtils.isNotBlank(expParm.getDbId())) {
                                    expParm.setDbId(expParm.getDbId());//设置表达式参数 dbId
                                    log.debug("BPMN解析-xml编辑：用户节点-人员表达式-参数 dbId不存在,使用DTO生成." + expParm.toString());
                                } else {
                                    expParm.setDbId(String.valueOf(wfExpreUserParamDefDto.getId()));//设置表达式参数 dbId
                                }

                                wfExpreUserParamDefDto.setCamundaProcdefId(processDefinition.getId());
                                wfExpreUserParamDefDto.setCamundaProcdefKey(processDefinition.getKey());
                                wfExpreUserParamDefDto.setCamundaNodeDefId(startWfNodeDefDto.getCamundaNodeDefId());
                                wfExpreUserParamDefDtoList.add(wfExpreUserParamDefDto);
                            }
                        }
                        if(CollectionUtil.isNotEmpty(wfExpreUserParamDefDtoList)) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("${");
                            String _expre = exp.getExpreExpression();
                            String expre = specialCharsCheck(_expre);
                            expre = expre.substring(0,expre.length() - 1);
                            sb.append(expre);
                            //sb.append(",");
                            for (WfExpreUserParamDefDto wfExpreUserParamDefDto : wfExpreUserParamDefDtoList) {
                                Integer TypeVal = Integer.valueOf(wfExpreUserParamDefDto.getExpreParamVarType());
//                                if(ExpreParamVarTypeEnum.USER_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.ROLE_EXPRE.code == TypeVal
//                                        || ExpreParamVarTypeEnum.POST_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.JOB_EXPRE.code == TypeVal
//                                        || ExpreParamVarTypeEnum.DEPT_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.MECHANISM_EXPRE.code == TypeVal
//                                        || ExpreParamVarTypeEnum.BUILTINROLE_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.STRING_EXPRE.code == TypeVal
//                                        || ExpreParamVarTypeEnum.DATE_EXPRE.code == TypeVal) {
//                                    if(ExpreParamSourceEnum.FORM_FIELD.getType() == wfExpreUserParamDefDto.getExpreParamSource()) {
//                                        sb.append(wfExpreUserParamDefDto.getExpreParamValue());
//                                        sb.append(",");
//                                    }
//                                    if(ExpreParamSourceEnum.HAND_FIELD.getType() == wfExpreUserParamDefDto.getExpreParamSource()) {
//                                        sb.append("'");
//                                        sb.append(wfExpreUserParamDefDto.getExpreParamValue());
//                                        sb.append("'");
//                                        sb.append(",");
//                                    }
//                                }
//                                if(ExpreParamVarTypeEnum.BOOLEAN_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.INTEGER_EXPRE.code == TypeVal
//                                        ||ExpreParamVarTypeEnum.LONG_EXPRE.code == TypeVal || ExpreParamVarTypeEnum.DOUBLE_EXPRE.code == TypeVal) {
//                                    sb.append(wfExpreUserParamDefDto.getExpreParamValue()+",");
//                                }
                                //TODO List 暂时没有场景 暂时不处理
                            }
                            sb.setLength(sb.length()-1);
                            sb.append(")}");
                            wfExpreUserDefDto.setExpression(sb.toString());
                            log.debug("解析用户节点-人员【表达式】为：{}", sb.toString());
                            wfExpreUserDefDto.setWfExpreUserParamDefDtoList(wfExpreUserParamDefDtoList);
                        }
                        wfExpreUserDefDtoList.add(wfExpreUserDefDto);
                    } else {
                        // 如果不是表达式 则特殊处理(岗位、职位、人员、机构、部门、人员、人员角色、资源组)
                        //wfExpreUserDefDto.setExpreId(0L);
                        wfExpreUserDefDto.setNodeDefId(startWfNodeDefDto.getId());
                        wfExpreUserDefDto.setNodeType(NodeTypeEnum.USERTASK.code);
                        wfExpreUserDefDto.setProcdefId(baseProcdefId);
                        wfExpreUserDefDto.setCamundaProcdefId(processDefinition.getId());
                        wfExpreUserDefDto.setCamundaProcdefKey(processDefinition.getKey());
                        wfExpreUserDefDto.setCamundaNodeDefId(startWfNodeDefDto.getCamundaNodeDefId());
                        wfExpreUserDefDto.setExpressionName(exp.getExpreName());
                        if(StringUtils.isNotBlank(exp.getBizId())) {
                            wfExpreUserDefDto.setBizId(exp.getBizId());
                            //wfExpreUserDefDto.setBizType(Integer.valueOf(exp.getBizType()));
                        }

                        if(StringUtils.isNotBlank(exp.getDbId())) {
                            exp.setDbId(exp.getDbId());//设置表达式 dbId
                            log.debug("BPMN解析-xml编辑：用户节点-人员表达式 dbId不存在,使用DTO生成." + exp.toString());
                        } else {
                            exp.setDbId(String.valueOf(wfExpreUserDefDto.getId()));//设置表达式 dbId
                        }

                        String bizId = exp.getBizId();
                        StringBuilder sb = new StringBuilder();
                        sb.append("${wfUserExpression.");//invokeMethod('
                        if(String.valueOf(ExpressionBizTypeEnum.EMPL.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_USER_IDS);
                            sb.append("('");
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(bizId);
                            }
                            sb.append("')}");
                        }
                        if(String.valueOf(ExpressionBizTypeEnum.ROLE.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_ROLE_IDS);
                            sb.append("('");
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(bizId);
                            }
                            sb.append("')}");
                        }
                        if(String.valueOf(ExpressionBizTypeEnum.DUTY.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_POST_IDS);
                            sb.append("(-1");//默认查询所有(包含主岗，次岗)
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(",'");
                                sb.append(bizId);
                                sb.append("')}");
                            }
                        }
                        if(String.valueOf(ExpressionBizTypeEnum.POST.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_POST_IDS);
                            sb.append("(-1");//默认查询所有(包含主职，次职)
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(",'");
                                sb.append(bizId);
                                sb.append("')}");
                            }
                        }
                        if(String.valueOf(ExpressionBizTypeEnum.DEPT.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_DEPT_IDS);
                            sb.append("('");
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(bizId);
                            }
                            sb.append("')}");
                        }
                        if(String.valueOf(ExpressionBizTypeEnum.ORGAN.getType()).equals(exp.getBizType())){
                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_MECHANISM_IDS);
                            sb.append("('");
                            if(StringUtils.isNotBlank(bizId)) {
                                sb.append(bizId);
                            }
                            sb.append("')}");
                        }
//                        if(String.valueOf(ExpressionBizTypeEnum.RESOURCE_GROUP.getType()).equals(exp.getBizType())){
//                            sb.append(ExpressionVariable.EXPRESS_GET_USER_BY_POST_RULES_ID);
//                            sb.append("('");
//                            if(StringUtils.isNotBlank(bizId)) {
//                                sb.append(bizId);
//                            }
//                            sb.append("')}");
//                        }
                        wfExpreUserDefDto.setExpression(sb.toString());
                        log.debug("解析用户节点-人员表达式【基础业务选择】为：{}", sb.toString());
                        wfExpreUserDefDtoList.add(wfExpreUserDefDto);
                    }
                }
            }
        }
        return wfExpreUserDefDtoList;
    }


    /**
     * 解析变量信息信息
     * @param netwisdVars
     * @param wfNodeDefDto
     * @param baseProcdefId
     * @param processDefinition
     * @return
     */
    public static List<WfVarDefDto> resolveVars(NetwisdVars netwisdVars,WfNodeDefDto wfNodeDefDto,Long baseProcdefId,ProcessDefinition processDefinition,Long formId){
        if(null != netwisdVars) {
            List<NetwisdVar> netwisdVar = netwisdVars.getNetwisdvar();
            List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(netwisdVar)) {
                for (NetwisdVar var : netwisdVar) {
                    WfVarDefDto wfVarDefDto = new WfVarDefDto();
                    wfVarDefDto.setSequDefId(null);
                    wfVarDefDto.setProcdefId(baseProcdefId);
                    wfVarDefDto.setNodeDefId(wfNodeDefDto.getId());
                    //wfVarDefDto.setVarId(Long.valueOf(var.getVarId()));
                    wfVarDefDto.setFormId(formId);
                    wfVarDefDto.setActionScope(Integer.valueOf(var.getActionScope()));
                    wfVarDefDto.setCamundaSequDefId(null);
                    wfVarDefDto.setCamundaNodeDefId(wfNodeDefDto.getCamundaNodeDefId());
                    wfVarDefDto.setCamundaProcdefId(processDefinition.getId());
                    wfVarDefDto.setCamundaProcdefKey(processDefinition.getKey());
                    //wfVarDefDto.setFormVarId(var.getFormVarId());
                    wfVarDefDtoList.add(wfVarDefDto);

                    if(StringUtils.isNotBlank(var.getDbId())) {
                        var.setDbId(var.getDbId());//设置变量 dbId
                        log.debug("BPMN解析-xml编辑：用户节点-变量 dbId不存在,使用DTO生成." + var.toString());
                    } else {
                        var.setDbId(String.valueOf(wfVarDefDto.getId()));//设置变量 dbId
                    }
                }
            }
            return wfVarDefDtoList;
        }
        return  null;
    }

    /**
     * 标签内 特殊字符处理
     * @param content
     * @return
     */
    public static String tagContentCheck(String content){
        content = content.replace("&","&amp;");
        content = content.replace("<","&lt;");
        content = content.replace(">","&gt;");
        return content;
    }
}

