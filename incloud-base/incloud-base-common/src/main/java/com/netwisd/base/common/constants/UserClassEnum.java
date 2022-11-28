package com.netwisd.base.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 用户分类
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum UserClassEnum {

    SYS(1, "系统用户"),
    BIZ(2, "中原建设用户"),
    EDU(3, "教育培训用户");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (UserClassEnum value : UserClassEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
