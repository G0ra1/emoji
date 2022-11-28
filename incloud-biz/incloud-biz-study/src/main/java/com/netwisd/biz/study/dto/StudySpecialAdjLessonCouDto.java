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
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 专题调整课程与课件表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Data
@Map("incloud_biz_study_special_adj_lesson_cou")
@ApiModel(value = "专题调整课程与课件表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialAdjLessonCouDto extends IDto{

    public StudySpecialAdjLessonCouDto(Args args){
        super(args);
    }
    /**
     * special_lesson_id
     * 专题课程表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_special_adj_lesson" ,field = "id")
    @ApiModelProperty(value="专题课程表id")
    private Long specialLessonId;
    /**
     * lesson_id
     * 课程id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="课程id")
    private Long lessonId;
    /**
     * special_id
     * 专题id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * cou_id
     * 课件id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="课件id")
    private Long couId;
    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value="课件名称")
    private String couName;
    /**
     * cou_duration
     * 课件时长;;单位：秒《格式化后的》)
     */
    @ApiModelProperty(value="课件时长;;单位：秒《格式化后的》)")
    private String couDuration;
    /**
     * cou_use_range
     * 课件使用权限;0公开;1私有
     */
    @ApiModelProperty(value="课件使用权限;0公开;1私有")
    private Integer couUseRange;
    /**
     * cou_code
     * 课件类型;0文档;1图文类型课件，2图片，3音频，4视频，5链接
     */
    @ApiModelProperty(value="课件类型;0文档;1图文类型课件，2图片，3音频，4视频，5链接")
    private Integer couCode;
    /**
     * cou_is_compulsory
     * 课件是否必修;0:否1:是)
     */
    @ApiModelProperty(value="课件是否必修;0:否1:是)")
    private Integer couIsCompulsory;



}
