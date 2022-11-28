package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
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
 * @Description $按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
@Data
@Table(value = "incloud_base_wf_event",comment = "按钮维护")
@TableName("incloud_base_wf_event")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "按钮维护 Entity")
public class WfEvent extends IModel<WfEvent> {

    /**
     * event_type
     * 事件分类
     */
    @ApiModelProperty(value="事件分类")
    @TableField(value="event_type")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "事件分类")
    private Integer eventType;

    /**
     * listener_id
     * 监听ID
     */
    @ApiModelProperty(value="监听ID")
    @TableField(value="listener_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "监听ID")
    private String listenerId;
    /**
     * listener_name
     * 监听名称
     */
    @ApiModelProperty(value="监听名称")
    @TableField(value="listener_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "监听名称")
    private String listenerName;
    /**
     * listener_impl
     * 实现rest或bean
     */
    @ApiModelProperty(value="实现rest或bean")
    @TableField(value="listener_impl")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "实现rest或bean")
    private String listenerImpl;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
    /**
     * select_sign
     * 默认是否选中
     */
    @ApiModelProperty(value="默认是否选中")
    @TableField(value="select_sign")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "默认是否选中")
    private Integer selectSign;
    /**
     * select_must
     * 默认是否强制
     */
    @ApiModelProperty(value="默认是否强制")
    @TableField(value="select_must")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "默认是否强制")
    private Integer selectMust;
    /**
     * procdef_type_id
     * 流程分类id
     */
    @ApiModelProperty(value="流程分类id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程分类id")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "流程分类名称")
    private String procdefTypeName;

    /**
     * default_trig_val
     * 默认周期
     */
    @ApiModelProperty(value="默认周期")
    @TableField(value="default_trig_val")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "默认周期")
    private String defaultTrigVal;

    /**
     * node_event_type
     * 事件的节点类型
     */
    @ApiModelProperty(value="事件的节点类型")
    @TableField(value="node_event_type")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = false, comment = "事件的节点类型")
    private String nodeEventType;

    /**
     * sort
     * 排序字段-提供默认事件排序
     */
    @ApiModelProperty(value="排序字段(负数为默认事件排序)")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "负数为默认事件排序")
    private Integer sort;
}
