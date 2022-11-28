package com.netwisd.base.common.user.dto;

import lombok.Data;

@Data
public class RoleDTO extends BaseDomainDTO {

    private String roleName;

    private String roleId;

    private String roleCode;

    private String deptIds;

    private String emplIds;

    private String postIds;

    private String sysDataIds;

    private String resourceDataIds;

    private String sysIds;

    private String sysId;

    private String resourceIds;
}
