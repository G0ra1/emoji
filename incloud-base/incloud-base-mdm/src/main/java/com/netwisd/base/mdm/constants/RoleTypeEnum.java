package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 角色类型
 * @Author: XHL@netwisd.com
 * @Date: 2021/9/03 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum RoleTypeEnum {
    RUNNING(1, "功能角色"),
    CANCELED(2, "数据角色"),
    BROKEN(3, "流程角色");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (RoleTypeEnum value : RoleTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
