package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@Data
@ApiModel(value = "课程评分表 Vo")
public class StudyLessonScoreVo extends IVo{

    /**
     * lesson_id
     * 课程主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="课程主键")
    private Long lessonId;
    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value="课程名称")
    private String lessonName;
    /**
     * score
     * 分数
     */
    @ApiModelProperty(value="分数")
    private BigDecimal score;
}
