package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeptTypeVO implements Serializable {

    private Integer deptType; // 部门类型

    private String typeName;// 部门类型名称

}
