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
 * @Description $流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@Data
@Table(value = "incloud_base_wf_form_def",comment = "流程表单定义")
@TableName("incloud_base_wf_form_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程表单定义 Entity")
public class WfFormDef extends IModel<WfFormDef> {
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
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
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 100,  isNull = false, comment = "表单ID")
    private Long formId;
    /**
     * form_name
     * 表单code
     */
    @ApiModelProperty(value="表单code")
    @TableField(value="form_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "表单code")
    private String formName;
    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    @TableField(value="form_name_ch")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "表单名称")
    private String formNameCh;
    /**
     * camunda_node_def_id
     * camunda节点定义ID
     */
    @ApiModelProperty(value="camunda节点定义ID")
    @TableField(value="camunda_node_def_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda节点定义ID")
    private String camundaNodeDefId;

    /**
     * node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    @TableField(value="node_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "节点定义Id")
    private Long nodeDefId;

//    /**
//     * page_url  不存了 实时取model
//     * 外链表单路径
//     */
//    @ApiModelProperty(value="外链表单路径")
//    @TableField(value="page_url")
//    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "外链表单路径")
//    private String pageUrl;
}
