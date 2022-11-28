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
 * @Description $题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
@Data
@Table(value = "incloud_biz_study_exam_question",comment = "题目定义")
@TableName("incloud_biz_study_exam_question")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "题目定义 Entity")
public class StudyExamQuestion extends IModel<StudyExamQuestion> {

    /**
    * database_id
    * 题库id
    */
    @ApiModelProperty(value="题库id")
    @TableField(value="database_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "题库id")
    private Long databaseId;

    /**
    * question
    * 题干
    */
    @ApiModelProperty(value="题干")
    @TableField(value="question")
    @Column(type = DataType.VARCHAR, length = 2048,  isNull = true, comment = "题干")
    private String question;

    /**
    * parse
    * 解析
    */
    @ApiModelProperty(value="解析")
    @TableField(value="parse")
    @Column(type = DataType.VARCHAR, length = 1024,  isNull = true, comment = "解析")
    private String parse;

    /**
    * grade
    * 难度;0易，1中，2难
    */
    @ApiModelProperty(value="难度;0易，1中，2难")
    @TableField(value="grade")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "难度;0易，1中，2难")
    private Integer grade;

    /**
    * question_code
    * 题目类型;0单选题，1多选题，2填空题，3判断题，4简答题
    */
    @ApiModelProperty(value="题目类型;0单选题，1多选题，2填空题，3判断题，4简答题")
    @TableField(value="question_code")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "题目类型;0单选题，1多选题，2填空题，3判断题，4简答题")
    private Integer questionCode;

    /**
    * database_name
    * 题库名称
    */
    @ApiModelProperty(value="题库名称")
    @TableField(value="database_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题库名称")
    private String databaseName;

    /**
     * is_quote
     * 是否引用 0未引用 1引用(题目是否被试卷选中)
     */
    @ApiModelProperty(value="是否引用 0未引用 1引用(题目是否被试卷选中)")
    @TableField(value="is_quote")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否引用 0未引用 1引用(题目是否被试卷选中)")
    private Integer isQuote;
}
