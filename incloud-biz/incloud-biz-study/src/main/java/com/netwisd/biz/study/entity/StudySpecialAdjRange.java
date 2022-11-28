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
 * @Description $专题调整与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Data
@Table(value = "incloud_biz_study_special_adj_range",comment = "专题调整与对象表")
@TableName("incloud_biz_study_special_adj_range")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题调整与对象表 Entity")
public class StudySpecialAdjRange extends IModel<StudySpecialAdjRange> {

    /**
     * special_id
     * 培训计划id
     */
    @ApiModelProperty(value="培训计划id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_adj" ,fkFieldName = "id" , isNull = true, comment = "培训计划id")
    private Long specialId;
    /**
     * range_type
     * 对象类型
     */
    @ApiModelProperty(value="对象类型")
    @TableField(value="range_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "对象类型")
    private String rangeType;
    /**
     * range_id
     * 对象id
     */
    @ApiModelProperty(value="对象id")
    @TableField(value="range_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "对象id")
    private Long rangeId;
    /**
     * range_name
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    @TableField(value="range_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "对象名称")
    private String rangeName;


}
