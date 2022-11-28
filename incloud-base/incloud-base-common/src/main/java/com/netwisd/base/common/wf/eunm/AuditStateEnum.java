package com.netwisd.base.common.wf.eunm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com 测试构
 * @Date: 2020/8/6 2:27 下午
 */
@AllArgsConstructor
@Getter
public enum AuditStateEnum {
    NONE(0,""),
    STARTING(1,"起草"),
    SUBMIT(2,"运行"),
    SUSPEND(5,"挂起"),
    ACTIVATE(6,"激活"),
    COMPLETE(7,"结束"),
    TERMINATION(8,"终止")
    ;

    private Integer type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (AuditStateEnum var : AuditStateEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
