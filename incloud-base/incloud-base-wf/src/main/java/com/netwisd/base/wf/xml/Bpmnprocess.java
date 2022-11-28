package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:11 下午
 */
@Data
@XmlType(propOrder = {"bpmnextensionElements","bpmnstartEvent","bpmnsequenceFlow","bpmnuserTask","bpmnexclusiveGateway","bpmnendEvent","bpmnserviceTask","bpmnsubProcess","bpmncallActivity"})
public class Bpmnprocess {
    private String id;
    private String name;
    private String isExecutable;
    private String camundacandidateStarterUsers;
    private String camundaversionTag;
    private String camundataskPriority;
    private String netwisdprocdefTypeCode;
    private String netwisdprocdefTypeName;
    private String netwisdprocdefTypeId;
    private String netwisdremark;
    private String netwisdformId;
    private String netwisdformName;

    private BpmnextensionElements bpmnextensionElements;
    private List<BpmnstartEvent> bpmnstartEvent;
    private List<BpmnsequenceFlow> bpmnsequenceFlow;
    private List<BpmnuserTask> bpmnuserTask;
    private List<BpmnexclusiveGateway> bpmnexclusiveGateway;
    private List<BpmnendEvent> bpmnendEvent;
    private List<BpmnserviceTask> bpmnserviceTask;
    private List<BpmnsubProcess> bpmnsubProcess;
    private List<BpmncallActivity> bpmncallActivity;

    private String netwisddbId; //流程定义id
    private String netwisddbFormId; //流程定义关联的表单id
    private String netwisdformKey;

    @XmlAttribute
    public String getNetwisddbId() {
        return netwisddbId;
    }

    public void setNetwisddbId(String netwisddbId) {
        this.netwisddbId = netwisddbId;
    }
    @XmlAttribute
    public String getNetwisddbFormId() {
        return netwisddbFormId;
    }

    public void setNetwisddbFormId(String netwisddbFormId) {
        this.netwisddbFormId = netwisddbFormId;
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
    public String getIsExecutable() {
        return isExecutable;
    }

    public void setIsExecutable(String isExecutable) {
        this.isExecutable = isExecutable;
    }
    @XmlAttribute
    public String getCamundacandidateStarterUsers() {
        return camundacandidateStarterUsers;
    }

    public void setCamundacandidateStarterUsers(String camundacandidateStarterUsers) {
        this.camundacandidateStarterUsers = camundacandidateStarterUsers;
    }
    @XmlAttribute
    public String getCamundaversionTag() {
        return camundaversionTag;
    }

    public void setCamundaversionTag(String camundaversionTag) {
        this.camundaversionTag = camundaversionTag;
    }
    @XmlAttribute
    public String getCamundataskPriority() {
        return camundataskPriority;
    }

    public void setCamundataskPriority(String camundataskPriority) {
        this.camundataskPriority = camundataskPriority;
    }

    @XmlAttribute
    public String getNetwisdprocdefTypeCode() {
        return netwisdprocdefTypeCode;
    }

    public void setNetwisdprocdefTypeCode(String netwisdprocdefTypeCode) {
        this.netwisdprocdefTypeCode = netwisdprocdefTypeCode;
    }
    @XmlAttribute
    public String getNetwisdprocdefTypeName() {
        return netwisdprocdefTypeName;
    }

    public void setNetwisdprocdefTypeName(String netwisdprocdefTypeName) {
        this.netwisdprocdefTypeName = netwisdprocdefTypeName;
    }
    @XmlAttribute
    public String getNetwisdprocdefTypeId() {
        return netwisdprocdefTypeId;
    }

    public void setNetwisdprocdefTypeId(String netwisdprocdefTypeId) {
        this.netwisdprocdefTypeId = netwisdprocdefTypeId;
    }
    @XmlAttribute
    public String getNetwisdremark() {
        return netwisdremark;
    }

    public void setNetwisdremark(String netwisdremark) {
        this.netwisdremark = netwisdremark;
    }
    @XmlAttribute
    public String getNetwisdformId() {
        return netwisdformId;
    }

    public void setNetwisdformId(String netwisdformId) {
        this.netwisdformId = netwisdformId;
    }
    @XmlAttribute
    public String getNetwisdformName() {
        return netwisdformName;
    }

    public void setNetwisdformName(String netwisdformName) {
        this.netwisdformName = netwisdformName;
    }
    @XmlAttribute
    public String getNetwisdformKey() {
        return netwisdformKey;
    }

    public void setNetwisdformKey(String netwisdformKey) {
        this.netwisdformKey = netwisdformKey;
    }

}
