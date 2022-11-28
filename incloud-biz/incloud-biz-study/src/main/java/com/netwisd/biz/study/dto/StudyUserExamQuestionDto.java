package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 人员考试题目表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@Map("incloud_biz_study_user_exam_question")
@ApiModel(value = "人员考试题目表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyUserExamQuestionDto extends IDto{

    public StudyUserExamQuestionDto(Args args){
        super(args);
    }
    /**
     * user_exam_id
     * 人员试卷id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_user_exam" ,field = "id")
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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

    @ApiModelProperty(value="人员考试题目详情表")
    private List<StudyUserExamQuestionDetailDto> answers;

}
