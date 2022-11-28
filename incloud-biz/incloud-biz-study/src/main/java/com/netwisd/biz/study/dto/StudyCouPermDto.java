package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件授权表 功能描述...
 * @date 2022-04-19 19:13:08
 */
@Data
@ApiModel(value = "课件授权表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyCouPermDto extends IDto {

    /**
     * cou_id
     * 课件主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "课件主键")
    private Long couId;

    /**
     * range_type
     * 授权对象类型
     */
    @ApiModelProperty(value = "授权对象类型")
    private String rangeType;
    /**
     * range_id
     * 授权对象id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "授权对象id")
    private Long rangeId;
    /**
     * range_name
     * 授权对象名称
     */
    @ApiModelProperty(value = "授权对象名称")
    private String rangeName;
}
