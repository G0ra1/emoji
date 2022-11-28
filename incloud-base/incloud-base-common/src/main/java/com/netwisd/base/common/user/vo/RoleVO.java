package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleCode; //角色编码

    private String roleName;//角色名称

    private Integer effStatus;

    private String descr;
}
