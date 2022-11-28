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
public enum StatusEnum {
    RUNNING(1, "运营中的"),
    BROKEN(2, "被拆分的"),
    CANCELED(3, "被撤消的");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (StatusEnum value : StatusEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
