package com.netwisd.base.dict.entity;

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
 * @Description $编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@Table(value = "incloud_base_dict_encond_rule_detail",comment = "编码规则详情")
@TableName("incloud_base_dict_encond_rule_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "编码规则详情 Entity")
public class EncondRuleDetail extends IModel<EncondRuleDetail> {

    /**
     * rule_id
     * 规则ID
     */
    @ApiModelProperty(value="规则ID")
    @TableField(value="rule_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_base_dict_encond_rule" ,fkFieldName = "id" , isNull = true, comment = "规则ID")
    private Long ruleId;
    /**
     * encond_type
     * 编号方式
     */
    @ApiModelProperty(value="编号方式")
    @TableField(value="encond_type")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "编号方式")
    private String encondType;
    /**
     * content
     * 内容
     */
    @ApiModelProperty(value="内容")
    @TableField(value="content")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "内容")
    private String content;
    /**
     * reset_cycle
     * 重置周期
     */
    @ApiModelProperty(value="重置周期")
    @TableField(value="reset_cycle")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "重置周期")
    private String resetCycle;
    /**
     * initial_value
     * 初始值
     */
    @ApiModelProperty(value="初始值")
    @TableField(value="initial_value")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "初始值")
    private String initialValue;
    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "排序")
    private Integer sort;

    /**
     * formName
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    @TableField(value="form_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "表单名称")
    private String formName;

    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "form_name_ch")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "表单名称")
    private String formNameCh;


    /**
     * formId
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "表单ID")
    private Long formId;


    /**
     * form_type
     * 表单名称
     */
    @ApiModelProperty(value = "表单类型")
    @TableField(value = "form_type")
    @Column(type = DataType.VARCHAR, length = 10, isNull = true, comment = "表单类型")
    private String formType;

}
