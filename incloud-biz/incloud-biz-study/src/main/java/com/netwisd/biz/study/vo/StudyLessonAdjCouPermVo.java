package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 课程课件授权调整表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:54:14
 */
@Data
@ApiModel(value = "课程课件授权调整表 Vo")
public class StudyLessonAdjCouPermVo extends IVo{

    /**
     * lesson_adj_id
     * 课程主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="课程主键")
    private Long lessonAdjId;
    /**
     * cou_id
     * 课件主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="课件主键")
    private Long couId;
    /**
     * range_type
     * 授权对象类型
     */
    
    @ApiModelProperty(value="授权对象类型")
    private String rangeType;
    /**
     * range_id
     * 授权对象id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="授权对象id")
    private Long rangeId;
    /**
     * range_name
     * 授权对象名称
     */
    
    @ApiModelProperty(value="授权对象名称")
    private String rangeName;
}
