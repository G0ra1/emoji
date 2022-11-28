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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
@Data
@Table(value = "incloud_base_wf_var_def",comment = "流程定义-变量")
@TableName("incloud_base_wf_var_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-变量 Entity")
public class WfVarDef extends IModel<WfVarDef> {
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;
    /**
     * model_field_id
     * 模型字段id
     */
    @ApiModelProperty(value="模型字段id")
    @TableField(value="model_field_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "模型字段id")
    private Long modelFieldId;
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
     * action_scope
     * 作用域 0全局 1局部
     */
    @ApiModelProperty(value="作用域 0全局 1局部")
    @TableField(value="action_scope")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "作用域 0全局 1局部")
    private Integer actionScope;
    /**
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义ID")
    private Long nodeDefId;
    /**
     * sequ_def_id
     * 序列流定义id
     */
    @ApiModelProperty(value="序列流定义id")
    @TableField(value="sequ_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "序列流定义id")
    private Long sequDefId;

    /**
     * camunda_node_def_id
     * camunda节点定义ID
     */
    @ApiModelProperty(value="camunda节点定义ID")
    @TableField(value="camunda_node_def_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda节点定义ID")
    private String camundaNodeDefId;

    /**
     * camunda_sequ_def_id
     * camunda序列列定义id
     */
    @ApiModelProperty(value="camunda序列列定义id")
    @TableField(value="camunda_sequ_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda序列列定义id")
    private String camundaSequDefId;

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
     * java_name
     * 字段名(驼峰)
     */
    @ApiModelProperty(value="字段名(驼峰)")
    @TableField(value="java_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "字段名(驼峰)")
    private String javaName;
    /**
     * expre_Java_Name
     * 表达式映射变量
     */
    @ApiModelProperty(value="表达式映射变量")
    @TableField(value="expre_Java_Name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "表达式映射变量")
    private String expreJavaName;
    /**
     * name_ch
     * 字段中文名称
     */
    @ApiModelProperty(value = "字段中文名称")
    @TableField(value = "name_ch")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true,comment = "字段中文名称")
    private String nameCh;
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
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "是否映射为变量")
    private Integer isOrm;
}
