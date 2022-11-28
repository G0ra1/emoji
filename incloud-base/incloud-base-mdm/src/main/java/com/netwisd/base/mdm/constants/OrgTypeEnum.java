package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 通用状态
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum OrgTypeEnum {

    ORG(1, "组织"),
    DEPT(2, "部门");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (OrgTypeEnum value : OrgTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
