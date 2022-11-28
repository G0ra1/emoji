package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $专题定义表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2022-04-20 17:09:09
 */
@Data
@Table(value = "incloud_biz_study_special",comment = "专题定义表")
@TableName("incloud_biz_study_special")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题定义表 Entity")
public class StudySpecial extends WfEntity<StudySpecial> {

    /**
    * label
    * 标签
    */
    @ApiModelProperty(value="标签")
    @TableField(value="label")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "标签")
    private String label;

    /**
    * label_code
    * 标签code
    */
    @ApiModelProperty(value="标签code")
    @TableField(value="label_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "标签code")
    private String labelCode;

    /**
    * type_code
    * 分类code
    */
    @ApiModelProperty(value="分类code")
    @TableField(value="type_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类code")
    private String typeCode;

    /**
    * type_name
    * 分类名称
    */
    @ApiModelProperty(value="分类名称")
    @TableField(value="type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类名称")
    private String typeName;

    /**
    * special_name
    * 培训专题名称
    */
    @ApiModelProperty(value="培训专题名称")
    @TableField(value="special_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "培训专题名称")
    private String specialName;

    /**
     * description
     * 简介（描述）
     */
    @ApiModelProperty(value="简介（描述）")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "简介（描述）")
    private String description;

    /**
    * special_time_type
    * 专题时间类型;(专题时间类型(0普通培训，1长期培训)
    */
    @ApiModelProperty(value="专题时间类型;(专题时间类型(0普通培训，1长期培训)")
    @TableField(value="special_time_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "专题时间类型;(专题时间类型(0普通培训，1长期培训)")
    private Integer specialTimeType;

    /**
    * special_start_time
    * 专题开始时间
    */
    @ApiModelProperty(value="专题开始时间")
    @TableField(value="special_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题开始时间")
    private LocalDateTime specialStartTime;

    /**
     * special_lecturer
     * 专题讲师名称
     */
    @ApiModelProperty(value = "专题讲师名称")
    @TableField(value="special_lecturer")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题讲师名称")
    private String specialLecturer;

    /**
    * special_end_time
    * 专题结束时间
    */
    @ApiModelProperty(value="专题结束时间")
    @TableField(value="special_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题结束时间")
    private LocalDateTime specialEndTime;

    /**
    * special_paper_id
    * 专题考试试卷id
    */
    @ApiModelProperty(value="专题考试试卷id")
    @TableField(value="special_paper_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "专题考试试卷id")
    private Long specialPaperId;

    /**
    * special_paper_name
    * 专题考试试卷名称
    */
    @ApiModelProperty(value="专题考试试卷名称")
    @TableField(value="special_paper_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题考试试卷名称")
    private String specialPaperName;

    /**
    * special_paper_total_score
    * 专题考试试卷总分
    */
    @ApiModelProperty(value="专题考试试卷总分")
    @TableField(value="special_paper_total_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "专题考试试卷总分")
    private BigDecimal specialPaperTotalScore;

    /**
    * special_paper_start_time
    * 专题考试开始时间;（年月日时分）
    */
    @ApiModelProperty(value="专题考试开始时间;（年月日时分）")
    @TableField(value="special_paper_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题考试开始时间;（年月日时分）")
    private LocalDateTime specialPaperStartTime;

    /**
    * special_paper_end_time
    * 专题考试结束时间;（年月日时分）
    */
    @ApiModelProperty(value="专题考试结束时间;（年月日时分）")
    @TableField(value="special_paper_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题考试结束时间;（年月日时分）")
    private LocalDateTime specialPaperEndTime;

    /**
    * special_paper_type
    * 专题考试试卷类型;0练习题，1考试题（仅考试题）
    */
    @ApiModelProperty(value="专题考试试卷类型;0练习题，1考试题（仅考试题）")
    @TableField(value="special_paper_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题考试试卷类型;0练习题，1考试题（仅考试题）")
    private String specialPaperType;

    /**
    * special_exam_time
    * 专题考试时长;单位：分钟
    */
    @ApiModelProperty(value="专题考试时长;单位：分钟")
    @TableField(value="special_exam_time")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "专题考试时长;单位：分钟")
    private Integer specialExamTime;

    /**
     * specialExamNum
     * 专题考试次数 默认值 1
     */
    @ApiModelProperty(value = "专题考试次数")
    @TableField(value="special_exam_num")
    @Column(type = DataType.DECIMAL, length = 11,  isNull = true, comment = "专题考试次数")
    private Integer specialExamNum;

    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value = "专题考试合格分数")
    @TableField(value="special_exam_qualified_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * is_have_short_answer
     * 是否包含简答题;0：否1：是
     */
    @ApiModelProperty(value = "是否包含简答题;0：否1：是")
    @TableField(value = "is_have_short_answer")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "是否包含简答题;0：否1：是")
    private Integer isHaveShortAnswer;
    /**
    * special_exam_paper_teacher_id
    * 专题考试试卷阅卷老师id
    */
    @ApiModelProperty(value="专题考试试卷阅卷老师id")
    @TableField(value="special_exam_paper_teacher_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "专题考试试卷阅卷老师id")
    private Long specialExamPaperTeacherId;

    /**
    * special_exam_paper_teacher_name
    * 专题考试试卷阅卷老师名称
    */
    @ApiModelProperty(value="专题考试试卷阅卷老师名称")
    @TableField(value="special_exam_paper_teacher_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题考试试卷阅卷老师名称")
    private String specialExamPaperTeacherName;

    /**
    * file_id
    * 文件id
    */
    @ApiModelProperty(value="文件id")
    @TableField(value="file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "文件id")
    private Long fileId;



    /**
    * file_url
    * 文件URL
    */
    @ApiModelProperty(value="文件URL")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 3000,  isNull = true, comment = "文件URL")
    private String fileUrl;

    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    @TableField(value="hits")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "点击量")
    private Long hits;
    /**
     * is_index
     * 是否首页展示;0 不展示 1展示
     */
    @ApiModelProperty(value="是否首页展示;0 不展示 1展示")
    @TableField(value="is_index")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否首页展示;0 不展示 1展示")
    private Integer isIndex;

    /**
     * is_enable
     * 是否启用 0禁用 1启用
     */
    @ApiModelProperty(value="是否启用;0禁用 1启用")
    @TableField(value="is_enable")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否启用;0禁用 1启用")
    private Integer isEnable;

    /**
     * status
     * 状态(0:未生效1:已生效2:调整中)
     */
    @ApiModelProperty(value="状态(0:未生效1:已生效2:调整中)")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "状态(0:未生效1:已生效2:调整中)")
    private Integer status;


    /**
     * audit_submit_time
     * 审批提交时间
     */
    @ApiModelProperty(value="审批提交时间")
    @TableField(value="audit_submit_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批提交时间")
    private LocalDateTime auditSubmitTime;
    /**
     * audit_success_time
     * 审核通过时间
     */
    @ApiModelProperty(value="审核通过时间")
    @TableField(value="audit_success_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审核通过时间")
    private LocalDateTime auditSuccessTime;



}
