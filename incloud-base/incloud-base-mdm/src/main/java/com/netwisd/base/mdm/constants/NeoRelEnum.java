package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: neo4j 关系枚举
 * @Author: XHL@netwisd.com
 * @Date: 2021/9/27 11:21
 */
@AllArgsConstructor
@Getter
public enum NeoRelEnum {
    ROLE_RES("Role_Res", "角色-资源"),
    RES_ROLE("Res_Role", "资源-角色"),

    ROLE_USER("Role_User", "角色-人员"),
    USER_ROLE("User_Role", "人员-角色"),

    ROLE_POST("Role_Post", "角色-岗位"),
    POST_ROLE("Post_Role", "岗位-角色"),

    POST_User("Post_User", "岗位-人员"),
    USER_POST("User_Post", "人员-岗位");

    public String code;
    public String message;

    public static String getMessage(String code){
        for (NeoRelEnum value : NeoRelEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
