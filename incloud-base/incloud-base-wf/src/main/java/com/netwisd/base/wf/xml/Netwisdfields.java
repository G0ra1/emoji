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
public class Netwisdfields {
    private String id; //字典参数id(数据库id)
    private String name; //参数id
    private String paramName; //显示名称
    private String netwisdstring; //参数类型
    private String netwisdexpression; //同上
    private String isPullDown; //是否下拉选值
    private String dbId;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    @XmlElement
    public String getNetwisdstring() {
        return netwisdstring;
    }

    public void setNetwisdstring(String netwisdstring) {
        this.netwisdstring = netwisdstring;
    }
    @XmlElement
    public String getNetwisdexpression() {
        return netwisdexpression;
    }

    public void setNetwisdexpression(String netwisdexpression) {
        this.netwisdexpression = netwisdexpression;
    }

    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @XmlAttribute
    public String getIsPullDown() {
        return isPullDown;
    }

    public void setIsPullDown(String isPullDown) {
        this.isPullDown = isPullDown;
    }
}
