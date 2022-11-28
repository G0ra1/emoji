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

import java.math.BigDecimal;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description $课程课件表 功能描述...
 * @date 2022-04-19 19:20:24
 */
@Data
@Table(value = "incloud_biz_study_lesson_cou", comment = "课程课件表")
@TableName("incloud_biz_study_lesson_cou")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程课件表 Entity")
public class StudyLessonCou extends IModel<StudyLessonCou> {

    /**
     * lesson_id
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    @TableField(value = "lesson_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课程id")
    private Long lessonId;

    /**
     * cou_id
     * 课件id
     */
    @ApiModelProperty(value = "课件id")
    @TableField(value = "cou_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课件id")
    private Long couId;

    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码")
    @TableField(value = "label_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "标签编码")
    private String labelCode;

    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    @TableField(value = "label_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "标签名称")
    private String labelName;

    /**
     * cou_type
     * 课件分类 1 音频 2 视频 3文档
     */
    @ApiModelProperty(value = "课件分类 1 音频 2 视频 3文档")
    @TableField(value = "cou_type")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "课件分类 1 音频 2 视频 3文档")
    private Integer couType;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value = "课件名称")
    @TableField(value = "cou_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "课件名称")
    private String couName;

    /**
     * study_best_less_time
     * 最低学习时长（分钟）
     */
    @ApiModelProperty(value="最低学习时长（分钟）")
    @TableField(value="study_best_less_time")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "最低学习时长（分钟）")
    private Integer studyBestLessTime;

    /**
     * video_duration
     * 视频时长(分钟)
     */
    @ApiModelProperty(value="课件时长(分钟)")
    @TableField(value="cou_duration")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "视频时长(分钟)")
    private Integer couDuration;

    /**
     * video_duration
     * 视频比率
     */
    @ApiModelProperty(value="视频比率")
    @TableField(value="cou_ratio")
    @Column(type = DataType.DECIMAL, length = 10,  isNull = true, comment = "视频比率")
    private BigDecimal couRatio;

    /**
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    @TableField(value="use_range")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "使用权限 1 公开 2 私有")
    private Integer useRange;

}
