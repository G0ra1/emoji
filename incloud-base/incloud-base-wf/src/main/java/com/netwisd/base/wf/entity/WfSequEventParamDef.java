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
 * @Description $流程定义-序列流-事件-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:56:16
 */
@Data
@Table(value = "incloud_base_wf_sequ_event_param_def",comment = "流程定义-序列流-事件-参数")
@TableName("incloud_base_wf_sequ_event_param_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-序列流-事件-参数 Entity")
public class WfSequEventParamDef extends IModel<WfSequEventParamDef> {
    /**
     * event_def_id
     * 事件定义ID
     */
    @ApiModelProperty(value="事件定义ID")
    @TableField(value="event_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "事件定义ID")
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
     * 变量
     */
    @ApiModelProperty(value="变量")
    @TableField(value="param_id")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "变量")
    private String paramId;
    /**
     * param_name
     * 变量名称
     */
    @ApiModelProperty(value="变量名称")
    @TableField(value="param_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "变量名称")
    private String paramName;
    /**
     * param_defalut_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    @TableField(value="param_defalut_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "默认值")
    private String paramDefalutValue;

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
