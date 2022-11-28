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
 * @Description $流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@Data
@Table(value = "incloud_base_wf_sequ_def",comment = "流程定义-序列流")
@TableName("incloud_base_wf_sequ_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-序列流 Entity")
public class WfSequDef extends IModel<WfSequDef> {
    /**
     * camunda_sequ_id
     * camunda序列流ID
     */
    @ApiModelProperty(value="camunda序列流ID")
    @TableField(value="camunda_sequ_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda序列流ID")
    private String camundaSequId;
    /**
     * sequ_name
     * camunda节点名称
     */
    @ApiModelProperty(value="camunda节点名称")
    @TableField(value="sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda节点名称")
    private String sequName;
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
     * expression
     * 网关表达式
     */
    @ApiModelProperty(value="网关表达式")
    @TableField(value="expression")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "网关表达式")
    private String expression;
    /**
     * expression_name
     * 网关表达式名称
     */
    @ApiModelProperty(value="网关表达式名称")
    @TableField(value="expression_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "网关表达式名称")
    private String expressionName;

    /**
     * camunda_parent_node_def_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    @TableField(value="camunda_parent_node_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "父级子流程nodeId")
    private String camundaParentNodeDefId;
}
