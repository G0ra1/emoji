package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 股份组织类型类型
 * @Author: XHL@netwisd.com
 * @Date: 2021/9/03 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum GuFenOrgTypeEnum {
    K("@K", "岗位"),
    UN("UN", "机构"),
    UM("UM", "部门");

    public String code;
    public String message;

    public static String getMessage(String code){
        for (GuFenOrgTypeEnum value : GuFenOrgTypeEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
