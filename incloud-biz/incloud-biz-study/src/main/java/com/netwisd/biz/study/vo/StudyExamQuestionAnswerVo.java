package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Data
@ApiModel(value = "问题答案表 Vo")
public class StudyExamQuestionAnswerVo extends IVo{

    /**
     * question_id
     * 所属的题目id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属的题目id")
    private Long questionId;
    /**
     * answer
     * 答案内容
     */

    @ApiModelProperty(value="答案内容")
    private String answer;
    /**
     * is_true
     * 是否正确答案 0错误，1正确
     */

    @ApiModelProperty(value="是否正确答案 0错误，1正确")
    private Integer isTrue;

    /**
     * 答案排序
     *
     */
    @ApiModelProperty(value="答案排序")
    private Integer sort;

    /**
     * 排序文本
     *
     */
    @ApiModelProperty(value="排序文本")
    private char  sortText;

}
