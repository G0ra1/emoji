package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:41 上午
 */
@Data
public class BpmnserviceTask implements BpmnFlowNode{
    private String id;
    private String name;
    private String camundaclass;
    private List<String> bpmnincoming;
    private List<String> bpmnoutcoming;
    private BpmnextensionElements bpmnextensionElements;

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
    public String getCamundaclass() {
        return camundaclass;
    }

    public void setCamundaclass(String camundaclass) {
        this.camundaclass = camundaclass;
    }
}
