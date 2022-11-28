package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@Data
@ApiModel(value = "用户学习记录表 Vo")
public class StudyUserStudyRecordsVo extends IVo{

    /**
     * special_id
     * 专题主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题主键")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value="专题名称")
    private String specialName;

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
     * cou_id
     * 课件主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="课件主键")
    private Long couId;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value="课件名称")
    private String couName;

    /**
     * user_id
     * 用户主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="用户主键")
    private Long userId;

    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;

    /**
     * user_org_full_name
     * 用户父级组织全路径名称
     */
    @ApiModelProperty(value="用户父级组织全路径名称")
    private String userOrgFullName;

    /**
     * study_best_less_time
     * 最低学习时长（分钟）
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="最低学习时长（秒）")
    private Long studyBestLessTime;

    /**
     * cumulative_study_time
     * 累计学习时长（秒）
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="累计学习时长（秒）")
    private Long cumulativeStudyTime;

    /**
     * last_study_time
     * 最后学习时间
     */
    @ApiModelProperty(value="最后学习时间")
    private LocalDateTime lastStudyTime;

    /**
     * lastVideoTime
     * 最后播放音视频时间节点
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="最后播放音视频时间节点")
    private Long lastVideoTime;
}
