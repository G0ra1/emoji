package com.netwisd.base.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 用户账号登录方式
 * @Author: XHL
 * @Date: 2021/11/23 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum LoginEnum {

    EMAIL("1#", "邮箱登录"),
    PHONE("2#", "手机号登录"),
    IDNUMBER("3#", "身份证号登录"),
    USERNAME("4#", "用户名登录"),
    VERIFYCODE("5#", "验证码登录")
    ;

    public String code;
    public String message;

    public static String getMessage(Integer code){
        for (LoginEnum value : LoginEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
