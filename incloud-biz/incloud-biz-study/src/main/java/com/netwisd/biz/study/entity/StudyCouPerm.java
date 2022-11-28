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
 * @author 云数网讯 lhy@netwisd.com
 * @Description $课件授权表 功能描述...
 * @date 2022-04-19 19:13:08
 */
@Data
@Table(value = "incloud_biz_study_cou_perm", comment = "课件授权表")
@TableName("incloud_biz_study_cou_perm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课件授权表 Entity")
public class StudyCouPerm extends IModel<StudyCouPerm> {

    /**
     * cou_id
     * 课件主键
     */
    @ApiModelProperty(value = "课件主键")
    @TableField(value = "cou_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课件主键")
    private Long couId;

    /**
     * range_type
     * 授权对象类型
     */
    @ApiModelProperty(value = "授权对象类型")
    @TableField(value = "range_type")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "授权对象类型")
    private String rangeType;
    /**
     * range_id
     * 授权对象id
     */
    @ApiModelProperty(value = "授权对象id")
    @TableField(value = "range_id")
    @Column(type = DataType.BIGINT, length = 19, isNull = true, comment = "授权对象id")
    private Long rangeId;
    /**
     * range_name
     * 授权对象名称
     */
    @ApiModelProperty(value = "授权对象名称")
    @TableField(value = "range_name")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "授权对象名称")
    private String rangeName;

}
