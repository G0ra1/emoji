package com.netwisd.biz.study.dto;

import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Data
@ApiModel(value = "问题答案表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyExamQuestionAnswerDto extends IDto{

    /**
     * question_id
     * 所属的题目id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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

}
