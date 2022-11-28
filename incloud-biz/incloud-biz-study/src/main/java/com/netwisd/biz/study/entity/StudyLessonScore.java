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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@Data
@Table(value = "incloud_biz_study_lesson_score",comment = "课程评分表")
@TableName("incloud_biz_study_lesson_score")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程评分表 Entity")
public class StudyLessonScore extends IModel<StudyLessonScore> {

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
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课程名称")
    private String lessonName;

    /**
    * score
    * 分数
    */
    @ApiModelProperty(value="分数")
    @TableField(value="score")
    @Column(type = DataType.DECIMAL, length = 2, precision = 1 , isNull = true, comment = "分数")
    private BigDecimal score;

}
