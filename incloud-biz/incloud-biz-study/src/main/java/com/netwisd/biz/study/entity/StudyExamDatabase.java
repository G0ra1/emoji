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


/**
 * @Description $题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
@Data
@Table(value = "incloud_biz_study_exam_database",comment = "题库定义")
@TableName("incloud_biz_study_exam_database")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "题库定义 Entity")
public class StudyExamDatabase extends IModel<StudyExamDatabase> {

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
    * 描述
    */
    @ApiModelProperty(value="描述")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 2048,  isNull = true, comment = "描述")
    private String description;

}
