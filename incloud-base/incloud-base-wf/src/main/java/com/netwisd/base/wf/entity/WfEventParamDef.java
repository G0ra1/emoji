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
 * @Description $事件定义参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:00:37
 */
@Data
@Table(value = "incloud_base_wf_event_param_def",comment = "事件定义参数维护")
@TableName("incloud_base_wf_event_param_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "事件定义参数维护 Entity")
public class WfEventParamDef extends IModel<WfEventParamDef> {
    /**
     * event_def_id
     * 事件def id
     */
    @ApiModelProperty(value="事件def id")
    @TableField(value="event_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "事件def id")
    private Long eventDefId;
    /**
     * param_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @TableField(value="param_type")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "参数类型")
    private String paramType;
    /**
     * param_id
     * 字典参数id
     */
    @ApiModelProperty(value="字典参数id")
    @TableField(value="param_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "字典参数id")
    private String paramId;

    /**
     * param_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    @TableField(value="param_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "默认值")
    private String paramValue;

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
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义ID")
    private Long nodeDefId;
}
