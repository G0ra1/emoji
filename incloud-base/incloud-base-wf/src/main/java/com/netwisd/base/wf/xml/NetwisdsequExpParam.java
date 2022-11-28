package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description: 序列流表达式参数
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/21 22:42 下午
 */
@Data
public class NetwisdsequExpParam {
    private String expreParamId;
    private String expreParamValue;
    private String expreParamSource;

    private String dbId;
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
}
