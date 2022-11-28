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
 * @Description 题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
@Data
@ApiModel(value = "题目定义 Vo")
public class StudyExamQuestionVo extends IVo{

    /**
     * database_id
     * 题库id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="题库id")
    private Long databaseId;
    /**
     * question
     * 题干
     */
    
    @ApiModelProperty(value="题干")
    private String question;
    /**
     * parse
     * 解析
     */
    
    @ApiModelProperty(value="解析")
    private String parse;

    @ApiModelProperty(value="难度;0易，1中，2难")
    private Integer grade;
    /**
     * question_code
     * 题目类型;0单选题，1多选题，2填空题，3判断题，4简答题
     */
    
    @ApiModelProperty(value="题目类型;0单选题，1多选题，2填空题，3判断题，4简答题")
    private Integer questionCode;
    /**
     * database_name
     * 题库名称
     */
    
    @ApiModelProperty(value="题库名称")
    private String databaseName;

    /**
     * answer
     * 选项 多个选项逗号隔开
     */
    @ApiModelProperty(value="选项 多个选项逗号隔开")
    private List answers;

    /**
     * is_quote
     * 是否引用 0未引用 1引用(题目是否被试卷选中)
     */
    @ApiModelProperty(value="是否引用 0未引用 1引用(题目是否被试卷选中)")
    private Integer isQuote;
 //--------------学员端使用-----------------
    @ApiModelProperty(value="排序号")
    private Integer sortNumber;
    @ApiModelProperty(value="分值")
    private BigDecimal questionScore;
    @ApiModelProperty(value = "学员答题")
    private String userAnswer;

}
