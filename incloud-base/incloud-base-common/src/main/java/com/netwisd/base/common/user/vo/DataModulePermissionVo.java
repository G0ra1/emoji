package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataModulePermissionVo implements Serializable {

    private String id;

    private Integer effStatus;

    private String effStatusText;

    private String dataModuleType;

    private String dataModuleTypeText;

    private String mechanismId;

    private String mechanismName;

    private String deptIds;

    private String deptName;

    private String userIds;

    private String emplIds;

    private String userName;

    private String perDeptIds;

    private String perEmplIds;

    private String perMechanismIds;

    private String perUserIds;

    private String perUserNames;

    private String perDeptNames;
}
