package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

@Data
public class Camundaout {
    private String source;
    private String target;
    private String local;

    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @XmlAttribute
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    @XmlAttribute
    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
