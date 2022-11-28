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
public class NetwisdexecutionListener {
    private String clazz;
    //private String netwisdclass;
    private String event;
    private List<Netwisdfields> netwisdfield;
    private String eventId;

    private String dbId;

    private String eventName;
    private String eventSubmitSign;

    private String listenerId;
    private String listenerType;
    private String listenerImpl;
    private String eventSelectMust;//是否强制事件
    private String order;

    @XmlAttribute
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    @XmlAttribute
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    @XmlAttribute
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    @XmlAttribute
    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
    @XmlAttribute
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    @XmlAttribute
    public String getEventSubmitSign() {
        return eventSubmitSign;
    }

    public void setEventSubmitSign(String eventSubmitSign) {
        this.eventSubmitSign = eventSubmitSign;
    }
    @XmlAttribute
    public String getListenerId() {
        return listenerId;
    }

    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
    }
    @XmlAttribute
    public String getListenerType() {
        return listenerType;
    }

    public void setListenerType(String listenerType) {
        this.listenerType = listenerType;
    }
    @XmlAttribute
    public String getListenerImpl() {
        return listenerImpl;
    }

    public void setListenerImpl(String listenerImpl) {
        this.listenerImpl = listenerImpl;
    }
    @XmlAttribute
    public String getEventSelectMust() {
        return eventSelectMust;
    }

    public void setEventSelectMust(String eventSelectMust) {
        this.eventSelectMust = eventSelectMust;
    }
    @XmlAttribute

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
