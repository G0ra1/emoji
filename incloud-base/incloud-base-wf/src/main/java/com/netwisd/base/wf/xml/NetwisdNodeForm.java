package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/19 22:42 下午
 */
@Data
public class NetwisdNodeForm {
    private String id;
    private String formName;
    private List<NetwisdField> netwisdfield; //表单字段list
    private String mustFlag;

    private String dbId;
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
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @XmlAttribute
    public String getMustFlag() {
        return mustFlag;
    }

    public void setMustFlag(String mustFlag) {
        this.mustFlag = mustFlag;
    }
}
