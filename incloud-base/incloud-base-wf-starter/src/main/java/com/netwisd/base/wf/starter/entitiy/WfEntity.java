package com.netwisd.base.wf.starter.entitiy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/30 12:41:00
 */
@Data
public class WfEntity<T extends WfEntity<?>> extends IModel<T> {
    @Valid(nullMsg = "工作流参数不能为空！")
    @ApiModelProperty(value="流程相关对象")
    @TableField(value="wf_engine",exist = false)
    private WfEngineDto wfEngine;
    /**
     * camunda_procdef_key
     * 流程定义key
     */
    @ApiModelProperty(value="流程定义key")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @TableField(value="procdef_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "流程定义名称")
    private String procdefName;
    /**
     * camunda_procdef_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程定义ID")
    private String camundaProcdefId;

    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    @TableField(value="procdef_version")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "流程版本")
    private Integer procdefVersion;

    /**
     * camunda_procins_id
     * 流程实例id
     */
    @ApiModelProperty(value="流程实例id")
    @TableField(value="camunda_procins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程实例ID")
    private String camundaProcinsId;

    /**
     * processName
     * 流程实例标题
     */
    @ApiModelProperty(value="流程实例标题")
    @TableField(value="process_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "流程实例名称")
    private String processName;

    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;

    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "bizKey")
    private String bizKey;

    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 50,  isNull = true, comment = "流程分类ID")
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
     * biz_priority
     * 业务优先级
     */
    @TableField(value="biz_priority")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "任务优先级")
    private String bizPriority;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    @TableField(value="audit_status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "审批状态")
    private Integer auditStatus;
}
