package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EmplVO implements Serializable {

    private List<RoleVO> roleList;

    private List<PostDetailsVO> jobList;

    private List<PostVO> postList;

    private String emplPostId;

    private Integer deptProperty;

    private String id;

    private String userId;

    private String realName;

    private String userName;

    private String isdel;

    private String bank;

    private String bankAccount;

    private String email;

    private String phone;

    private Integer sex;

    private String mobilePhone;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String deptId;

    private String deptName;

    private String postId;

    private String postCode;

    private String postName;

    private String jobId;

    private String jobCode;

    private String jobName;

    private String isPartyMember;

    private Integer employment;

    private Integer enabled;

    private String workNumber;

    private String identityNumber;//身份证号码

    private String mechanismId;//机构ID

    private String mechanismName;//机构名称

    private List<SysVO> sysList;

    private List<ResourceDetailsVO> resources;

    private String isPrimaryPost;//主，次(岗位、职位)

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginValidDate;//登录有效期
}
