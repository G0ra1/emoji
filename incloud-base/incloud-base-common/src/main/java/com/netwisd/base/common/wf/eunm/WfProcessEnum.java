package com.netwisd.base.common.wf.eunm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 流程的状态 构建
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 2:27 下午
 */
@AllArgsConstructor
@Getter
public enum WfProcessEnum {
    DRAFT(0, "草稿"),
    RUNNING(1,"运行中"),
    DONE(2,"完成"),
    SUPENSION(3,"挂起"),
    TERMINATION(4,"终止"),
    NOTIFY(5,"知会"),
    ;

    private Integer type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (WfProcessEnum var : WfProcessEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
    public static String getMessage(Integer type){
        for (WfProcessEnum value : WfProcessEnum.values()) {
            if(value.type == type){
                return value.name;
            }
        }
        return null;
    }
}
