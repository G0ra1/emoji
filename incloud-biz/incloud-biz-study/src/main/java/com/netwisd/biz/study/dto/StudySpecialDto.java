package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Map;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题定义表 功能描述...
 * @date 2022-05-13 10:59:05
 */
@Data
@Map("incloud_biz_study_special")
@ApiModel(value = "专题定义表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialDto extends WfDto {

    public StudySpecialDto(Args args) {
        super(args);
    }

    /**
     * label
     * 标签
     */
    @ApiModelProperty(value = "标签")
    @Valid(nullMsg = "请选择标签")
    private String label;
    /**
     * label_code
     * 标签code
     */
    @ApiModelProperty(value = "标签code")
    @Valid(nullMsg = "请选择标签")
    private String labelCode;
    /**
     * type_code
     * 分类code
     */
    @ApiModelProperty(value = "分类code")
    @Valid(nullMsg = "请选择分类")
    private String typeCode;
    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @Valid(nullMsg = "请选择分类")
    private String typeName;
    /**
     * special_name
     * 培训专题名称
     */
    @ApiModelProperty(value = "培训专题名称")
    @Valid(nullMsg = "请输入专题名称")
    private String specialName;

    /**
     * description
     * 简介（描述）
     */
    @ApiModelProperty(value = "简介（描述）")
    @Valid(nullMsg = "请选择简介（描述）")
    private String description;

    /**
     * special_time_type
     * 专题时间类型;;专题时间类型(0普通培训，1长期培训)
     */
    @ApiModelProperty(value = "专题时间类型;;专题时间类型(0普通培训，1长期培训)")
    @Valid(nullMsg = "请选择专题时间类型")
    private Integer specialTimeType;
    /**
     * special_start_time
     * 专题开始时间
     */
    @ApiModelProperty(value = "专题开始时间")
    @Valid(nullMsg = "请选择专题开始时间")
    private LocalDateTime specialStartTime;
    /**
     * special_end_time
     * 专题结束时间
     */
    @ApiModelProperty(value = "专题结束时间")
    private LocalDateTime specialEndTime;
    /**
     * special_paper_id
     * 专题考试试卷id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "专题考试试卷id")
    private Long specialPaperId;

    /**
     * special_lecturer
     * 专题讲师名称
     */
    @ApiModelProperty(value = "专题讲师名称")
    private String specialLecturer;

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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "请上传封面图")
    @ApiModelProperty(value = "文件id")
    private Long fileId;
    /**
     * file_url
     * 文件URL
     */
    @ApiModelProperty(value = "文件URL")
    @Valid(nullMsg = "请上传封面图")
    private String fileUrl;
    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "点击量")
    private Long hits;
    /**
     * is_index
     * 是否首页展示;0;不展示 1展示
     */
    @ApiModelProperty(value = "是否首页展示;0;不展示 1展示")
    private Integer isIndex;
    /**
     * is_enable
     * 是否启用;0禁用;1启用
     */
    @ApiModelProperty(value = "是否启用;0禁用;1启用")
    private Integer isEnable;
    /**
     * is_have_short_answer
     * 是否包含简答题;0：否1：是
     */
    @ApiModelProperty(value = "是否包含简答题;0：否1：是")
    private Integer isHaveShortAnswer;
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


    @ApiModelProperty(value = "专题对象（机构、部门）集合")
    private List<StudySpecialRangeDto> studySpecialRangeOrgDeptList;
    @ApiModelProperty(value = "专题必考人员集合")
    private List<StudySpecialMustExamUserDto> studySpecialMustExamUserList;
    @ApiModelProperty(value = "专题对象（人员）集合")
    private List<StudySpecialRangeDto> studySpecialRangeUserList;
    @ApiModelProperty(value = "专题结业设置集合")
    private List<StudySpecialJieyeDto> studySpecialJieyeList;
    @ApiModelProperty(value = "专题学习资料集合")
    private List<StudySpecialMaterialsDto> studySpecialMaterialsList;
    @ApiModelProperty(value = "专题课程集合")
    private List<StudySpecialLessonDto> studySpecialLessonList;

    //搜索使用
    @ApiModelProperty(value = "标签code集合")
    private List<String> labelCodeList;


    //排序字段
    @ApiModelProperty(value = "排序字段")
    private String orderByField;

    //排序规则 asc/desc
    @ApiModelProperty(value = "排序规则asc/desc")
    private String orderBySort;

}
