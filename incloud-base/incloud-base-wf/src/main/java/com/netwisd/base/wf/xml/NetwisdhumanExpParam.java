package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/15 22:42 下午
 */
@Data
public class NetwisdhumanExpParam {
    private String expreParamId; //表达式参数ID
    private String expreParamValue; //参数值
    private String expreParamSource; //参数来源
    private String expreParamValueText;//参数名称
    private String expreParamVarType; //参数类型

    private String dbId; //表达式参数id
    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @XmlAttribute
    public String getExpreParamId() {
        return expreParamId;
    }

    public void setExpreParamId(String expreParamId) {
        this.expreParamId = expreParamId;
    }
    @XmlAttribute
    public String getExpreParamValue() {
        return expreParamValue;
    }

    public void setExpreParamValue(String expreParamValue) {
        this.expreParamValue = expreParamValue;
    }
    @XmlAttribute
    public String getExpreParamSource() {
        return expreParamSource;
    }

    public void setExpreParamSource(String expreParamSource) {
        this.expreParamSource = expreParamSource;
    }

    @XmlAttribute
    public String getExpreParamValueText() {
        return expreParamValueText;
    }

    public void setExpreParamValueText(String expreParamValueText) {
        this.expreParamValueText = expreParamValueText;
    }
    @XmlAttribute
    public String getExpreParamVarType() {
        return expreParamVarType;
    }

    public void setExpreParamVarType(String expreParamVarType) {
        this.expreParamVarType = expreParamVarType;
    }
}
