package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleDetailsVO extends BaseDomainVO {

    private List<SysVO> sysList;

    private List<DeptDetailsVO> deptList;

    private List<EmplDetailsVO> emplList;

    private List<PostDetailsVO> postList;

    private List<PostDetailsVO> jobList;

    private String roleCode;

    private String roleName;

    private Integer effStatus;

    private String descr;
}
