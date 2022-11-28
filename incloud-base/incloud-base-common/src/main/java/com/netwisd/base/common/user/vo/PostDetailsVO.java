package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDetailsVO extends BaseDomainVO {

    private Integer sort;

    private String deptParantAllName;

    private String postName;

    private Integer isPrimary;

    private Integer postType;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changeDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String descr;

    private String deptId;

    private String deptName;

    private String mechanismId;

    private String mechanismName;

    private String createUserId;

    private String postCode;

    private Integer isDel;

    private Boolean emplSelected = false;

    private Boolean roleSelected = false;

    private List<RoleDetailsVO> roleList;

    private List<EmplVO> emplList;

    private String fixedRulesId;

    private String fixedRulesName;
}