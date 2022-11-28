package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
@Data
public class Camundastring {
    private String content;
    private String xsitype;

    @XmlValue
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @XmlAttribute
    public String getXsitype() {
        return xsitype;
    }

    public void setXsitype(String xsitype) {
        this.xsitype = xsitype;
    }
}
