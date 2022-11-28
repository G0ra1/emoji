package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 排序
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/12 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum SortEnum {

    FIRST("first"),
    LAST( "last");

    public String value;

}
