package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * @Description: 自定义按钮节点
 * @Author: XHL@netwisd.com
 * @Date: 2020/7/21 22:42 下午
 */
@Data
public class NetwisdVars {
    private List<NetwisdVar> netwisdvar;
    private String mustFlag;
    @XmlAttribute
    public String getMustFlag() {
        return mustFlag;
    }

    public void setMustFlag(String mustFlag) {
        this.mustFlag = mustFlag;
    }
}
