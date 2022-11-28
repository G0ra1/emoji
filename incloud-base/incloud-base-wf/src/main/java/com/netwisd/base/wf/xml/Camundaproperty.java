package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:13 下午
 */
@Data
public class Camundaproperty {
    private String name;
    private String value;
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
