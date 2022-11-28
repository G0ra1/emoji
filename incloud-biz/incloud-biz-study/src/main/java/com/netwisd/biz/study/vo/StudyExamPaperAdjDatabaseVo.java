package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * @Description 试卷调整题库 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Data
@ApiModel(value = "试卷调整题库 Vo")
public class StudyExamPaperAdjDatabaseVo extends IVo{

    /**
     * paper_id
     * 试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * database_id
     * 题库id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="题库id")
    private Long databaseId;
    /**
     * database_name
     * 题库名称
     */
    @ApiModelProperty(value="题库名称")
    private String databaseName;
    /**
     * description
     * 题库描述
     */
    @ApiModelProperty(value="题库描述")
    private String description;
}
