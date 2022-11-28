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
 * @Description $试卷调整题目 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Data
@Table(value = "incloud_biz_study_exam_paper_adj_question",comment = "试卷调整题目")
@TableName("incloud_biz_study_exam_paper_adj_question")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "试卷调整题目 Entity")
public class StudyExamPaperAdjQuestion extends IModel<StudyExamPaperAdjQuestion> {

    /**
     * paper_id
     * 试卷id
     */
    @ApiModelProperty(value="试卷id")
    @TableField(value="paper_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_exam_paper_adj" ,fkFieldName = "id" , isNull = true, comment = "试卷id")
    private Long paperId;
    /**
     * database_id
     * 题库id
     */
    @ApiModelProperty(value="题库id")
    @TableField(value="database_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "题库id")
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
     * question_id
     * 题目id
     */
    @ApiModelProperty(value="题目id")
    @TableField(value="question_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "题目id")
    private Long questionId;
    /**
     * question_code
     * 题目类型;0单选题;1多选题，2填空题，3判断题，4简答题
     */
    @ApiModelProperty(value="题目类型;0单选题;1多选题，2填空题，3判断题，4简答题")
    @TableField(value="question_code")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "题目类型;0单选题;1多选题，2填空题，3判断题，4简答题")
    private Integer questionCode;
    /**
     * question_score
     * 题目分数
     */
    @ApiModelProperty(value="题目分数")
    @TableField(value="question_score")
    @Column(type = DataType.DECIMAL, length = 3, precision = 1 , isNull = true, comment = "题目分数")
    private BigDecimal questionScore;
    /**
     * question
     * 题干
     */
    @ApiModelProperty(value="题干")
    @TableField(value="question")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题干")
    private String question;
    /**
     * sort_number
     * 排序号
     */
    @ApiModelProperty(value="排序号")
    @TableField(value="sort_number")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "排序号")
    private Integer sortNumber;
    /**
     * grade
     * 题目难度;0易;1中，2难
     */
    @ApiModelProperty(value="题目难度;0易;1中，2难")
    @TableField(value="grade")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "题目难度;0易;1中，2难")
    private Integer grade;
}
