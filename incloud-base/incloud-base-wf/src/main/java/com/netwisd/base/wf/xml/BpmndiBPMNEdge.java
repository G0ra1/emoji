package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Description:
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/9 16:11 下午
 */
@Data
@XmlType(propOrder = {"id","bpmnElement","diwaypoint","bpmndiBPMNLabel"})
public class BpmndiBPMNEdge {
    private String id;
    private String bpmnElement;
    private List<Diwaypoint> diwaypoint;
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
}
