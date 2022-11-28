package com.netwisd.base.common.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeptDTO extends BaseDomainDTO {

    private String groupId;//组ID也就是组织树的ID。传这个字段是为了回显某个部门下人员是否在这个组中

    private String deptName;

    private String deptId;

    private Integer deptType;

    private String emplId;

    private String roleId;

    private String roleCode;

    private List<String> deptIdsList;

    private String deptAbbreviation;

    private String deptEnglishName;

    private String deptEnglishAbbreviation;

    private String province;

    private String city;

    private String area;

    private String street;

    private String postalCode;

    private String parentId;

    private String mechanismId;

    private String phone;

    private String fax;

    private String email;

    private String url;

    private String businessType;

    private String descr;

    private String deptIcon;

    private Integer deptProperty;

    private String emplIds;

    private String deptTypes;

    private String taxpayerName;//纳税名称

    private String taxpayerNumber;//纳税人识别号

    private String taxpayerAddress;//纳税人地址

    private String taxpayerPhone;//纳税人电话

    private String taxpayerBank;//纳税人开户行

    private String taxpayerBankAccount;//纳税人开户行账号

    private List<String> roleList;
}
