package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 2:27 下午
 */
@AllArgsConstructor
@Getter
public enum WfProcessLogEnum {
    NONE(0,""),
    //STARTING(1,"起草"),
    SUBMIT(1,"提交"),
    REJECT(2,"驳回"),
    REVOKE(3,"撤回"),
    DUPLICATE(4,"传阅"),
    SUSPEND(5,"挂起"),
    ACTIVATE(6,"激活"),
    COMPLETE(7,"结束"),
    TERMINATION(8,"终止"),
    TOSB(9,"转办"),
    NOTIFY(10,"知会")
    ;

    private Integer type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (WfProcessLogEnum var : WfProcessLogEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
