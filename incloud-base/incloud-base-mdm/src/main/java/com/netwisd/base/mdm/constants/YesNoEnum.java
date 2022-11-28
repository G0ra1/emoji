package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum YesNoEnum {

    NO(0, "否"),
    YES(1, "是");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (YesNoEnum value : YesNoEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
