package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:12 下午
 */
@Data
public class CamundaexecutionListener {
    private String clazz;
    private String netwisdclass;
    private String event;
    private List<Camundafield> camundafield;
    private String netwisdeventId;

    private String netwisddbId;

    private String netwisdeventName;
    private String netwisdeventSubmitSign;

    private String netwisdlistenerId;
    private String netwisdlistenerType;
    private String netwisdlistenerImpl;
    private String netwisdeventSelectMust;//是否强制事件

    private String netwisdorder;

    @XmlAttribute
    public String getNetwisddbId() {
        return netwisddbId;
    }

    public void setNetwisddbId(String netwisddbId) {
        this.netwisddbId = netwisddbId;
    }

    @XmlAttribute
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    @XmlAttribute
    public String getNetwisdclass() {
        return netwisdclass;
    }

    public void setNetwisdclass(String netwisdclass) {
        this.netwisdclass = netwisdclass;
    }
    @XmlAttribute
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    @XmlAttribute
    public String getNetwisdeventId() {
        return netwisdeventId;
    }

    public void setNetwisdeventId(String netwisdeventId) {
        this.netwisdeventId = netwisdeventId;
    }
    @XmlAttribute
    public String getNetwisdeventName() {
        return netwisdeventName;
    }

    public void setNetwisdeventName(String netwisdeventName) {
        this.netwisdeventName = netwisdeventName;
    }

    @XmlAttribute
    public String getNetwisdlistenerId() {
        return netwisdlistenerId;
    }

    public void setNetwisdlistenerId(String netwisdlistenerId) {
        this.netwisdlistenerId = netwisdlistenerId;
    }
    @XmlAttribute
    public String getNetwisdlistenerType() {
        return netwisdlistenerType;
    }

    public void setNetwisdlistenerType(String netwisdlistenerType) {
        this.netwisdlistenerType = netwisdlistenerType;
    }
    @XmlAttribute
    public String getNetwisdlistenerImpl() {
        return netwisdlistenerImpl;
    }

    public void setNetwisdlistenerImpl(String netwisdlistenerImpl) {
        this.netwisdlistenerImpl = netwisdlistenerImpl;
    }
    @XmlAttribute
    public String getNetwisdeventSubmitSign() {
        return netwisdeventSubmitSign;
    }

    public void setNetwisdeventSubmitSign(String netwisdeventSubmitSign) {
        this.netwisdeventSubmitSign = netwisdeventSubmitSign;
    }
    @XmlAttribute
    public String getNetwisdeventSelectMust() {
        return netwisdeventSelectMust;
    }

    public void setNetwisdeventSelectMust(String netwisdeventSelectMust) {
        this.netwisdeventSelectMust = netwisdeventSelectMust;
    }
    @XmlAttribute

    public String getNetwisdorder() {
        return netwisdorder;
    }

    public void setNetwisdorder(String netwisdorder) {
        this.netwisdorder = netwisdorder;
    }
}
