package com.netwisd.base.common.user.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmplDTO extends BaseDomainDTO {

    private String jobName;

    private String postName;

    private String ids;

    private String deptIds;

    private String postIds;

    private String emplName;

    private String deptId;

    private String deptName;

    private String emplId;

    private String emplIds;

    private String postId;

    private List<String> deptIdsList;

    private List<String> mechanismIdList;

    private String newPassword;

    private String oldPassword;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginValidDate;//登录有效期
}
