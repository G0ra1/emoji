package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:42 上午
 */
@Data
@XmlType(propOrder = {"bpmnextensionElements","bpmnconditionExpression"})
public class BpmnsequenceFlow {
    private String id;
    private String name;
    private String sourceRef;
    private String targetRef;
    private BpmnextensionElements bpmnextensionElements;
    private BpmnconditionExpression bpmnconditionExpression;

    private String netwisdexpression; //seqFlow 上总的 表达式
    private String netwisdexpressionName; //seqFlow 上总的 表达式名称

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
    @XmlAttribute
    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }
    @XmlAttribute
    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    @XmlElement()
    public BpmnconditionExpression getBpmnconditionExpression() {
        return bpmnconditionExpression;
    }

    public void setBpmnconditionExpression(BpmnconditionExpression bpmnconditionExpression) {
        this.bpmnconditionExpression = bpmnconditionExpression;
    }
    @XmlAttribute
    public String getNetwisdexpression() {
        return netwisdexpression;
    }

    public void setNetwisdexpression(String netwisdexpression) {
        this.netwisdexpression = netwisdexpression;
    }
    @XmlAttribute
    public String getNetwisdexpressionName() {
        return netwisdexpressionName;
    }

    public void setNetwisdexpressionName(String netwisdexpressionName) {
        this.netwisdexpressionName = netwisdexpressionName;
    }
}
