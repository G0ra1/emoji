package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:43 上午
 */
@Data
public class BpmnexclusiveGateway implements BpmnFlowNode{
    private String id;
    private String name;
    private List<String> bpmnincoming;
    private List<String> bpmnoutgoing;
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
    @XmlElement
    public List<String> getBpmnincoming() {
        return bpmnincoming;
    }

    public void setBpmnincoming(List<String> bpmnincoming) {
        this.bpmnincoming = bpmnincoming;
    }
    @XmlElement
    public List<String> getBpmnoutgoing() {
        return bpmnoutgoing;
    }

    public void setBpmnoutgoing(List<String> bpmnoutgoing) {
        this.bpmnoutgoing = bpmnoutgoing;
    }
}
