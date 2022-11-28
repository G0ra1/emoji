package com.netwisd.biz.study.dto;


import com.netwisd.base.wf.starter.dto.WfDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 试卷结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:32:00
 */
@Data
@ApiModel(value = "试卷结果表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyExamPaperDto extends WfDto {

    /**
     * type_code
     * 试卷分类code
     */
    @ApiModelProperty(value="试卷分类code")
    private String typeCode;

    /**
     * type_name
     * 试卷分类名称
     */
    @ApiModelProperty(value="试卷分类名称")
    private String typeName;

    /**
     * paper_name
     * 试卷名称
     */
    @ApiModelProperty(value="试卷名称")
    private String paperName;

    /**
     * paper_code
     * 出题类型 0固定试卷，1随机试卷
     */
    @ApiModelProperty(value="出题类型 0固定试卷，1随机试卷")
    private Integer paperCode;

    /**
     * paper_type
     * 试卷类型;0练习题，1考试题
     */
    @ApiModelProperty(value="试卷类型;0练习题，1考试题")
    private String paperType;

    /**
     * paper_total_score
     * 试卷总分
     */
    @ApiModelProperty(value="试卷总分")
    private BigDecimal paperTotalScore;

    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value="专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * status
     * 状态0:未生效1:已生效2:调整中
     */
    @ApiModelProperty(value = "状态0:未生效1:已生效2:调整中")
    private Integer status;

    /**
     * audit_submit_time
     * 审批提交时间
     */
    @ApiModelProperty(value = "审批提交时间")
    private LocalDateTime auditSubmitTime;
    /**
     * audit_success_time
     * 审核通过时间
     */
    @ApiModelProperty(value = "审核通过时间")
    private LocalDateTime auditSuccessTime;

    @ApiModelProperty(value="题目集合")
    private List<StudyExamPaperQuestionDto> studyExamPaperApplyQuestionList;

    @ApiModelProperty(value="题库集合")
    private List<StudyExamPaperDatabaseDto> studyExamPaperApplyDatabaseList;

    /**
     * single_score
     * 单选题分值
     */
    @ApiModelProperty(value="单选题分值")
    private BigDecimal singleScore;

    /**
     * multiple_score
     * 多选题分值
     */
    @ApiModelProperty(value="多选题分值")
    private BigDecimal multipleScore;

    /**
     * completion_score
     * 填空题分值
     */
    @ApiModelProperty(value="填空题分值")
    private BigDecimal completionScore;

    /**
     * judgment_score
     * 判断题分值
     */
    @ApiModelProperty(value="判断题分值")
    private BigDecimal judgmentScore;

    /**
     * short_answer_score
     * 简答题分值
     */
    @ApiModelProperty(value="简答题分值")
    private BigDecimal shortAnswerScore;

    /**
     * single_number
     * 单选题数量
     */
    @ApiModelProperty(value="单选题数量")
    private Integer singleNumber;

    /**
     * multiple_number
     * 多选题数量
     */
    @ApiModelProperty(value="多选题数量")
    private Integer multipleNumber;

    /**
     * completion_number
     * 填空题数量
     */
    @ApiModelProperty(value="填空题数量")
    private Integer completionNumber;

    /**
     * judgment_number
     * 判断题数量
     */
    @ApiModelProperty(value="判断题数量")
    private Integer judgmentNumber;

    /**
     * short_answer_number
     * 简答题数量
     */
    @ApiModelProperty(value="简答题数量")
    private Integer shortAnswerNumber;

    /**
     * is_enable
     * 是否启用 0禁用,1启用
     */
    @ApiModelProperty(value="是否启用 0禁用,1启用")
    private Integer isEnable;

    /**
     * is_have_short_answer
     * 试卷中是否有简答题
     */
    @ApiModelProperty(value="试卷中是否有简答题")
    private Integer isHaveShortAnswer;

    /**
     * special_exam_time
     * 考试时长
     */
    @ApiModelProperty(value="考试时长")
    private Integer specialExamTime;

}
