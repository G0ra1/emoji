package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 人员考试题目表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@ApiModel(value = "人员考试题目表 Vo")
public class StudyUserExamQuestionVo extends IVo{

    /**
     * user_exam_id
     * 人员试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="人员试卷id")
    private Long userExamId;
    /**
     * question_number
     * 题目序号
     */
    @ApiModelProperty(value="题目序号")
    private Integer sortNumber;
    /**
     * question_id
     * 题目ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="题目ID")
    private Long questionId;
    /**
     * question_name
     * 题干
     */
    @ApiModelProperty(value="题干")
    private String question;
    /**
     * question_parse
     * 解析
     */
    @ApiModelProperty(value="解析")
    private String parse;
    /**
     * question_grade
     * 难度
     */
    @ApiModelProperty(value="难度")
    private Integer grade;
    /**
     * question_code
     * 题目类型
     */
    @ApiModelProperty(value="题目类型")
    private Integer questionCode;
    /**
     * question_fractions
     * 分值
     */
    @ApiModelProperty(value="分值")
    private BigDecimal questionScore;
    /**
     * is_correct
     * 是否答对;(0否；1是)
     */
    @ApiModelProperty(value="是否答对;(0否；1是)")
    private Integer isCorrect;
    /**
     * score
     * 得分
     */
    @ApiModelProperty(value="得分")
    private BigDecimal score;
    /**
     * user_answer
     * 学员做题答案
     */
    @ApiModelProperty(value="学员做题答案")
    private String userAnswer;

    @ApiModelProperty(value="人员考试题目详情集合")
    private List<StudyUserExamQuestionDetailVo> answers;
}
