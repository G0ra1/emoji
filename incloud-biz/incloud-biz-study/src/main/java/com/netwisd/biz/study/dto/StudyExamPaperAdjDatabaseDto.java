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
 * @Description 试卷调整题库 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Data
@Map("incloud_biz_study_exam_paper_adj_database")
@ApiModel(value = "试卷调整题库 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyExamPaperAdjDatabaseDto extends IDto{

    /**
     * paper_id
     * 试卷id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_exam_paper_adj" ,field = "id")
    @ApiModelProperty(value="试卷id")
    private Long paperId;
    /**
     * database_id
     * 题库id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
