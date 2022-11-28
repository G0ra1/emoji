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
 * @author 云数网讯 lhy@netwisd.com
 * @Description $课程学习资料表 功能描述...
 * @date 2022-04-19 19:21:47
 */
@Data
@Table(value = "incloud_biz_study_lesson_marterials", comment = "课程学习资料表")
@TableName("incloud_biz_study_lesson_marterials")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程学习资料表 Entity")
public class StudyLessonMarterials extends IModel<StudyLessonMarterials> {

    /**
     * lesson_id
     * 课程主键
     */
    @ApiModelProperty(value = "课程主键")
    @TableField(value = "lesson_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "课程主键")
    private Long lessonId;

    /**
     * marterials_id
     * 学习资料id
     */
    @ApiModelProperty(value = "学习资料id")
    @TableField(value = "marterials_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "学习资料id")
    private Long marterialsId;

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
     * marterials_name
     * 学习资料名称
     */
    @ApiModelProperty(value = "学习资料名称")
    @TableField(value = "marterials_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "学习资料名称")
    private String marterialsName;

    /**
     * is_download
     * 是否允许下载 0否 1是
     */
    @ApiModelProperty(value = "是否允许下载 0否 1是")
    @TableField(value = "is_download")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "是否允许下载 0否 1是")
    private Integer isDownload;

}
