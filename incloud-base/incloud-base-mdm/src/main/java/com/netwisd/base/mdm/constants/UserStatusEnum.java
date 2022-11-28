package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 人员装填
 * @Author: XHL@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum UserStatusEnum {
    ONJOB(1, "在职"),
    QUIT(2, "离职"),
    ;

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (UserStatusEnum value : UserStatusEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
