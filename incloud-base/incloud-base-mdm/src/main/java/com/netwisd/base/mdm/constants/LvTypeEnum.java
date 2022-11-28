package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: geps  使用的级别类型状态
 * @Author: XHL@netwisd.com
 * @Date: 2021/10/24 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum LvTypeEnum {
    CONSTRUCTION_COMPANY(1, "建设公司"),
    CONTROLLED_CORPORATION(2, "分公司"),
    PROJECT_DEPARTMENT(3, "项目部"),
    FUNCTIONAL_DEPARTMENT(4, "职能部门");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (LvTypeEnum value : LvTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
