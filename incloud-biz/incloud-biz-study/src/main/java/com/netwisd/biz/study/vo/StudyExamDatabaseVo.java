package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;



/**
 * @Description 题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
@Data
@ApiModel(value = "题库定义 Vo")
public class StudyExamDatabaseVo extends IVo{

    /**
     * database_name
     * 题库名称
     */
    
    @ApiModelProperty(value="题库名称")
    private String databaseName;

    /**
     * description
     * 描述
     */
    
    @ApiModelProperty(value="描述")
    private String description;

    @ApiModelProperty(value="题目集合")
    private List<StudyExamQuestionVo> studyExamQuestionDefList;

}
