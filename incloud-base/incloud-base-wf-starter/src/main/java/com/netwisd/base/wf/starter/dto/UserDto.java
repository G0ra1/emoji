package com.netwisd.base.wf.starter.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import lombok.Data;

/**
 * @Description: 用户相关信息
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 2:20 下午
 */
@Data
public class UserDto implements IValidation {
    @Valid(nullMsg = "用户ID不能为空！")
    private String userId;
/*    @Valid(nullMsg = "用户名称不能为空！")
    private String userName;
    @Valid(nullMsg = "部门ID不能为空！")
    private String deptId;
    @Valid(nullMsg = "部门名称不能为空！")
    private String deptName;
    @Valid(nullMsg = "机构ID不能为空！")
    private String orgId;
    @Valid(nullMsg = "机构名称不能为空！")
    private String orgName;*/
}
