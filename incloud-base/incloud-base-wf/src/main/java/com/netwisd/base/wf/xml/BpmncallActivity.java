package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

/**
 * @Description:
 * @Author: XHL@netwisd.com
 * @Date: 2020/10/21 9:41 上午
 */
@Data
@XmlType(propOrder = {"id","name","calledElement","camundacalledElementBinding","camundacalledElementVersion","bpmnextensionElements","bpmnincoming","bpmnoutgoing","bpmnmultiInstanceLoopCharacteristics"})
public class BpmncallActivity implements BpmnFlowNode{
    private String id;
    private String name;
    private String calledElement; //子流程key
    private BpmnextensionElements bpmnextensionElements;
    private List<String> bpmnincoming;
    private List<String> bpmnoutgoing;
    private BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics;
    private String camundacalledElementBinding; //绑定类型
    private String camundacalledElementVersion; //子流程对应的版本号
    private String netwisddbId;//用户任务id

    private String netwisdisLookOver;
    private String netwisdisLookMe;
    private String netwisdcmdChildLogProcdefId;
    private String netwisdctmChildLogProcdefId;
    private String netwisdchildProcdefName;

    @XmlAttribute
    public String getNetwisddbId() {
        return netwisddbId;
    }

    public void setNetwisddbId(String netwisddbId) {
        this.netwisddbId = netwisddbId;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getCalledElement() {
        return calledElement;
    }

    public void setCalledElement(String calledElement) {
        this.calledElement = calledElement;
    }
    @XmlAttribute
    public String getCamundacalledElementVersion() {
        return camundacalledElementVersion;
    }

    public void setCamundacalledElementVersion(String camundacalledElementVersion) {
        this.camundacalledElementVersion = camundacalledElementVersion;
    }

    @XmlAttribute
    public String getCamundacalledElementBinding() {
        return camundacalledElementBinding;
    }

    public void setCamundacalledElementBinding(String camundacalledElementBinding) {
        this.camundacalledElementBinding = camundacalledElementBinding;
    }
    @XmlAttribute
    public String getNetwisdisLookOver() {
        return netwisdisLookOver;
    }

    public void setNetwisdisLookOver(String netwisdisLookOver) {
        this.netwisdisLookOver = netwisdisLookOver;
    }
    @XmlAttribute
    public String getNetwisdcmdChildLogProcdefId() {
        return netwisdcmdChildLogProcdefId;
    }

    public void setNetwisdcmdChildLogProcdefId(String netwisdcmdChildLogProcdefId) {
        this.netwisdcmdChildLogProcdefId = netwisdcmdChildLogProcdefId;
    }
    @XmlAttribute
    public String getNetwisdisLookMe() {
        return netwisdisLookMe;
    }

    public void setNetwisdisLookMe(String netwisdisLookMe) {
        this.netwisdisLookMe = netwisdisLookMe;
    }
    @XmlAttribute
    public String getNetwisdctmChildLogProcdefId() {
        return netwisdctmChildLogProcdefId;
    }

    public void setNetwisdctmChildLogProcdefId(String netwisdctmChildLogProcdefId) {
        this.netwisdctmChildLogProcdefId = netwisdctmChildLogProcdefId;
    }
    @XmlAttribute
    public String getNetwisdchildProcdefName() {
        return netwisdchildProcdefName;
    }

    public void setNetwisdchildProcdefName(String netwisdchildProcdefName) {
        this.netwisdchildProcdefName = netwisdchildProcdefName;
    }
}
