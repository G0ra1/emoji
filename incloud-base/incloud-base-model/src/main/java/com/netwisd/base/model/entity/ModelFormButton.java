package com.netwisd.base.model.entity;

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

@Data
@Table(value = "incloud_base_model_form_button", comment = "数据建模表单按钮")
@TableName("incloud_base_model_form_button")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模表单按钮 Entity")
public class ModelFormButton extends IModel<ModelFormButton> {

    /**
     * model_form_id
     * 表单Id
     */
    @ApiModelProperty(value = "表单Id")
    @TableField(value = "model_form_id")
    @Column(length = 20, type = DataType.BIGINT, isNull = false, comment = "表单Id")
    private Long modelFormId;

    /**
     * model_form_name
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "model_form_name")
    @Column(length = 100, isNull = false, comment = "表单名称")
    private String modelFormName;

    /**
     * model_form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "model_form_name_ch")
    @Column(length = 100, isNull = false, comment = "表单名称")
    private String modelFormNameCh;

    /**
     * button_code
     * 按钮Code
     */
    @ApiModelProperty(value = "按钮Code")
    @TableField(value = "button_code")
    @Column(length = 100, isNull = false, comment = "按钮Code")
    private String buttonCode;

    /**
     * button_name
     * 按钮名称
     */
    @ApiModelProperty(value = "按钮名称")
    @TableField(value = "button_name")
    @Column(length = 100, isNull = false, comment = "按钮名称")
    private String buttonName;

    /**
     * button_property
     * 按钮类型
     */
    @ApiModelProperty(value = "按钮类型")
    @TableField(value = "button_type")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "按钮类型")
    private Integer buttonType;

    /**
     * button_url
     * 按钮执行url
     */
    @ApiModelProperty(value = "按钮执行url")
    @TableField(value = "button_url")
    @Column(isNull = false, comment = "按钮执行url")
    private String buttonUrl;

    /**
     * button_exc_method_type
     * 按钮执行方法类型
     */
    @ApiModelProperty(value = "按钮执行方法类型")
    @TableField(value = "button_exc_method_type")
    @Column(isNull = false, comment = "按钮执行方法类型")
    private String buttonExcMethodType;

}
