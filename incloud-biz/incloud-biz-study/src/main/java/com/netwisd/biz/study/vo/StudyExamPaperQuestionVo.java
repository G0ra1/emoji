package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 试卷和题目结果 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 15:59:53
 */
@Data
@ApiModel(value = "试卷和题目结果 Vo")
public class StudyExamPaperQuestionVo extends IVo{

    /**
     * paper_id
     * 试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * database_id
     * 题库id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="题目id")
    private Long questionId;

    /**
     * question
     * 题干
     */
    @ApiModelProperty(value="题干")
    private String question;

    /**
     * question_code
     * 题目类型;0单选题，1多选题，2填空题，3判断题，4简答题
     */
    
    @ApiModelProperty(value="题目类型;0单选题，1多选题，2填空题，3判断题，4简答题")
    private Integer questionCode;
    /**
     * grade
     * 题目难度;0易，1中，2难
     */
    
    @ApiModelProperty(value="题目难度;0易，1中，2难")
    private Integer grade;

    /**
     * parse
     * 解析
     */
    @ApiModelProperty(value="解析")
    private String parse;

    /**
     * question_score
     * 题目分数
     */
    
    @ApiModelProperty(value="题目分数")
    private BigDecimal questionScore;

    @ApiModelProperty(value="排序号")
    private Integer sortNumber;
    /**
     * answer
     * 选项 多个选项逗号隔开
     */
    @ApiModelProperty(value="选项 多个选项逗号隔开")
    private List answers;
}
