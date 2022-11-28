package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 试卷题库历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Data
@ApiModel(value = "试卷题库历史 Vo")
public class StudyExamPaperHisDatabaseVo extends IVo{

    /**
     * paper_id
     * 试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * database_id
     * 题题库id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="题题库id")
    private Long databaseId;
    /**
     * database_name
     * 题库名称
     */
    @ApiModelProperty(value="题库名称")
    private String databaseName;
    /**
     * description
     * 题库描述;
     */
    @ApiModelProperty(value="题库描述;")
    private String description;
}
