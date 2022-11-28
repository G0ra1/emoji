package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: neo4j 节点枚举
 * @Author: XHL@netwisd.com
 * @Date: 2021/9/27 11:21
 */
@AllArgsConstructor
@Getter
public enum NeoNodeEnum {
    USER("User", "用户"),
    POST("Post", "岗位"),
    ROLE("Role", "角色"),
    RES("Res","资源");

    public String code;
    public String message;

    public static String getMessage(String code){
        for (NeoNodeEnum value : NeoNodeEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
