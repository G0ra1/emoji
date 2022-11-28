package com.netwisd.base.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 1:51 下午
 */
@AllArgsConstructor
@Getter
public enum SuspensionState {
    ACTIVE(1),SUSPENDED(2);
    private Integer stateCode;
}
