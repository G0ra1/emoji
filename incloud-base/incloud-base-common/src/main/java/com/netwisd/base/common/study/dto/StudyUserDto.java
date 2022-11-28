package com.netwisd.base.common.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "在线学习用户 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyUserDto extends IDto {
    private Long parentOrgId;
    private String parentOrgName;
    private Long parentDeptId;
    private String parentDeptName;
    private String orgFullId;
    private String orgFullName;
    private String parentOrgFullName;
    private String parentDeptFullName;
    private String userType;
    private Integer userClass;
    private String userNameCh;
    private String passWord;
    private Integer sex;
    private String phoneNum;
    private Integer cardType;
    private String idCard;
    private String email;
    private Integer status;
}
