package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description $人员考试 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@Table(value = "incloud_biz_study_user_exam",comment = "人员考试")
@TableName("incloud_biz_study_user_exam")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员考试 Entity")
public class StudyUserExam extends IModel<StudyUserExam> {

    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "用户id")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户名称")
    private String userName;
    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "专题id")
    private Long specialId;
    /**
     * lesson_id
     * 课程id
     */
    @ApiModelProperty(value="课程id")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "课程id")
    private Long lessonId;
    /**
     * paper_id
     * 试卷id
     */
    @ApiModelProperty(value="试卷id")
    @TableField(value="paper_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "试卷id")
    private Long paperId;
    /**
     * paper_code
     * 出题类型;0固定试卷，1随机试卷
     */
    @ApiModelProperty(value="出题类型;0固定试卷，1随机试卷")
    @TableField(value="paper_code")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "出题类型;0固定试卷，1随机试卷")
    private Integer paperCode;
    /**
     * paper_status
     * 试卷状态;0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）
     */
    @ApiModelProperty(value="试卷状态;0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）")
    @TableField(value="paper_status")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "试卷状态;0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）")
    private Integer paperStatus;

    /**
     * paper_type
     * 试卷分类;0练习题，1考试题
     */
    @ApiModelProperty(value="试卷分类;0练习题，1考试题")
    @TableField(value="paper_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "试卷分类;0练习题，1考试题")
    private String paperType;
    /**
     * paper_name
     * 试卷名称;试卷带的
     */
    @ApiModelProperty(value="试卷名称;试卷带的")
    @TableField(value="paper_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "试卷名称;试卷带的")
    private String paperName;
    /**
     * paper_total_score
     * 试卷总分数;试卷带的
     */
    @ApiModelProperty(value="试卷总分数;试卷带的")
    @TableField(value="paper_total_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "试卷总分数;试卷带的")
    private BigDecimal paperTotalScore;
    /**
     * exam_score
     * 考试分数;学员考试的分数/ 老师阅卷评分
     */
    @ApiModelProperty(value="考试分数;学员考试的分数/ 老师阅卷评分")
    @TableField(value="exam_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "考试分数;学员考试的分数/ 老师阅卷评分")
    private BigDecimal examScore;
    /**
     * remark
     * 评语;（阅卷老师填写）
     */
    @ApiModelProperty(value="评语;（阅卷老师填写）")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "评语;（阅卷老师填写）")
    private String remark;
    /**
     * exam_time
     * 考试时长;秒
     */
    @ApiModelProperty(value="考试时长;分钟")
    @TableField(value="special_exam_time")
    @Column(type = DataType.INT, length = 20,  isNull = true, comment = "考试时长;分钟")
    private Integer specialExamTime;

    /**
     * paper_start_time
     * 考试开始时间;（年月日时分）
     */
    @ApiModelProperty(value="考试开始时间;（年月日时分）")
    @TableField(value="paper_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题考试开始时间;（年月日时分）")
    private LocalDateTime paperStartTime;

    /**
     * paper_end_time
     * 考试结束时间;（年月日时分）
     */
    @ApiModelProperty(value="考试结束时间;（年月日时分）")
    @TableField(value="paper_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "专题考试结束时间;（年月日时分）")
    private LocalDateTime paperEndTime;

    /**
     * exam_level
     * 考试状态;0不合格；1合格
     */
    @ApiModelProperty(value="考试状态;0不合格；1合格")
    @TableField(value="exam_level")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "考试状态;0不合格；1合格")
    private Integer examLevel;
    /**
     * hand_time
     * 交卷时间;与是否交卷（为是）同步设置
     */
    @ApiModelProperty(value="交卷时间;与是否交卷（为是）同步设置")
    @TableField(value="hand_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "交卷时间;与是否交卷（为是）同步设置")
    private LocalDateTime handTime;
    /**
     * marking_id
     * 阅卷老师Id;后台设置
     */
    @ApiModelProperty(value="阅卷老师Id;后台设置")
    @TableField(value="marking_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "阅卷老师Id;后台设置")
    private Long markingId;
    /**
     * marking_name
     * 阅卷老师名称;后台设置
     */
    @ApiModelProperty(value="阅卷老师名称;后台设置")
    @TableField(value="marking_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "阅卷老师名称;后台设置")
    private String markingName;

    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value="专题考试合格分数")
    @TableField(value="special_exam_qualified_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * marking_time
     * 阅卷时间;老师阅卷完成时设置
     */
    @ApiModelProperty(value="阅卷时间;老师阅卷完成时设置")
    @TableField(value="marking_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "阅卷时间;老师阅卷完成时设置")
    private LocalDateTime markingTime;

    /**
     * answer_start_time
     * 答题开始时间;点击开始考试时设置
     */
    @ApiModelProperty(value="答题开始时间;点击开始考试时设置")
    @TableField(value="answer_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "答题开始时间;点击开始考试时设置")
    private LocalDateTime answerStartTime;
    /**
     * answer_end_time
     * 答题结束时间;保存时设置
     */
    @ApiModelProperty(value="答题结束时间;保存时设置")
    @TableField(value="answer_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "答题结束时间;保存时设置")
    private LocalDateTime answerEndTime;

    /**
     * isSubmit
     * 是否提交(0否；1是)
     */
    @ApiModelProperty(value="是否提交(0否；1是)")
    @TableField(value="is_submit")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否提交(0否；1是)")
    private Integer isSubmit;

}
