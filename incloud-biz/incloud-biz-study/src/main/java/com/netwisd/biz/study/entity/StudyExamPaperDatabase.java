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
 * @Description $试卷申请题库结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:36:55
 */
@Data
@Table(value = "incloud_biz_study_exam_paper_database",comment = "试卷申请题库结果表")
@TableName("incloud_biz_study_exam_paper_database")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "试卷申请题库结果表 Entity")
public class StudyExamPaperDatabase extends IModel<StudyExamPaperDatabase> {

    /**
    * paper_id
    * 试卷id
    */
    @ApiModelProperty(value="试卷id")
    @TableField(value="paper_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "试卷id")
    private Long paperId;

    /**
    * database_id
    * 题库id
    */
    @ApiModelProperty(value="题库id")
    @TableField(value="database_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "题库id")
    private Long databaseId;

    /**
    * database_name
    * 题库名称
    */
    @ApiModelProperty(value="题库名称")
    @TableField(value="database_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题库名称")
    private String databaseName;

    /**
    * description
    * 题库描述
    */
    @ApiModelProperty(value="题库描述")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题库描述")
    private String description;

}
