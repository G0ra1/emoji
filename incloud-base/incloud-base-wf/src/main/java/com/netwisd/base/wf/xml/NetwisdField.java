package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/19 22:42 下午
 */
@Data
public class NetwisdField {
    private String id; //字典表单字段id
    //private String isView; //是否可见
    //private String isWrite; //可读可写
    private String varId;//字段代码code
    private String varName;//字段名称
    private String javaType;//字段名称
    private String varType;//字段类型
    private String powerCode; //权限code
    private String isMoreRow; //是否是多行数据

    private String dbId; //表单字段维护id
    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }
    @XmlAttribute
    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
    @XmlAttribute
    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    @XmlAttribute
    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }
    @XmlAttribute
    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }
    @XmlAttribute
    public String getIsMoreRow() {
        return isMoreRow;
    }

    public void setIsMoreRow(String isMoreRow) {
        this.isMoreRow = isMoreRow;
    }
}
