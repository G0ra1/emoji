package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description:
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/9 16:11 下午
 */
@Data
public class BpmndiBPMNPlane {
    private String id;
    private String bpmnElement;
    private List<BpmndiBPMNEdge> bpmndiBPMNEdge;
    private List<BpmndiBPMNShape> bpmndiBPMNShape;
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
