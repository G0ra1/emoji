package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:41 上午
 */
@Data
@XmlType(propOrder = {"bpmnextensionElements","bpmnincoming","bpmnoutgoing","bpmnmultiInstanceLoopCharacteristics","bpmnstartEvent","bpmnsequenceFlow","bpmnuserTask","bpmnexclusiveGateway","bpmnendEvent","bpmnserviceTask","bpmnsubProcess","bpmncallActivity"})
public class BpmnsubProcess {
    private String id;
    private String name;
    private BpmnextensionElements bpmnextensionElements;
    private List<BpmnstartEvent> bpmnstartEvent;
    private List<BpmnsequenceFlow> bpmnsequenceFlow;
    private List<BpmnuserTask> bpmnuserTask;
    private List<BpmnexclusiveGateway> bpmnexclusiveGateway;
    private List<BpmnendEvent> bpmnendEvent;
    private BpmnserviceTask bpmnserviceTask;
    private List<BpmnsubProcess> bpmnsubProcess;
    private List<BpmncallActivity> bpmncallActivity;
    private List<String> bpmnincoming;
    private List<String> bpmnoutgoing;

    private String netwisdremark;//子流程备注
    private String netwisdformId;//表单id
    private String netwisdformName;//表单名称
    private String netwisddbFormId; //流程定义关联的表单id
    private String netwisdformKey;//表单key

    private BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics;

    private String netwisddbId;//用户任务id

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
    public String getNetwisddbFormId() {
        return netwisddbFormId;
    }

    public void setNetwisddbFormId(String netwisddbFormId) {
        this.netwisddbFormId = netwisddbFormId;
    }
    @XmlAttribute
    public String getNetwisdformKey() {
        return netwisdformKey;
    }

    public void setNetwisdformKey(String netwisdformKey) {
        this.netwisdformKey = netwisdformKey;
    }
}
