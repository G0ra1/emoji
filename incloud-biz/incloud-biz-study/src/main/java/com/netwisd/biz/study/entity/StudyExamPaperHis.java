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

/**
 * @Description $试卷历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Data
@Table(value = "incloud_biz_study_exam_paper_his",comment = "试卷历史")
@TableName("incloud_biz_study_exam_paper_his")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "试卷历史 Entity")
public class StudyExamPaperHis extends IModel<StudyExamPaperHis> {

    /**
     * link_id
     * 关联原记录ID
     */
    @ApiModelProperty(value="关联原记录ID")
    @TableField(value="link_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "关联原记录ID")
    private Long linkId;
    /**
     * type_code
     * 试卷分类code
     */
    @ApiModelProperty(value="试卷分类code")
    @TableField(value="type_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "试卷分类code")
    private String typeCode;
    /**
     * type_name
     * 试卷分类名称
     */
    @ApiModelProperty(value="试卷分类名称")
    @TableField(value="type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "试卷分类名称")
    private String typeName;
    /**
     * paper_name
     * 试卷名称
     */
    @ApiModelProperty(value="试卷名称")
    @TableField(value="paper_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "试卷名称")
    private String paperName;
    /**
     * paper_code
     * 出题类型;0固定试卷，1随机试卷
     */
    @ApiModelProperty(value="出题类型;0固定试卷，1随机试卷")
    @TableField(value="paper_code")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "出题类型;0固定试卷，1随机试卷")
    private Integer paperCode;
    /**
     * paper_type
     * 试卷类型;0练习题;1考试题
     */
    @ApiModelProperty(value="试卷类型;0练习题;1考试题")
    @TableField(value="paper_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "试卷类型;0练习题;1考试题")
    private String paperType;
    /**
     * paper_total_score
     * 试卷总分
     */
    @ApiModelProperty(value="试卷总分")
    @TableField(value="paper_total_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "试卷总分")
    private BigDecimal paperTotalScore;
    /**
     * special_exam_qualified_score
     * 专题考试合格分数
     */
    @ApiModelProperty(value="专题考试合格分数")
    @TableField(value="special_exam_qualified_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "专题考试合格分数")
    private BigDecimal specialExamQualifiedScore;

    /**
     * is_have_short_answer
     * 试卷中是否包含简答题(0不包含;1包含)
     */
    @ApiModelProperty(value="试卷中是否包含简答题(0不包含;1包含)")
    @TableField(value="is_have_short_answer")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "试卷中是否包含简答题(0不包含;1包含)")
    private Integer isHaveShortAnswer;
    /**
     * special_exam_time
     * 考试时长
     */
    @ApiModelProperty(value="考试时长")
    @TableField(value="special_exam_time")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "考试时长")
    private Integer specialExamTime;
    /**
     * single_score
     * 单选题分值
     */
    @ApiModelProperty(value="单选题分值")
    @TableField(value="single_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "单选题分值")
    private BigDecimal singleScore;
    /**
     * multiple_score
     * 多选题分值
     */
    @ApiModelProperty(value="多选题分值")
    @TableField(value="multiple_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "多选题分值")
    private BigDecimal multipleScore;
    /**
     * completion_score
     * 填空题分值
     */
    @ApiModelProperty(value="填空题分值")
    @TableField(value="completion_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "填空题分值")
    private BigDecimal completionScore;
    /**
     * judgment_score
     * 判断题分值
     */
    @ApiModelProperty(value="判断题分值")
    @TableField(value="judgment_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "判断题分值")
    private BigDecimal judgmentScore;
    /**
     * short_answer_score
     * 简答题分值
     */
    @ApiModelProperty(value="简答题分值")
    @TableField(value="short_answer_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "简答题分值")
    private BigDecimal shortAnswerScore;
    /**
     * single_number
     * 单选题数量
     */
    @ApiModelProperty(value="单选题数量")
    @TableField(value="single_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "单选题数量")
    private Integer singleNumber;
    /**
     * multiple_number
     * 多选题数量
     */
    @ApiModelProperty(value="多选题数量")
    @TableField(value="multiple_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "多选题数量")
    private Integer multipleNumber;
    /**
     * completion_number
     * 填空题数量
     */
    @ApiModelProperty(value="填空题数量")
    @TableField(value="completion_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "填空题数量")
    private Integer completionNumber;
    /**
     * judgment_number
     * 判断题数量
     */
    @ApiModelProperty(value="判断题数量")
    @TableField(value="judgment_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "判断题数量")
    private Integer judgmentNumber;
    /**
     * short_answer_number
     * 简答题数量
     */
    @ApiModelProperty(value="简答题数量")
    @TableField(value="short_answer_number")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "简答题数量")
    private Integer shortAnswerNumber;
}
