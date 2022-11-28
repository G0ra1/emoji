package com.netwisd.base.common.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;

	private String id;

	private String userId;

	private String username;

	private String realName;

	private String password;

	private Boolean enabled;

	private String isdel;

	private String bank;

	private String bankAccount;

	private String email;

	private String phone;

	private Integer sex;

	private String mobilePhone;

	private Date createTime;

	private String deptId;

	private String deptName;

	private String postId;

	private String postName;

	private String jobId;

	private String jobName;

	private String isPartyMember;

	private Integer employment;

	private String workNumber;

	private String mechanismId;//机构ID

	private String mechanismName;//机构名称

	private String identityNumber;//身份证号码

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginValidDate;//登录有效期
}
