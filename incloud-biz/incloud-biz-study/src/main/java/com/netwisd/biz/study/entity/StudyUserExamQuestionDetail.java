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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $人员考试题目子表-详情表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@Table(value = "incloud_biz_study_user_exam_question_detail",comment = "人员考试题目子表-详情表")
@TableName("incloud_biz_study_user_exam_question_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员考试题目子表-详情表 Entity")
public class StudyUserExamQuestionDetail extends IModel<StudyUserExamQuestionDetail> {

    /**
     * user_exam_question
     * 人员考试题目表id
     */
    @ApiModelProperty(value="人员考试题目表id")
    @TableField(value="user_exam_question_id")
    @Column(type = DataType.BIGINT, length = 20, fkTableName = "incloud_biz_study_user_exam_question" ,fkFieldName = "id" , isNull = true, comment = "人员考试题目表id")
    private Long userExamQuestionId;
    /**
     * sort
     * 选项排序
     */
    @ApiModelProperty(value="选项排序")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "选项排序")
    private Integer sort;


    /**
     * answer
     * 题目答案/选项
     */
    @ApiModelProperty(value="题目答案/选项")
    @TableField(value="answer")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "题目答案/选项")
    private String answer;
    /**
     * is_true
     * 是否正确答案;0错误;1正确
     */
    @ApiModelProperty(value="是否正确答案;0错误;1正确")
    @TableField(value="is_true")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "是否正确答案;0错误;1正确")
    private Integer isTrue;


}
