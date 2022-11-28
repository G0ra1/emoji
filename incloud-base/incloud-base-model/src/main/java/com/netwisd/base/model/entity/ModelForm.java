package com.netwisd.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "incloud_base_model_form", comment = "数据建模表单")
@TableName("incloud_base_model_form")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模表单 Entity")
public class ModelForm extends IModel<ModelForm> {

    /**
     * form_name
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "form_name")
    @Column(length = 100, isNull = false, comment = "表单名称")
    private String formName;

    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "form_name_ch")
    @Column(length = 100, isNull = false, comment = "表单名称")
    private String formNameCh;

    /**
     * form_property
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型")
    @TableField(value = "form_type")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "表单类型")
    private Integer formType;

    /**
     * form_content
     * 表单内容
     */
    @ApiModelProperty(value = "表单内容")
    @TableField(value = "form_content")
    @Column(type = DataType.TEXT, comment = "表单内容")
    private String formContent;

    /**
     * modeling_id
     * 模型Id
     */
    @ApiModelProperty(value = "模型Id")
    @TableField(value = "modeling_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "模型Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelingId;

    /**
     * status
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    @Column(type = DataType.INT, length = 1, comment = "状态")
    private Integer status;

    /**
     * model_type_id
     * 模型分类ID
     */
    @ApiModelProperty(value = "模型分类ID")
    @TableField(value = "model_type_id")
    @Column(type = DataType.BIGINT, isNull = false, comment = "模型分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelTypeId;

    /**
     * sysName
     * 模型分类code
     */
    @ApiModelProperty(value = "模型分类code")
    @TableField(value = "model_type_code")
    @Column(length = 100, isNull = false, comment = "模型分类code")
    private String modelTypeCode;

    /**
     * model_type_name
     * 模型分类名称
     */
    @ApiModelProperty(value = "模型分类名称")
    @TableField(value = "model_type_name")
    @Column(length = 100, isNull = false, comment = "模型分类名称")
    private String modelTypeName;

    @ApiModelProperty(value = "表单保存URL")
    @TableField(value = "form_save_url")
    @Column(length = 1024, comment = "表单保存URL")
    private String formSaveUrl;

    @ApiModelProperty(value = "表单删除URL")
    @TableField(value = "form_delete_url")
    @Column(length = 1024, comment = "表单保存URL")
    private String formDeleteUrl;

    @ApiModelProperty(value = "表单更新URL")
    @TableField(value = "form_update_url")
    @Column(length = 1024, comment = "表单保存URL")
    private String formUpdateUrl;

    @ApiModelProperty(value = "表单获取URL")
    @TableField(value = "form_get_url")
    @Column(length = 1024, comment = "表单保存URL")
    private String formGetUrl;

    @ApiModelProperty(value = "表单保存URL类型")
    @TableField(value = "form_save_method_type")
    @Column(length = 128, comment = "表单保存URL类型")
    private String formSaveMethodType;

    @ApiModelProperty(value = "表单删除URL类型")
    @TableField(value = "form_delete_method_type")
    @Column(length = 128, comment = "表单删除URL类型")
    private String formDeleteMethodType;

    @ApiModelProperty(value = "表单更新URL类型")
    @TableField(value = "form_update_method_type")
    @Column(length = 128, comment = "表单更新URL类型")
    private String formUpdateMethodType;

    @ApiModelProperty(value = "表单获取URL类型")
    @TableField(value = "form_get_method_type")
    @Column(length = 128, comment = "表单获取URL类型")
    private String formGetMethodType;

    @ApiModelProperty(value = "外联表单地址")
    @TableField(value = "page_url")
    @Column(length = 1024, comment = "外联表单地址")
    private String pageUrl;
}
