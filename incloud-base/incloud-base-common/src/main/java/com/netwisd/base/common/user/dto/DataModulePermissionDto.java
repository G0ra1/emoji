package com.netwisd.base.common.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataModulePermissionDto extends BaseDomainDTO implements Serializable {

    private String dataModuleType;

    private String deptIds;//查看着部门

    private String userIds;//查看着人员UserId

    private String perDeptIds;//数据权限部门

    private String perUserIds;//数据权限人员

    private Integer effStatus;

}
