package com.netwisd.biz.study.vo;


import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "试卷分析 Vo")
public class StudyPaperAnalysisVo extends IVo {

    /**
     * paper_total_score
     * 试卷总分
     */
    @ApiModelProperty(value="试卷总分")
    private BigDecimal paperTotalScore;

    /**
     * paper_pass_score
     * 及格分数
     */
    @ApiModelProperty(value="及格分数")
    private BigDecimal paperPassScore;

    /**
     * exam_time
     * 考试时长
     */
    @ApiModelProperty(value="考试时长")
    private Integer examTime;


    /**
     * highest_score
     * 最高分
     */
    @ApiModelProperty(value="最高分")
    private BigDecimal highestScore;

    /**
     * minimum_score
     * 最低分
     */
    @ApiModelProperty(value="最低分")
    private BigDecimal minimumScore;

    /**
     * average_score
     * 平均分
     */
    @ApiModelProperty(value="平均分")
    private BigDecimal averageScore;

    /**
     * pass_number
     * 及格人数
     */
    @ApiModelProperty(value="及格人数")
    private Integer passNumber;

    /**
     * total_number
     * 总人数
     */
    @ApiModelProperty(value="总人数")
    private Integer totalNumber;

    /**
     * pass_ratio
     * 及格比例
     */
    @ApiModelProperty(value="及格比例")
    private String passRatio;
}
