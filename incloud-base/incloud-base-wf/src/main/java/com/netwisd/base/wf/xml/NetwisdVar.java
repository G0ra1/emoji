package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/21 22:42 下午
 */
@Data
public class NetwisdVar {
    private String formVarId; //字典表单字段id(字段代码)
    private String formVarName;//字段名称
    private String actionScope; //作用域
    private String formId; //表单id
    private String varId; //表单字段维护数据库id
    private String javaType; //java字段类型
    private String isMoreRow; //是否多行


    private String dbId;
    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @XmlAttribute
    public String getFormVarId() {
        return formVarId;
    }

    public void setFormVarId(String formVarId) {
        this.formVarId = formVarId;
    }
    @XmlAttribute
    public String getActionScope() {
        return actionScope;
    }

    public void setActionScope(String actionScope) {
        this.actionScope = actionScope;
    }
    @XmlAttribute
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
    @XmlAttribute
    public String getFormVarName() {
        return formVarName;
    }

    public void setFormVarName(String formVarName) {
        this.formVarName = formVarName;
    }
    @XmlAttribute
    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }
    @XmlAttribute
    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    @XmlAttribute
    public String getIsMoreRow() {
        return isMoreRow;
    }

    public void setIsMoreRow(String isMoreRow) {
        this.isMoreRow = isMoreRow;
    }
}
