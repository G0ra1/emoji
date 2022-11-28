package com.netwisd.base.common.user.dto;

import lombok.Data;

@Data
public class ResourceDTO extends BaseDomainDTO {

    private String sysId;

    private String resName;

    private String roleId;

    private String roleCode;

    private String emplId;

    private String resIds;
}
