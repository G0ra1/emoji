package com.netwisd.biz.study.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@Data
@ApiModel(value = "用户学习记录表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyUserStudyRecordsDto extends IDto{

    /**
     * special_id
     * 专题主键
     */
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
    @ApiModelProperty(value="最低学习时长（秒）")
    private Long studyBestLessTime;

    /**
     * cumulative_study_time
     * 累计学习时长（秒）
     */
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
    @ApiModelProperty(value="最后播放音视频时间节点")
    private Long lastVideoTime;

    /**
     * studyType
     * 学习类型
     */
    @ApiModelProperty(value="学习类型 1 学习中  2 已完成 不传查全部")
    private Integer studyType;

    /**
     * targetType
     * 类型
     */
    @ApiModelProperty(value="类型 0课程 1专题 不传查全部")
    private Integer targetType;
}
