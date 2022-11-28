package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.base.common.user.eunm.DeptPropertyEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DeptDetailsVO extends BaseDomainVO {

    private Integer sort;

    //数据返回的都是true,说明有权限
    private String isPermission = "false";

    //组织的所有上级组织名称
    private String deptParantAllName;

    private Boolean roleSelected = false;

    private Integer deptProperty;

    private String deptPropertyName = DeptPropertyEnum.getMsgByCode(this.deptProperty);

    private String provinceName;

    private String cityName;

    private String areaName;

    private String parantDeptName;

    private String mechanismName;

    private String typeName;

    private String deptName;

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

    private Integer deptType;

    private String businessType;

    private String descr;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changeDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String createUserId;

    private String deptIcon;

    private Integer isDel;

    private String taxpayerName;//纳税名称

    private String taxpayerNumber;//纳税人识别号

    private String taxpayerAddress;//纳税人地址

    private String taxpayerPhone;//纳税人电话

    private String taxpayerBank;//纳税人开户行

    private String taxpayerBankAccount;//纳税人开户行账号

    private Integer level;

    private List<RoleDetailsVO> roleList = new ArrayList<>();

    private List<EmplDetailsVO> emplList = new ArrayList<>();

    private List<PostDetailsVO> postList = new ArrayList<>();

    private List<PostDetailsVO> jobList = new ArrayList<>();

}