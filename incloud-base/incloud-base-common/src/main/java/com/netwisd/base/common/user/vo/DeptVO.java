package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeptVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String deptName;

    private String deptType;

    private String typeName;

    private String parentId;

    private Integer level;

    private Integer sort;

    private String mechanismId;//机构ID

    private String mechanismName;//机构名称

    private String deptAbbreviation;//部门缩写n

    private Integer deptProperty;

    private String taxpayerName;//纳税名称

    private String taxpayerNumber;//纳税人识别号

    private String taxpayerAddress;//纳税人地址

    private String taxpayerPhone;//纳税人电话

    private String taxpayerBank;//纳税人开户行

    private String taxpayerBankAccount;//纳税人开户行账号

    private String isSelect;//0:否 1：是

    private String needSelect;//必须选中的 0:否 1：是

    private String isPrimary;//是否是主部门  0主 1次

    private String deptPropertyText;//部门属性

}
