package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourceDetailsVO implements Serializable {

    private static final long serialVersionUID = 466100741698109635L;

    private String id;

    private String resCode;

    private String resName;

    private String resType;

    private String resUrl;

    private String resIcon;

    private String parentId;

    private String parentName;

    private String permission;

    private Integer effStatus;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changeDate;

    private String sysId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String createUserId;

    private Integer isDel;

    private Boolean roleSelected = false;

    private Integer sort;
}
