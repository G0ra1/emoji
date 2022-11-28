package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@Data
@ApiModel(value = "课程评分表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonScoreDto extends IDto{

    public StudyLessonScoreDto(Args args){
        super(args);
    }
    /**
     * lesson_id
     * 课程主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
