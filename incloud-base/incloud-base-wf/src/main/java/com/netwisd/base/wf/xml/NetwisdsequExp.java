package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description: 序列流 表达式
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/21 22:42 下午
 */
@Data
public class NetwisdsequExp {
    private String expreId; //字典表达式id
    private String expreName; //字典表达式名称
    private String expression; //表达式
    private List<NetwisdsequExpParam> netwisdsequExpParam;

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
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
