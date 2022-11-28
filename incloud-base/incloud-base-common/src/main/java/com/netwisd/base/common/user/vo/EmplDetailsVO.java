package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.base.common.user.eunm.EmploymentEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmplDetailsVO extends BaseDomainVO {

    private Integer sort;

    private String deptParantAllName;

    private Boolean roleSelected = false;

    private Boolean groupSelected = false;

    private String provinceName;

    private String cityName;

    private String areaName;

    private String homeProvinceName;

    private String homeCityName;

    private String homeAreaName;

    private List<SecondDeptPostDetailsVO> postList;

    private String deptName;

    private String jobName;

    private String postCode;

    private String postName;

    private String mechanismName;

    private String userId;

    private String userName;

    private Integer sex;

    private Integer enabled;

    private String changeDate;

    private String createDate;

    private String birthDate;

    private String createUserId;

    private Integer isDel;

    private String birthplace;

    private String homeProvince;

    private String homeCity;

    private String homeArea;

    private String homeStreet;

    private String nowhomeProvince;

    private String nowhomeCity;

    private String nowhomeArea;

    private String nowhomeStreet;

    private String phone;

    private String mobilePhone;

    private String email;

    private String qq;

    private String weChat;

    private String identityNumber;

    private String identityAddress;

    private String bank;

    private String bankAccount;

    private String mechanismId;

    private String workNumber;

    private String deptId;

    private String avatarAddress;

    private String positionId;//职位

    private String jobId;//岗位

    private Integer isSecret;//是否涉密

    private String secretLevel;//涉密级别

    private String isPartyMember;//是否是党员

    private Integer employment;//用工形式

    private String employmentName = EmploymentEnum.getMsgByCode(this.employment);

    private String uuid;

    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date loginValidDate;//登录有效期
}
