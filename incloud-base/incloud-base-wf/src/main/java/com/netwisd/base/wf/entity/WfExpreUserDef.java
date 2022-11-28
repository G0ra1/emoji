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
 * @date 2020-07-14 11:48:32
 */
@Data
@Table(value = "incloud_base_wf_expre_user_def",comment = "人员表达式定义")
@TableName("incloud_base_wf_expre_user_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员表达式定义 Entity")
public class WfExpreUserDef extends IModel<WfExpreUserDef> {
    /**
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "节点定义ID")
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
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
    @TableField(value="expression")
    @Column(type = DataType.VARCHAR, length = 600,  isNull = false, comment = "表达式的值")
    private String expression;

    /**
     * expression_name
     * 表达式名称
     */
    @ApiModelProperty(value="表达式名称")
    @TableField(value="expression_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "表达式名称")
    private String expressionName;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;

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
     * biz_type
     * 业务基础类型
     */
    @ApiModelProperty(value="业务基础类型")
    @TableField(value="biz_type")
    @Column(type = DataType.VARCHAR, length = 16,  isNull = true, comment = "业务基础类型")
    private String bizType;

    /**
     * biz_id
     * 对应业务基础类型id
     */
    @ApiModelProperty(value="对应业务基础类型id")
    @TableField(value="biz_id")
    @Column(type = DataType.VARCHAR, length = 800,  isNull = true, comment = "对应业务基础类型id")
    private String bizId;
}
