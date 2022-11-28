package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:13 下午
 */
@Data
public class BpmnstartEvent{
    private String id;
    private String name;
    private String bpmnoutgoing;
    private BpmnextensionElements bpmnextensionElements;

    private String netwisddbId;
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
    public String getBpmnoutgoing() {
        return bpmnoutgoing;
    }

    public void setBpmnoutgoing(String bpmnoutgoing) {
        this.bpmnoutgoing = bpmnoutgoing;
    }
}
