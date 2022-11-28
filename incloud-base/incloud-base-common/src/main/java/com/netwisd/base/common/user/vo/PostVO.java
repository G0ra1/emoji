package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostVO implements Serializable {

    private static final long serialVersionUID = 7780183540402557285L;

    private String id;

    private String descr;

    private String deptId;

    private String deptName;

    private String mechanismId;

    private String mechanismName;

    private String postName;

    private String postCode;

    private Integer postType;

    private Integer isPrimary;

    private Integer isSelect;

}
