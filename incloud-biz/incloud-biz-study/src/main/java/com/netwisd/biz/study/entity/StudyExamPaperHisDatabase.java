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
 * @Description $试卷题库历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Data
@Table(value = "incloud_biz_study_exam_paper_his_database",comment = "试卷题库历史")
@TableName("incloud_biz_study_exam_paper_his_database")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "试卷题库历史 Entity")
public class StudyExamPaperHisDatabase extends IModel<StudyExamPaperHisDatabase> {

    /**
     * paper_id
     * 试卷id
     */
    @ApiModelProperty(value="试卷id")
    @TableField(value="paper_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_exam_paper_his" ,fkFieldName = "id" , isNull = true, comment = "试卷id")
    private Long paperId;
    /**
     * database_id
     * 题题库id
     */
    @ApiModelProperty(value="题题库id")
    @TableField(value="database_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "题题库id")
    private Long databaseId;
    /**
     * database_name
     * 题库名称
     */
    @ApiModelProperty(value="题库名称")
    @TableField(value="database_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题库名称")
    private String databaseName;
    /**
     * description
     * 题库描述;
     */
    @ApiModelProperty(value="题库描述;")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "题库描述;")
    private String description;
}
