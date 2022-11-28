package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 专题与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Data
@ApiModel(value = "专题与对象表 Vo")
public class StudySpecialRangeVo extends IVo{

    /**
     * special_id
     * 培训计划id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="培训计划id")
    private Long specialId;
    /**
     * range_type
     * 对象类型
     */
    @ApiModelProperty(value="对象类型")
    private String rangeType;
    /**
     * range_id
     * 对象id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="对象id")
    private Long rangeId;
    /**
     * range_name
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String rangeName;



}
