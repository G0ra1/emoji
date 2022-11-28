package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 人员考试题目子表-详情表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Data
@Map("incloud_biz_study_user_exam_question_detail")
@ApiModel(value = "人员考试题目子表-详情表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyUserExamQuestionDetailDto extends IDto{

    public StudyUserExamQuestionDetailDto(Args args){
        super(args);
    }
    /**
     * user_exam_question
     * 人员考试题目表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_user_exam_question" ,field = "id")
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
    private String sortText;

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
