package com.netwisd.base.wf.xml;

import lombok.Data;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnLabel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description:
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/9 16:11 下午
 */
@Data
@XmlType(propOrder = {"id","bpmnElement","isMarkerVisible","isExpanded","dcBounds","bpmndiBPMNLabel"})
public class BpmndiBPMNShape {
    private String id;
    private String bpmnElement;
    private String isMarkerVisible;
    private String isExpanded;
    private DcBounds dcBounds;
    private BpmndiBPMNLabel bpmndiBPMNLabel;
    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getBpmnElement() {
        return bpmnElement;
    }

    public void setBpmnElement(String bpmnElement) {
        this.bpmnElement = bpmnElement;
    }
    @XmlAttribute
    public String getIsMarkerVisible() {
        return isMarkerVisible;
    }

    public void setIsMarkerVisible(String isMarkerVisible) {
        this.isMarkerVisible = isMarkerVisible;
    }
    @XmlAttribute
    public String getIsExpanded() {
        return isExpanded;
    }

    public void setIsExpanded(String isExpanded) {
        this.isExpanded = isExpanded;
    }
}
