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
 * @Description 专题历史课程与课件表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@ApiModel(value = "专题历史课程与课件表 Vo")
public class StudySpecialHisLessonCouVo extends IVo{

    /**
     * special_lesson_id
     * 专题课程表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题课程表id")
    private Long specialLessonId;
    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="课程id")
    private Long lessonId;
    /**
     * special_id
     * 专题id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * cou_id
     * 课件id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
