package com.netwisd.base.wf.entity;

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
 * @Description $流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
@Data
@Table(value = "incloud_base_wf_form_fields_def",comment = "流程表单字段定义")
@TableName("incloud_base_wf_form_fields_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程表单字段定义 Entity")
public class WfFormFieldsDef extends IModel<WfFormFieldsDef> {
    /**
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "表单ID")
    private Long formId;

    /**
     * form_name
     * 表单code
     */
    @ApiModelProperty(value="表单code")
    @TableField(value="form_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "表单code")
    private String formName;

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda流程定义key")
    private String camundaProcdefKey;

    /**
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    @TableField(value="camunda_node_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda节点ID")
    private String camundaNodeDefId;

    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "节点ID")
    private Long nodeDefId;

    /**
     * java_name
     * 字段名(驼峰)
     */
    @ApiModelProperty(value="字段名(驼峰)")
    @TableField(value="java_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "字段名(驼峰)")
    private String javaName;

    /**
     * power_code
     * 字段权限标识
     */
    @ApiModelProperty(value="字段权限标识")
    @TableField(value="power_code")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "字段权限标识")
    private String powerCode;

    /**
     * java_type
     * Java类型
     */
    @ApiModelProperty(value="Java类型")
    @TableField(value="java_type")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "Java类型")
    private String javaType;

    /**
     * name_ch
     * 字段中文名称
     */
    @ApiModelProperty(value = "字段中文名称")
    @TableField(value = "name_ch")
    @Column(type = DataType.VARCHAR,comment = "字段中文名称", isNull = true)
    private String nameCh;

    /**
     * db_type
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    @TableField(value = "db_type")
    @Column(type = DataType.VARCHAR,length = 50, comment = "数据库类型")
    private String dbType;

    /**
     * is_more_row
     * 是否多行
     */
    @ApiModelProperty(value="是否多行")
    @TableField(value="is_more_row")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "是否多行")
    private Integer isMoreRow;

    /**
     * is_orm
     * 是否映射为变量
     */
    @ApiModelProperty(value="是否映射为变量")
    @TableField(value="is_orm")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "是否映射为变量")
    private Integer isOrm;

    /**
     * model_field_id
     * 模型字段id
     */
    @ApiModelProperty(value="模型字段id")
    @TableField(value="model_field_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "模型字段id")
    private Long modelFieldId;
}
