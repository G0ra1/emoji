package com.netwisd.common.core.constants;

import lombok.AllArgsConstructor;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.core.constants
 * @Author: zouliming
 * @Date: 2020/2/4 4:33 下午
 */
@AllArgsConstructor
public enum ResultConstants {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    SC_UNAUTHORIZED(401, "认证失败");
    public int code;
    public String msg;
}
