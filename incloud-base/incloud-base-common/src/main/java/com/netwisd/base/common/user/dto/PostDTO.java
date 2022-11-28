package com.netwisd.base.common.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO extends BaseDomainDTO {

    private String postName;

    private String deptId;

    private String deptName;

    private String postType;

    private List<String> deptIdsList;

    private String fixedRulesId;
}
