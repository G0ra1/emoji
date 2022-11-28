package com.netwisd.base.wf.starter.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 2:27 下午
 */
@AllArgsConstructor
@Getter
public enum SubmitTypeEnum {
    SUBMIT(0,""),
    //STARTING(1,"起草"),
    SUBMIT_TO_REJECT_NODE(1,"提交到原驳回节点"),
    COUNTERSIGN(2,"加签"),
    ;

    private Integer type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (SubmitTypeEnum var : SubmitTypeEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
