package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:11 下午
 */
@Data
public class BpmnextensionElements {
    private List<CamundaexecutionListener> camundaexecutionListener;
    private List<CamundataskListener> camundataskListener;
    private Camundaproperties camundaproperties;
    //private NetwisdbuttonDefs netwisdbuttonDefs; //统一使用一种解析
    private NetwisdhumanExps netwisdhumanExps; //节点表达式
    private NetwisdNodeForm netwisdnodeForm; //表单数据
    private NetwisdnodeButtons netwisdnodeButtons; //按钮数据
    private NetwisdVars netwisdvars; //节点变量数据
    private List<NetwisdsequExps> netwisdsequExps; //序列流表达式
    private String netwisdsequExpText;

    private List<NetwisdexecutionListener> netwisdexecutionListener;
    private List<NetwisdtaskListener> netwisdtaskListener;

    private List<Camundain> camundain; //businessKey
    private List<Camundaout> camundaout; //businessKey

    @XmlElement
    public String getNetwisdsequExpText() {
        return netwisdsequExpText;
    }

    public void setNetwisdsequExpText(String netwisdsequExpText) {
        this.netwisdsequExpText = netwisdsequExpText;
    }
    @XmlElement()
    public NetwisdnodeButtons getNetwisdnodeButtons() {
        return netwisdnodeButtons;
    }

    public void setNetwisdnodeButtons(NetwisdnodeButtons netwisdnodeButtons) {
        this.netwisdnodeButtons = netwisdnodeButtons;
    }
    @XmlElement()
    public NetwisdhumanExps getNetwisdhumanExps() {
        return netwisdhumanExps;
    }

    public void setNetwisdhumanExps(NetwisdhumanExps netwisdhumanExps) {
        this.netwisdhumanExps = netwisdhumanExps;
    }
    @XmlElement()
    public NetwisdNodeForm getNetwisdnodeForm() {
        return netwisdnodeForm;
    }

    public void setNetwisdnodeForm(NetwisdNodeForm netwisdnodeForm) {
        this.netwisdnodeForm = netwisdnodeForm;
    }
    @XmlElement()
    public NetwisdVars getNetwisdvars() {
        return netwisdvars;
    }

    public void setNetwisdvars(NetwisdVars netwisdvars) {
        this.netwisdvars = netwisdvars;
    }
    @XmlElement()
    public List<NetwisdsequExps> getNetwisdsequExps() {
        return netwisdsequExps;
    }

    public void setNetwisdsequExps(List<NetwisdsequExps> netwisdsequExps) {
        this.netwisdsequExps = netwisdsequExps;
    }
}
