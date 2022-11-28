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
 * @Description 试卷调整题目 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Data
@Map("incloud_biz_study_exam_paper_adj_question")
@ApiModel(value = "试卷调整题目 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyExamPaperAdjQuestionDto extends IDto{

    public StudyExamPaperAdjQuestionDto(Args args){
        super(args);
    }
    /**
     * paper_id
     * 试卷id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_exam_paper_adj" ,field = "id")
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * database_id
     * 题库id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="题库id")
    private Long databaseId;
    /**
     * database_name
     * 题库名称
     */
    @ApiModelProperty(value="题库名称")
    private String databaseName;
    /**
     * question_id
     * 题目id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="题目id")
    private Long questionId;
    /**
     * question_code
     * 题目类型;0单选题;1多选题，2填空题，3判断题，4简答题
     */
    @ApiModelProperty(value="题目类型;0单选题;1多选题，2填空题，3判断题，4简答题")
    private Integer questionCode;
    /**
     * question_score
     * 题目分数
     */
    @ApiModelProperty(value="题目分数")
    private BigDecimal questionScore;
    /**
     * question
     * 题干
     */
    @ApiModelProperty(value="题干")
    private String question;

    /**
     * sort_number
     * 排序号
     */
    @ApiModelProperty(value="排序号")
    private Integer sortNumber;

    /**
     * grade
     * 题目难度;0易;1中，2难
     */
    @ApiModelProperty(value="题目难度;0易;1中，2难")
    private Integer grade;
}
