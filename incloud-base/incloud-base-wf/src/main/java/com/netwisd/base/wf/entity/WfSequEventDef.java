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
 * @Description $流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@Data
@Table(value = "incloud_base_wf_sequ_event_def",comment = "流程定义-序列流-事件")
@TableName("incloud_base_wf_sequ_event_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-序列流-事件 Entity")
public class WfSequEventDef extends IModel<WfSequEventDef> {
    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="事件绑定类型")
    @TableField(value="event_bind_type")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "事件绑定类型")
    private String eventBindType;
    /**
     * event_id
     * 事件字典定义id
     */
    @ApiModelProperty(value="事件字典定义id")
    @TableField(value="event_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "事件字典定义id")
    private Long eventId;
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;
    /**
     * sequ_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @TableField(value="sequ_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义ID")
    private Long sequDefId;
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
     * camunda_sequ_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    @TableField(value="camunda_sequ_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda节点ID")
    private String camundaSequDefId;
}
