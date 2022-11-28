package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:41 上午
 */
@Data
public class BpmnconditionExpression {
    private String xsitype;
    private String content;
    @XmlAttribute
    public String getXsitype() {
        return xsitype;
    }

    public void setXsitype(String xsitype) {
        this.xsitype = xsitype;
    }
    @XmlValue
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
