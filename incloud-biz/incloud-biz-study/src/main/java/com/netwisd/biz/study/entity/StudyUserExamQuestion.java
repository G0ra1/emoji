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
 * @Description $人员考试题目表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@Table(value = "incloud_biz_study_user_exam_question",comment = "人员考试题目表")
@TableName("incloud_biz_study_user_exam_question")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员考试题目表 Entity")
public class StudyUserExamQuestion extends IModel<StudyUserExamQuestion> {

    /**
     * user_exam_id
     * 人员试卷id
     */
    @ApiModelProperty(value="人员试卷id")
    @TableField(value="user_exam_id")
    @Column(type = DataType.BIGINT, length = 20, fkTableName = "incloud_biz_study_user_exam" ,fkFieldName = "id" , isNull = true, comment = "人员试卷id")
    private Long userExamId;
    /**
     * sort_number
     * 题目序号
     */
    @ApiModelProperty(value="题目序号")
    @TableField(value="sort_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "题目序号")
    private Integer sortNumber;
    /**
     * question_id
     * 题目ID
     */
    @ApiModelProperty(value="题目ID")
    @TableField(value="question_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "题目ID")
    private Long questionId;
    /**
     * question
     * 题干
     */
    @ApiModelProperty(value="题干")
    @TableField(value="question")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题干")
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
     * 难度
     */
    @ApiModelProperty(value="难度")
    @TableField(value="grade")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "难度")
    private Integer grade;
    /**
     * question_code
     * 题目类型
     */
    @ApiModelProperty(value="题目类型")
    @TableField(value="question_code")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "题目类型")
    private Integer questionCode;
    /**
     * question_score
     * 分值
     */
    @ApiModelProperty(value="分值")
    @TableField(value="question_score")
    @Column(type = DataType.DECIMAL, length = 3, precision = 1 , isNull = true, comment = "分值")
    private BigDecimal questionScore;
    /**
     * is_correct
     * 是否答对;(0否；1是)
     */
    @ApiModelProperty(value="是否答对;(0否；1是)")
    @TableField(value="is_correct")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "是否答对;(0否；1是)")
    private Integer isCorrect;
    /**
     * score
     * 得分
     */
    @ApiModelProperty(value="得分")
    @TableField(value="score")
    @Column(type = DataType.DECIMAL, length = 3, precision = 1 , isNull = true, comment = "得分")
    private BigDecimal score;
    /**
     * user_answer
     * 学员做题答案
     */
    @ApiModelProperty(value="学员做题答案")
    @TableField(value="user_answer")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "学员做题答案")
    private String userAnswer;

}
