package com.netwisd.base.common.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyUserVo {
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long id;
    private String userNameCh;//姓名
    private String userName;//账号
    private String idCard;//证件号
    private Integer sex;//性别
    private String phoneNum;//手机号
    private Integer userClass;//来源
    private Integer userType;//用户角色
    private Integer status;//状态
    private String createUserName;//创建人
    private LocalDateTime createTime;//创建时间
    private String parentDeptName;//部门名称
    private String unitName;//单位名称
    private String userConditionCode;//人员情况
    private String userConditionName;//人员情况
}
