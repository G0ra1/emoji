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
 * @Description $事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@Data
@Table(value = "incloud_base_wf_event_def",comment = "事件定义")
@TableName("incloud_base_wf_event_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "事件定义 Entity")
public class WfEventDef extends IModel<WfEventDef> {

    /**
     * event_type
     * 事件分类
     */
    @ApiModelProperty(value="事件分类")
    @TableField(value="event_type")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "事件分类")
    private Integer eventType;

    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="默认周期")
    @TableField(value="event_bind_type")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "默认周期")
    private String eventBindType;
    /**
     * event_id
     * 事件 字典 定义id
     */
    @ApiModelProperty(value="事件字典定义id")
    @TableField(value="event_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "事件字典定义id")
    private Long eventId;
    /**
     * event_submit_sign
     * 完成前事件影响提交标识
     */
    @ApiModelProperty(value="完成前事件影响提交标识")
    @TableField(value="event_submit_sign")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "完成前事件影响提交标识")
    private Integer eventSubmitSign;
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;
    /**
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义ID")
    private Long nodeDefId;

    /**
     * event_type_sign
     * 事件所属节点类型(流程定义事件、网关事件、节点事件)
     */
    @ApiModelProperty(value="事件所属节点类型(流程定义事件、网关事件、节点事件)")
    @TableField(value="event_type_sign")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "事件所属节点类型(流程定义事件、网关事件、节点事件、)")
    private Integer eventTypeSign;

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
     * netwisd_order
     * 事件顺序
     */
    @ApiModelProperty(value="事件顺序")
    @TableField(value="netwisd_order")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "事件顺序")
    private Integer netwisdOrder;
}
