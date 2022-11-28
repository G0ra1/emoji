package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import com.netwisd.biz.study.vo.StudyUserExamQuestionVo;
import com.netwisd.biz.study.vo.StudyUserExamQuestionDetailVo;
/**
 * @Description 人员考试 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@ApiModel(value = "人员考试 Vo")
public class StudyUserExamVo extends IVo{

    /**
     * user_id
     * 用户id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="用户id")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private String userName;
    /**
     * special_id
     * 专题id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="课程id")
    private Long lessonId;
    /**
     * paper_id
     * 试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * paper_code
     * 出题类型;0固定试卷，1随机试卷
     */
    @ApiModelProperty(value="出题类型;0固定试卷，1随机试卷")
    private Integer paperCode;
    /**
     * paper_status
     * 试卷状态;0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）
     */
    @ApiModelProperty(value="试卷状态;0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）")
    private Integer paperStatus;
    /**
     * paper_type
     * 试卷分类;0练习题，1考试题
     */
    @ApiModelProperty(value="试卷分类;0练习题，1考试题")
    private String paperType;
    /**
     * paper_name
     * 试卷名称;试卷带的
     */
    @ApiModelProperty(value="试卷名称;试卷带的")
    private String paperName;
    /**
     * paper_total_score
     * 试卷总分数;试卷带的
     */
    @ApiModelProperty(value="试卷总分数;试卷带的")
    private BigDecimal paperTotalScore;

    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value="专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * exam_score
     * 考试分数;学员考试的分数/ 老师阅卷评分
     */
    @ApiModelProperty(value="考试分数;学员考试的分数/ 老师阅卷评分")
    private BigDecimal examScore;
    /**
     * remark
     * 评语;（阅卷老师填写）
     */
    @ApiModelProperty(value="评语;（阅卷老师填写）")
    private String remark;
    /**
     * exam_time
     * 考试时长;分钟
     */
    @ApiModelProperty(value="考试时长;分钟")
    private Integer specialExamTime;

    /**
     * paper_start_time
     * 考试开始时间;（年月日时分）
     */
    @ApiModelProperty(value="考试开始时间;（年月日时分）专题带入")
    private LocalDateTime paperStartTime;

    /**
     * paper_end_time
     * 考试结束时间;（年月日时分）
     */
    @ApiModelProperty(value="考试结束时间;（年月日时分）专题带入")
    private LocalDateTime paperEndTime;

    /**
     * exam_level
     * 考试状态;0不合格；1合格
     */
    @ApiModelProperty(value="考试状态;0不合格；1合格")
    private Integer examLevel;
    /**
     * hand_time
     * 交卷时间;与是否交卷（为是）同步设置
     */
    @ApiModelProperty(value="交卷时间;与是否交卷（为是）同步设置")
    private LocalDateTime handTime;
    /**
     * marking_id
     * 阅卷老师Id;后台设置
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="阅卷老师Id;后台设置")
    private Long markingId;
    /**
     * marking_name
     * 阅卷老师名称;后台设置
     */
    @ApiModelProperty(value="阅卷老师名称;后台设置")
    private String markingName;
    /**
     * marking_time
     * 阅卷时间;老师阅卷完成时设置
     */
    @ApiModelProperty(value="阅卷时间;老师阅卷完成时设置")
    private LocalDateTime markingTime;
    /**
     * answer_start_time
     * 答题开始时间;点击开始考试时设置
     */
    @ApiModelProperty(value="答题开始时间;点击开始考试时设置")
    private LocalDateTime answerStartTime;
    /**
     * answer_end_time
     * 答题结束时间;保存时设置
     */
    @ApiModelProperty(value="答题结束时间;保存时设置")
    private LocalDateTime answerEndTime;

    /**
     * isSubmit
     * 是否提交(0否；1是)
     */
    @ApiModelProperty(value="是否提交(0否；1是)")
    private Integer isSubmit;

    @ApiModelProperty(value="人员考试题目集合")
    private List<StudyUserExamQuestionVo> questionList;


}
