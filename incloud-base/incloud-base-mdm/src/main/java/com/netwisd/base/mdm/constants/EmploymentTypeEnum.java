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
public enum EmploymentTypeEnum {
    CONTRACT_USER(0, "合同制(正式员工)"),
    LABOR_DISPATCH(1, "派遣制"),
    OUTSOURCE_USER(2, "外包制(外协员工)"),
    DAY_LABORER (3, "临时工"),
    PROBATIONER(4, "实习生");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (EmploymentTypeEnum value : EmploymentTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
