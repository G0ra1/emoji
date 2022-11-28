package com.netwisd.base.mdm.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "查询用户相关信息 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NcDto {
    private String clientid;
    private String orgcode;
    private String sign;
    private Long time;
    private String name;
    private String year;
    private String pks[];
}
