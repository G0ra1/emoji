package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 11:12 上午
 */
@Data
public class NetwisdtaskListener {
    private String clazz;
    private String event;
    private String id;
    private List<Netwisdfields> netwisdfield;
    private String eventId;
    private String eventSubmitSign;
    private String listenerImpl;

    private String dbId;

    private String eventName;
    private String listenerId;
    private String listenerType;
    private String eventSelectMust;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    @XmlAttribute
    public String getEventSubmitSign() {
        return eventSubmitSign;
    }

    public void setEventSubmitSign(String eventSubmitSign) {
        this.eventSubmitSign = eventSubmitSign;
    }
    @XmlAttribute
    public String getListenerImpl() {
        return listenerImpl;
    }

    public void setListenerImpl(String listenerImpl) {
        this.listenerImpl = listenerImpl;
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
