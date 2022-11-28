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
 * @Description $课程历史学习资料 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Data
@Table(value = "incloud_biz_study_lesson_his_marterials", comment = "课程历史学习资料")
@TableName("incloud_biz_study_lesson_his_marterials")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程历史学习资料 Entity")
public class StudyLessonHisMarterials extends IModel<StudyLessonHisMarterials> {

    /**
     * lesson_his_id
     * 课程历史id
     */
    @ApiModelProperty(value = "课程历史id")
    @TableField(value = "lesson_his_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课程历史id")
    private Long lessonHisId;
    /**
     * marterials_id
     * 学习资料id
     */
    @ApiModelProperty(value = "学习资料id")
    @TableField(value = "marterials_id")
    @Column(type = DataType.BIGINT, length = 19, isNull = false, comment = "学习资料id")
    private Long marterialsId;
    /**
     * marterials_name
     * 学习资料名称
     */
    @ApiModelProperty(value = "学习资料名称")
    @TableField(value = "marterials_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "学习资料名称")
    private String marterialsName;
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
     * is_download
     * 是否允许下载 0否 1是
     */
    @ApiModelProperty(value = "是否允许下载 0否 1是")
    @TableField(value = "is_download")
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "是否允许下载 0否 1是")
    private Integer isDownload;

}
