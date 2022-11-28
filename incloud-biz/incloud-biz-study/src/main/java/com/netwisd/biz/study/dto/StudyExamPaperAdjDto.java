package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.netwisd.common.db.anntation.Map;


/**
 * @Description 试卷调整申请 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Data
@Map("incloud_biz_study_exam_paper_adj")
@ApiModel(value = "试卷调整申请 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyExamPaperAdjDto extends WfDto {

    /**
     * link_id
     * 关联原记录ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="关联原记录ID")
    private Long linkId;

    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;
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
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value="专题考试合格分数")
     private BigDecimal specialExamQualifiedScore;

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
     * paper_total_score
     * 试卷总分
     */
    @ApiModelProperty(value="试卷总分")
    private BigDecimal paperTotalScore;
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
    /**
     * audit_submit_time
     * 审批提交时间
     */
    @ApiModelProperty(value="审批提交时间")
    private LocalDateTime auditSubmitTime;
    /**
     * audit_success_time
     * 审核通过时间
     */
    @ApiModelProperty(value="审核通过时间")
    private LocalDateTime auditSuccessTime;

    @ApiModelProperty(value="studyExamPaperAdjQuestionList")
    private List<StudyExamPaperAdjQuestionDto> studyExamPaperApplyQuestionList;
    @ApiModelProperty(value="studyExamPaperAdjDatabaseList")
    private List<StudyExamPaperAdjDatabaseDto> studyExamPaperApplyDatabaseList;

}
