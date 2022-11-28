package com.netwisd.common.db.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/13 1:42 下午
 */
@AllArgsConstructor
@Getter
public enum IdTypeEnum {
    DEL("delete"),
    EDIT("update"),
    ADD("insert");
    String name;
}
