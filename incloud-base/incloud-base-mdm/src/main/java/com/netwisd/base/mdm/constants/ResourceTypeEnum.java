package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 资源管理类型
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/9/9 11:21
 */
@AllArgsConstructor
@Getter
public enum ResourceTypeEnum {
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    ENTRY(3, "流程入口"),
    CARD(4,"卡片");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (ResourceTypeEnum value : ResourceTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
