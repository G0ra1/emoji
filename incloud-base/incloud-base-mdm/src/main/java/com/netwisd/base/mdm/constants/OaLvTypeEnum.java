package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: OA 使用的级别类型状态
 * @Author: XHL@netwisd.com
 * @Date: 2021/10/24 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum OaLvTypeEnum {
    ORG(1, "oa机构"),
    DEPT(2, "oa部门"),
    ;

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (OaLvTypeEnum value : OaLvTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
