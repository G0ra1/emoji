package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 人员考试题目子表-详情表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@ApiModel(value = "人员考试题目子表-详情表 Vo")
public class StudyUserExamQuestionDetailVo extends IVo{

    /**
     * user_exam_question
     * 人员考试题目表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="人员考试题目表id")
    private Long userExamQuestionId;
    /**
     * sort
     * 选项排序
     */
    @ApiModelProperty(value="选项排序")
    private Integer sort;

    /**
     * sort_text
     * 选项排序文本
     */
    @ApiModelProperty(value="选项排序文本")
    private char sortText;

    /**
     * answer
     * 题目答案/选项
     */
    @ApiModelProperty(value="题目答案/选项")
    private String answer;
    /**
     * is_true
     * 是否正确答案;0错误;1正确
     */
    @ApiModelProperty(value="是否正确答案;0错误;1正确")
    private Integer isTrue;


}
