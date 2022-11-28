package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程课件表 功能描述...
 * @date 2022-04-19 19:20:24
 */
@Data
@ApiModel(value = "课程课件表 Vo")
public class StudyLessonCouVo extends IVo {

    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课程id")
    private Long lessonId;

    /**
     * cou_id
     * 课件id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课件id")
    private Long couId;

    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码")
    private String labelCode;

    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    /**
     * cou_type
     * 课件分类 1 音频 2 视频 3文档
     */
    @ApiModelProperty(value = "课件分类 1 音频 2 视频 3文档")
    private Integer couType;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value = "课件名称")
    private String couName;

    /**
     * study_best_less_time
     * 最低学习时长（分钟）
     */
    @ApiModelProperty(value="最低学习时长（分钟）")
    private Integer studyBestLessTime;

    /**
     * video_duration
     * 视频时长(分钟)
     */
    @ApiModelProperty(value="视频时长(分钟)")
     private Integer couDuration;

    /**
     * video_duration
     * 视频比率
     */
    @ApiModelProperty(value="视频比率")
     private BigDecimal couRatio;

    /**
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    private Integer useRange;

    /**
     * orgPermList
     * 课程课件机构部门授权
     */
    @ApiModelProperty(value = "课程课件机构部门授权")
    private List<StudyLessonCouPermVo> orgPermList;

    /**
     * userPermList
     * 课程课件人员授权
     */
    @ApiModelProperty(value = "课程课件人员授权")
    private List<StudyLessonCouPermVo> userPermList;
}
