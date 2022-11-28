package com.netwisd.base.common.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysVO implements Serializable {

    private String id;

    private String sysCode;

    private String sysName;

    private String descr;

    private String sysAbbreviation;

    private List<ResourceVO> resourceList;


    private Integer effStatus;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changeDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String sysIcon;

    private String createUserId;

    private Integer isDel;

}
