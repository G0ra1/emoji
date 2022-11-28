package com.netwisd.base.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 机构分类
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum OrgClassEnum {

    SYS(1, "系统级"),
    BIZ(2, "中原建设"),
    EDU(3, "教育培训");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (OrgClassEnum value : OrgClassEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
