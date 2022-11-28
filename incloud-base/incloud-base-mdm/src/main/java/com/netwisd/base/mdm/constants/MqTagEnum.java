package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: rock tag
 * @Author: XHL@netwisd.com
 * @Date: 2021/10/11 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum MqTagEnum {
    ADD("add", "增量"),
    EDIT("edit", "修改"),
    DEL("del", "删除");

    public String code;
    public String message;

    public static String getMessage(String code){
        for (MqTagEnum value : MqTagEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
