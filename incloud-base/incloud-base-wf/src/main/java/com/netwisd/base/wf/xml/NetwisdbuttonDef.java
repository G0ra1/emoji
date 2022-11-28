package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/15 22:42 下午
 */
@Data
public class NetwisdbuttonDef {
    private String buttonId;
    private String buttonName;
    private String isEnable;
    @XmlAttribute
    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }
    @XmlAttribute
    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
    @XmlAttribute
    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
