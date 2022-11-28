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
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 课程课件授权历史表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:53:32
 */
@Data
@ApiModel(value = "课程课件授权历史表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonHisCouPermDto extends IDto{

    public StudyLessonHisCouPermDto(Args args){
        super(args);
    }

    /**
     * lesson_adj_id
     * 课程主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="课程主键")
    private Long lessonAdjId;

    /**
     * cou_id
     * 课件主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="授权对象id")
    private Long rangeId;

    /**
     * range_name
     * 授权对象名称
     */
    @ApiModelProperty(value="授权对象名称")
    private String rangeName;

}
