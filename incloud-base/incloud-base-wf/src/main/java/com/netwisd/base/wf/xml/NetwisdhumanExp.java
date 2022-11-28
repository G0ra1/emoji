package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/15 22:42 下午
 */
@Data
public class NetwisdhumanExp {
    private String expreId; //表达式id
    private String expreName;//表达式名称
    private String expreExpression; //表达式
    private List<NetwisdhumanExpParam> netwisdhumanExpParam;
    private String bizType; //(人员、职位、岗位、角色)类型
    private String bizId; //对应的id值
    private String bizText; //对应的值名称

    private String dbId;
    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @XmlAttribute
    public String getExpreId() {
        return expreId;
    }

    public void setExpreId(String expreId) {
        this.expreId = expreId;
    }
    @XmlAttribute
    public String getExpreName() {
        return expreName;
    }

    public void setExpreName(String expreName) {
        this.expreName = expreName;
    }
    @XmlAttribute
    public String getExpreExpression() {
        return expreExpression;
    }

    public void setExpreExpression(String expreExpression) {
        this.expreExpression = expreExpression;
    }
    @XmlAttribute
    public String getBizText() {
        return bizText;
    }

    public void setBizText(String bizText) {
        this.bizText = bizText;
    }

    @XmlAttribute
    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @XmlAttribute
    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
}
