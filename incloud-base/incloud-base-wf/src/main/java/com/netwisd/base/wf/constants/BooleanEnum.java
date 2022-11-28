package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/28 10:41 上午
 */
@AllArgsConstructor
@Getter
public enum  BooleanEnum {
    TRUE(1),
    FALSE(0);
    int value;
}
