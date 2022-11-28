package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:44 上午
 */
@Data
public class BpmnmultiInstanceLoopCharacteristics {
    private String camundacollection;
    private String camundaelementVariable;
    private BpmncompletionCondition bpmncompletionCondition;

    private String netwisdpassingRate; //会签通过率
    private String netwisdpassingHandle; //会签通过处理方式
    private String netwisdunpassingHandle; //会签不通过处理方式
    @XmlAttribute
    public String getCamundacollection() {
        return camundacollection;
    }

    public void setCamundacollection(String camundacollection) {
        this.camundacollection = camundacollection;
    }
    @XmlAttribute
    public String getCamundaelementVariable() {
        return camundaelementVariable;
    }

    public void setCamundaelementVariable(String camundaelementVariable) {
        this.camundaelementVariable = camundaelementVariable;
    }

    @XmlAttribute
    public String getNetwisdpassingRate() {
        return netwisdpassingRate;
    }

    public void setNetwisdpassingRate(String netwisdpassingRate) {
        this.netwisdpassingRate = netwisdpassingRate;
    }
    @XmlAttribute
    public String getNetwisdpassingHandle() {
        return netwisdpassingHandle;
    }

    public void setNetwisdpassingHandle(String netwisdpassingHandle) {
        this.netwisdpassingHandle = netwisdpassingHandle;
    }
    @XmlAttribute
    public String getNetwisdunpassingHandle() {
        return netwisdunpassingHandle;
    }

    public void setNetwisdunpassingHandle(String netwisdunpassingHandle) {
        this.netwisdunpassingHandle = netwisdunpassingHandle;
    }

}
