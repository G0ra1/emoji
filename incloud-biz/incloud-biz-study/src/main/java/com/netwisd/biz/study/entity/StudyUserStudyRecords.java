package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description $用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@Data
@Table(value = "incloud_biz_study_user_study_records",comment = "用户学习记录表")
@TableName("incloud_biz_study_user_study_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户学习记录表 Entity")
public class StudyUserStudyRecords extends IModel<StudyUserStudyRecords> {

    /**
     * special_id
     * 专题主键
     */
    @ApiModelProperty(value="专题主键")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "专题主键")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value="专题名称")
    @TableField(value="special_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "专题名称")
    private String specialName;

    /**
     * lesson_id
     * 课程主键
     */
    @ApiModelProperty(value="课程主键")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "课程主键")
    private Long lessonId;

    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value="课程名称")
    @TableField(value="lesson_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "课程名称")
    private String lessonName;

    /**
     * cou_id
     * 课件主键
     */
    @ApiModelProperty(value="课件主键")
    @TableField(value="cou_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "课件主键")
    private Long couId;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value="课件名称")
    @TableField(value="cou_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "课件名称")
    private String couName;

    /**
    * user_id
    * 用户主键
    */
    @ApiModelProperty(value="用户主键")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "用户主键")
    private Long userId;

    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户中文名称")
    private String userNameCh;

    /**
     * user_org_full_name
     * 用户父级组织全路径名称
     */
    @ApiModelProperty(value="用户父级组织全路径名称")
    @TableField(value="user_org_full_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户父级组织全路径名称")
    private String userOrgFullName;

    /**
     * study_best_less_time
     * 最低学习时长（分钟）
     */
    @ApiModelProperty(value="最低学习时长（秒）")
    @TableField(value="study_best_less_time")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "最低学习时长（秒）")
    private Long studyBestLessTime;

    /**
     * cumulative_study_time
     * 累计学习时长（秒）
     */
    @ApiModelProperty(value="累计学习时长（秒）")
    @TableField(value="cumulative_study_time")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "累计学习时长（秒）")
    private Long cumulativeStudyTime;

    /**
     * last_study_time
     * 最后学习时间
     */
    @ApiModelProperty(value="最后学习时间")
    @TableField(value="last_study_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "最后学习时间")
    private LocalDateTime lastStudyTime;

    /**
     * last_video_time
     * 最后播放音视频时间节点
     */
    @ApiModelProperty(value="最后播放音视频时间节点")
    @TableField(value="last_video_time")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "最后播放音视频时间节点")
    private Long lastVideoTime;

}
