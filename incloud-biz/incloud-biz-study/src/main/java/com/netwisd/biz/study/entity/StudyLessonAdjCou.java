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

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description $课程调整课件 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Data
@Table(value = "incloud_biz_study_lesson_adj_cou", comment = "课程调整课件")
@TableName("incloud_biz_study_lesson_adj_cou")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程调整课件 Entity")
public class StudyLessonAdjCou extends IModel<StudyLessonAdjCou> {

    /**
     * lesson_adj_id
     * 课程调整表id
     */
    @ApiModelProperty(value = "课程id")
    @TableField(value = "lesson_adj_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课程调整表id")
    private Long lessonAdjId;
    /**
     * cou_id
     * 课件id
     */
    @ApiModelProperty(value = "课件id")
    @TableField(value = "cou_id")
    @Column(type = DataType.BIGINT, length = 19, isNull = true, comment = "课件id")
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
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "课件分类 1 音频 2 视频 3文档")
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
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    @TableField(value="use_range")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "使用权限 1 公开 2 私有")
    private Integer useRange;

}
