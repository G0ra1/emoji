package com.netwisd.base.wf.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.dto.WfEventParamDto;
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
import java.util.List;

/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
@Data
@ApiModel(value = "按钮维护 Vo")
public class WfEventVo extends IVo{
    /**
     * event_type
     * 事件分类
     */
    @ApiModelProperty(value="事件分类")
    private Integer eventType;
    /**
     * listener_type
     * 监听类型
     */
//    @ApiModelProperty(value="监听类型")
//    private Integer listenerType;
    /**
     * listener_id
     * 监听ID
     */
    @ApiModelProperty(value="监听ID")
    private String listenerId;
    /**
     * listener_name
     * 监听名称
     */
    @ApiModelProperty(value="监听名称")
    private String listenerName;
    /**
     * listener_impl
     * 实现rest或bean
     */
    @ApiModelProperty(value="实现rest或bean")
    private String listenerImpl;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * select_sign
     * 默认是否选中
     */
    @ApiModelProperty(value="默认是否选中")
    private Integer selectSign;
    /**
     * select_must
     * 默认是否强制
     */
    @ApiModelProperty(value="默认是否强制")
    private Integer selectMust;
    /**
     * procdef_type_id
     * 流程分类id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类id")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
    /**
     * 参数列表
     */
    @ApiModelProperty(value="参数列表")
    private List<WfEventParamVo> wfEventParamList;

    /**
     * default_trig_val
     * 默认周期
     */
    @ApiModelProperty(value="默认周期")
    private String defaultTrigVal;

    /**
     * node_event_type
     * 事件的节点类型
     */
    @ApiModelProperty(value="事件的节点类型")
    private String nodeEventType;

    /**
     * sort
     * 排序字段-提供默认事件排序
     */
    @ApiModelProperty(value="排序字段(负数为默认事件排序)")
    private Integer sort;
}
