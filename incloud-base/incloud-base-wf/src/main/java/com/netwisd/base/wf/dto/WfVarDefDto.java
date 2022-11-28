package com.netwisd.base.wf.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
@Data
@ApiModel(value = "流程定义-变量 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfVarDefDto extends IDto{

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * model_field_id
     * 模型字段id
     */
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelFieldId;

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
     * action_scope
     * 作用域 0全局 1局部
     */
    @ApiModelProperty(value="作用域 0全局 1局部")
    @Valid(length = 1) 
    private Integer actionScope;

    /**
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long nodeDefId;

    /**
     * sequ_def_id
     * 序列流定义id
     */
    @ApiModelProperty(value="序列流定义id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long sequDefId;

    /**
     * camunda_node_def_id
     * camunda节点定义ID
     */
    @ApiModelProperty(value="camunda节点定义ID")
    private String camundaNodeDefId;

    /**
     * camundaSequDefId
     * camunda序列列定义Id
     */
    @ApiModelProperty(value="camunda序列列定义Id")
    private String camundaSequDefId;

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
     * java_name
     * 字段名(驼峰)
     */
    @ApiModelProperty(value="字段名(驼峰)")
    private String javaName;

    /**
     * expre_Java_Name
     * 表达式映射变量
     */
    @ApiModelProperty(value="表达式映射变量")
    private String expreJavaName;

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

}
