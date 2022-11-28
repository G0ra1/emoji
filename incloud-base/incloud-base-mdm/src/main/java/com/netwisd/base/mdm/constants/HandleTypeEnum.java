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
public enum HandleTypeEnum {
    LEVEL(1, "修改level"),
    FULLINFO(2, "修改fullInfo"),
    STATUS(3, "修改status");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (HandleTypeEnum value : HandleTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
