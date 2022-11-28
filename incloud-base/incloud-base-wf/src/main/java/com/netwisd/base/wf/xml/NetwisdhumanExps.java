package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/15 22:42 下午
 */
@Data
public class NetwisdhumanExps {
    private String mustFlag;
    private List<NetwisdhumanExp> netwisdhumanExp;
    @XmlAttribute
    public String getMustFlag() {
        return mustFlag;
    }

    public void setMustFlag(String mustFlag) {
        this.mustFlag = mustFlag;
    }
    @XmlElement
    public List<NetwisdhumanExp> getNetwisdhumanExp() {
        return netwisdhumanExp;
    }

    public void setNetwisdhumanExp(List<NetwisdhumanExp> netwisdhumanExp) {
        this.netwisdhumanExp = netwisdhumanExp;
    }
}
