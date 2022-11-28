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
 * @Description $人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
@Data
@Table(value = "incloud_base_wf_expre_user_param_def",comment = "人员表达式参数定义")
@TableName("incloud_base_wf_expre_user_param_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员表达式参数定义 Entity")
public class WfExpreUserParamDef extends IModel<WfExpreUserParamDef> {
    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "节点ID")
    private Long nodeDefId;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @TableField(value="node_type")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "节点类型")
    private Integer nodeType;
    /**
     * expre_param_value
     * 表达式或过程变量或表单对应的参数值
     */
    @ApiModelProperty(value="表达式或过程变量或表单对应的参数值")
    @TableField(value="expre_param_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "表达式或过程变量或表单对应的参数值")
    private String expreParamValue;
    /**
     * expre_param_source
     * 参数来源
     */
    @ApiModelProperty(value="参数来源")
    @TableField(value="expre_param_source")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "参数来源")
    private String expreParamSource;

    /**
     * expre_user_def_id
     * 表达式defID
     */
    @ApiModelProperty(value="表达式defID")
    @TableField(value="expre_user_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "表达式defID")
    private Long expreUserDefId;

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
     * expre_param_var_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @TableField(value="expre_param_var_type")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "参数类型")
    private String expreParamVarType;

    /**
     * form_id
     * 业务表单id
     */
    @ApiModelProperty(value="业务表单id")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "业务表单id")
    private Long formId;

    /**
     * expre_param_name
     * 参数字段名称
     */
    @ApiModelProperty(value="参数字段名称")
    @TableField(value="expre_param_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "参数字段名称")
    private String expreParamName;

    /**
     * expre_param_desc
     * 参数字段描述
     */
    @ApiModelProperty(value="参数字段描述")
    @TableField(value="expre_param_desc")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "参数字段描述")
    private String expreParamDesc;
}
