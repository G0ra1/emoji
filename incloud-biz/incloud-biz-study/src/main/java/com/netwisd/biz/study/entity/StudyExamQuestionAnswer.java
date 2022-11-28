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

/**
 * @Description $问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Data
@Table(value = "incloud_biz_study_exam_question_answer",comment = "问题答案表")
@TableName("incloud_biz_study_exam_question_answer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "问题答案表 Entity")
public class StudyExamQuestionAnswer extends IModel<StudyExamQuestionAnswer> {

    /**
    * question_id
    * 所属的题目id
    */
    @ApiModelProperty(value="所属的题目id")
    @TableField(value="question_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属的题目id")
    private Long questionId;

    /**
    * answer
    * 答案内容
    */
    @ApiModelProperty(value="答案内容")
    @TableField(value="answer")
    @Column(type = DataType.VARCHAR, length = 2048,  isNull = true, comment = "答案内容")
    private String answer;

    /**
    * is_true
    * 是否正确答案 0错误，1正确
    */
    @ApiModelProperty(value="是否正确答案 0错误，1正确")
    @TableField(value="is_true")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否正确答案 0错误，1正确")
    private Integer isTrue;

    /**
     * sort
     * 答案排序
     */
    @ApiModelProperty(value="答案排序")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "答案排序")
    private Integer sort;

}
