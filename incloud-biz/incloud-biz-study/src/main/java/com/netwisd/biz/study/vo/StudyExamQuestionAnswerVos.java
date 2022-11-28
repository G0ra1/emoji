package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 问题答案表 功能描述... 不带isTrue
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Data
@ApiModel(value = "问题答案表 Vo")
public class StudyExamQuestionAnswerVos extends IVo{

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
     * 答案排序
     *
     */
    @ApiModelProperty(value="答案排序")
    private Integer sort;

    /**
     * sort_text
     * 选项排序文本
     */
    @ApiModelProperty(value="选项排序文本")
    private char sortText;

}
