package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.biz.study.entity.StudyExamPaper;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudySpecialLessonDetailVo {
    /**
     * id
     * 主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String lessonName;
    /**
     * special_lecturer
     * 专题讲师名称
     */
    @ApiModelProperty(value = "专题讲师名称")
    private String specialLecturer;
    /**
     * description
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * couSize
     * 课件数量
     */
    @ApiModelProperty(value = "课件数量")
    private Integer couSize;
    /**
     * lessonStudyTime
     * 课程时长(时分秒)
     */
    @ApiModelProperty(value = "课程时长(时分秒)")
    private String lessonStudyTime;
    /**
     * auditSuccessTime
     * 审批成功时间
     */
    @ApiModelProperty(value = "审批成功时间")
    private LocalDateTime auditSuccessTime;
    /**
     * score
     * 课程评分
     */
    @ApiModelProperty(value = "课程评分")
    private BigDecimal score;
    /**
     * isScore
     * 是否评分
     */
    @ApiModelProperty(value = "是否评分")
    private Integer isScore;
    /**
     * couList
     * 课件信息集合
     */
    @ApiModelProperty(value = "课件信息集合")
    private List<StudySpecialLessonCouDetailVo> couList;
    /**
     * marterialsList
     * 学习资料信息集合
     */
    @ApiModelProperty(value = "学习资料信息集合")
    private List<StudyMarterialsVo> marterialsList;
    /**
     * examPaper
     * 练习题
     */
    @ApiModelProperty(value = "练习题")
    private StudyExamPaperVo examPaper;
}
