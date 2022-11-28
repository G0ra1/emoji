package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
public class Camundain {
    private String businessKey;
    private String source;
    private String target;
    private String local;
    private String netwisdreadOnly;
    @XmlAttribute
    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }
    @XmlAttribute
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
    @XmlAttribute
    public String getNetwisdreadOnly() {
        return netwisdreadOnly;
    }

    public void setNetwisdreadOnly(String netwisdreadOnly) {
        this.netwisdreadOnly = netwisdreadOnly;
    }
}
