package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 11:08 上午
 */
@Data
public class Camundafield {
    private String netwisdid; //字典参数id(数据库id)
    private String name; //参数id
    private String netwisdparamName; //显示名称
    private String camundastring; //参数类型
    private String camundaexpression; //同上
    private String netwisdisPullDown; //是否下拉选值

    private String netwisddbId;

    @XmlAttribute
    public String getNetwisddbId() {
        return netwisddbId;
    }

    public void setNetwisddbId(String netwisddbId) {
        this.netwisddbId = netwisddbId;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement
    public String getCamundastring() {
        return camundastring;
    }

    public void setCamundastring(String camundastring) {
        this.camundastring = camundastring;
    }
    @XmlElement
    public String getCamundaexpression() {
        return camundaexpression;
    }

    public void setCamundaexpression(String camundaexpression) {
        this.camundaexpression = camundaexpression;
    }
    @XmlAttribute
    public String getNetwisdid() {
        return netwisdid;
    }

    public void setNetwisdid(String netwisdid) {
        this.netwisdid = netwisdid;
    }
    @XmlAttribute
    public String getNetwisdparamName() {
        return netwisdparamName;
    }

    public void setNetwisdparamName(String netwisdparamName) {
        this.netwisdparamName = netwisdparamName;
    }
    @XmlAttribute
    public String getNetwisdisPullDown() {
        return netwisdisPullDown;
    }

    public void setNetwisdisPullDown(String netwisdisPullDown) {
        this.netwisdisPullDown = netwisdisPullDown;
    }
}
