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
 * @Description $流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@Data
@Table(value = "incloud_base_wf_button_def",comment = "流程定义-按钮")
@TableName("incloud_base_wf_button_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-按钮 Entity")
public class WfButtonDef extends IModel<WfButtonDef> {

    @ApiModelProperty(value="按钮code")
    @TableField(value="button_code")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "按钮code")
    private String buttonCode;
    /**
     * button_name
     * 按钮name
     */
    @ApiModelProperty(value="按钮name")
    @TableField(value="button_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "按钮name")
    private String buttonName;
    /**
     * node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义Id")
    private Long nodeDefId;
    /**
     * procdef_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "流程定义id")
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
}
