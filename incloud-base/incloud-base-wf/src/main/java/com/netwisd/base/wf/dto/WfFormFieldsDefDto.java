package com.netwisd.base.wf.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
@Data
@ApiModel(value = "流程表单字段定义 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfFormFieldsDefDto extends IDto{
    /**
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long formId;

    /**
     * form_name
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    @Valid(length = 100)
    private String formName;

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;

    /**
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaNodeDefId;

    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long nodeDefId;

    /**
     * java_name
     * 字段名(驼峰)
     */
    @ApiModelProperty(value="字段名(驼峰)")
    private String javaName;

    /**
     * power_code
     * 字段权限标识
     */
    @ApiModelProperty(value="字段权限标识")
    private String powerCode;

    /**
     * java_type
     * Java类型
     */
    @ApiModelProperty(value="Java类型")
    private String javaType;
    /**
     * db_type
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    private String dbType;
    /**
     * name_ch
     * 字段中文名称
     */
    @ApiModelProperty(value = "字段中文名称")
    private String nameCh;

    /**
     * is_more_row
     * 是否多行
     */
    @ApiModelProperty(value="是否多行")
    private Integer isMoreRow;

    /**
     * is_orm
     * 是否映射为变量
     */
    @ApiModelProperty(value="是否映射为变量")
    private Integer isOrm;

    /**
     * model_field_id
     * 模型字段id
     */
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelFieldId;



}
