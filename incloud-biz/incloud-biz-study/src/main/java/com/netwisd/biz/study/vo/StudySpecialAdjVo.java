package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题调整申请表 功能描述...
 * @date 2022-05-13 11:27:37
 */
@Data
@ApiModel(value = "专题调整申请表 Vo")
public class StudySpecialAdjVo extends WfVo {

    /**
     * link_id
     * 关联结果表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "关联结果表id")
    private Long linkId;
    /**
     * label
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String label;
    /**
     * label_code
     * 标签code
     */
    @ApiModelProperty(value = "标签code")
    private String labelCode;
    /**
     * type_code
     * 分类code
     */
    @ApiModelProperty(value = "分类code")
    private String typeCode;
    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String typeName;
    /**
     * special_name
     * 培训专题名称
     */
    @ApiModelProperty(value = "培训专题名称")
    private String specialName;

    /**
     * description
     * 简介（描述）
     */
    @ApiModelProperty(value="简介（描述）")
    private String description;

    /**
     * special_time_type
     * 专题时间类型;;专题时间类型(0普通培训，1长期培训)
     */
    @ApiModelProperty(value = "专题时间类型;;专题时间类型(0普通培训，1长期培训)")
    private Integer specialTimeType;
    /**
     * special_start_time
     * 专题开始时间
     */
    @ApiModelProperty(value = "专题开始时间")
    private LocalDateTime specialStartTime;
    /**
     * special_end_time
     * 专题结束时间
     */
    @ApiModelProperty(value = "专题结束时间")
    private LocalDateTime specialEndTime;

    /**
     * special_lecturer
     * 专题讲师名称
     */
    @ApiModelProperty(value = "专题讲师名称")
    private String specialLecturer;

    /**
     * special_paper_id
     * 专题考试试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "专题考试试卷id")
    private Long specialPaperId;
    /**
     * special_paper_name
     * 专题考试试卷名称
     */
    @ApiModelProperty(value = "专题考试试卷名称")
    private String specialPaperName;
    /**
     * special_paper_total_score
     * 专题考试试卷总分
     */
    @ApiModelProperty(value = "专题考试试卷总分")
    private BigDecimal specialPaperTotalScore;
    /**
     * special_paper_start_time
     * 专题考试开始时间;;年月日时分）
     */
    @ApiModelProperty(value = "专题考试开始时间;;年月日时分）")
    private LocalDateTime specialPaperStartTime;
    /**
     * special_paper_end_time
     * 专题考试结束时间;;年月日时分）
     */
    @ApiModelProperty(value = "专题考试结束时间;;年月日时分）")
    private LocalDateTime specialPaperEndTime;
    /**
     * special_paper_type
     * 专题考试试卷类型;0练习题，1考试题;仅考试题）
     */
    @ApiModelProperty(value = "专题考试试卷类型;0练习题，1考试题;仅考试题）")
    private String specialPaperType;
    /**
     * special_exam_time
     * 专题考试时长;单位：分钟
     */
    @ApiModelProperty(value = "专题考试时长;单位：分钟")
    private Integer specialExamTime;

    /**
     * specialExamNum
     * 专题考试次数 默认值 1
     */
    @ApiModelProperty(value = "专题考试次数")
    private Integer specialExamNum;

    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value = "专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * special_exam_paper_teacher_id
     * 专题考试试卷阅卷老师id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "专题考试试卷阅卷老师id")
    private Long specialExamPaperTeacherId;
    /**
     * special_exam_paper_teacher_name
     * 专题考试试卷阅卷老师名称
     */
    @ApiModelProperty(value = "专题考试试卷阅卷老师名称")
    private String specialExamPaperTeacherName;
    /**
     * file_id
     * 文件id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "文件id")
    private Long fileId;
    /**
     * file_url
     * 文件URL
     */
    @ApiModelProperty(value = "文件URL")
    private String fileUrl;
    /**
     * is_have_short_answer
     * 是否包含简答题;0：否1：是
     */
    @ApiModelProperty(value = "是否包含简答题;0：否1：是")
    private Integer isHaveShortAnswer;

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
    //分页返回使用字段
    @ApiModelProperty(value = "专题时间（分页返回使用字段）")
    private String specialTime;

    @ApiModelProperty(value = "专题对象（机构与部门）集合")
    private List<StudySpecialAdjRangeVo> studySpecialRangeOrgDeptList;
    @ApiModelProperty(value = "专题对象（人员）集合")
    private List<StudySpecialAdjRangeVo> studySpecialRangeUserList;
    @ApiModelProperty(value="专题必考人员集合")
    private List<StudySpecialAdjMustExamUserVo> studySpecialMustExamUserList;
    @ApiModelProperty(value = "studySpecialAdjLessonList")
    private List<StudySpecialAdjLessonVo> studySpecialLessonList;
    @ApiModelProperty(value = "studySpecialAdjMaterialsList")
    private List<StudySpecialAdjMaterialsVo> studySpecialMaterialsList;
    @ApiModelProperty(value = "studySpecialAdjJieyeList")
    private List<StudySpecialAdjJieyeVo> studySpecialJieyeList;

}
