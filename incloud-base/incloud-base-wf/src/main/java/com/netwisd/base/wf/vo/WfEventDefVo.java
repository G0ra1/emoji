package com.netwisd.base.wf.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@Data
@ApiModel(value = "事件定义 Vo")
public class WfEventDefVo extends IVo{
    /**
     * event_type
     * 事件分类
     */
    @ApiModelProperty(value="事件分类")
    private Integer eventType;

    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="事件绑定类型")
    private String eventBindType;
    /**
     * event_id
     * 事件字典定义id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="事件字典定义id")
    private Long eventId;
    /**
     * event_submit_sign
     * 完成前事件影响提交标识
     */
    @ApiModelProperty(value="完成前事件影响提交标识")
    private Integer eventSubmitSign;
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;
    /**
     * node_def_id
     * 节点定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="节点定义ID")
    private Long nodeDefId;

    /**
     * event_type_sign
     * 事件所属节点类型(流程定义事件、网关事件、节点事件)
     */
    @ApiModelProperty(value="事件所属节点类型(流程定义事件、网关事件、节点事件)")
    private Integer eventTypeSign;

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
     * netwisd_order
     * 事件顺序
     */
    @ApiModelProperty(value="事件顺序")
    private Integer netwisdOrder;
}
