package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description:
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/9 16:11 下午
 */
@Data
public class Diwaypoint {
    private String x;
    private String y;
    @XmlAttribute
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
    @XmlAttribute
    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
