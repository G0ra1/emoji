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
 * @Description $流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
@Data
@Table(value = "incloud_base_wf_proc_def_rel",comment = "流程定义和子流程定义关系表")
@TableName("incloud_base_wf_proc_def_rel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义和子流程定义关系表 Entity")
public class WfProcDefRel extends IModel<WfProcDefRel> {

    /**
     * main_camunda_procdef_id
     * 主流程id
     */
    @ApiModelProperty(value="主流程id")
    @TableField(value="main_camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "主流程id")
    private String mainCamundaProcdefId;

    /**
     * main_procdef_name
     * 主流程定义名称
     */
    @ApiModelProperty(value="主流程定义名称")
    @TableField(value="main_procdef_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "main_procdef_name")
    private String mainProcdefName;

    /**
     * main_procdef_id
     * 主流程定义名称
     */
    @ApiModelProperty(value="主流程定义名称")
    @TableField(value="main_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "main_procdef_name")
    private Long mainProcdefId;

    /**
     * child_camunda_procdef_key
     * 子流程key
     */
    @ApiModelProperty(value="子流程key")
    @TableField(value="child_camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "子流程key")
    private String childCamundaProcdefKey;

    /**
     * child_procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    @TableField(value="child_procdef_version")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "流程版本")
    private Integer childProcdefVersion;

    /**
     * child_camunda_procdef_id
     * 子主流程定义id
     */
    @ApiModelProperty(value="子主流程定义id")
    @TableField(value="child_camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "子主流程定义id")
    private String childCamundaProcdefId;

    /**
     * child_procdef_name
     * 子主流程定义名称
     */
    @ApiModelProperty(value="子主流程定义名称")
    @TableField(value="child_procdef_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "子主流程定义名称")
    private String childProcdefName;

    /**
     * child_procdef_id
     * 子流程定义名称
     */
    @ApiModelProperty(value="子流程定义名称")
    @TableField(value="child_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "子流程定义名称")
    private Long childProcdefId;

    /**
     * main_camunda_node_def_id
     * 主流程节点定义id
     */
    @ApiModelProperty(value="main_camunda_node_def_id")
    @TableField(value="main_camunda_node_def_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "")
    private String mainCamundaNodeDefId;

    /**
     * main_node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    @TableField(value="main_node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义Id")
    private Long mainNodeDefId;

    /**
     * main_node_name
     * 主节点名称
     */
    @ApiModelProperty(value="主节点名称")
    @TableField(value="main_node_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "主节点名称")
    private String mainNodeName;


}
