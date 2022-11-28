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
 * @Description $流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@Data
@Table(value = "incloud_base_wf_expre_sequ_def",comment = "流程定义-序列流-表达式")
@TableName("incloud_base_wf_expre_sequ_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-序列流-表达式 Entity")
public class WfExpreSequDef extends IModel<WfExpreSequDef> {
    /**
     * expre_id
     * 表达式id
     */
    @ApiModelProperty(value="表达式id")
    @TableField(value="expre_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "表达式id")
    private Long expreId;
    /**
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
    @TableField(value="expression")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "表达式的值")
    private String expression;
    /**
     * sequ_def_id
     * def序列流ID
     */
    @ApiModelProperty(value="def序列流ID")
    @TableField(value="sequ_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "def序列流ID")
    private Long sequDefId;
    /**
     * camunda_sequ_id
     * camunda序列流ID
     */
    @ApiModelProperty(value="camunda序列流ID")
    @TableField(value="camunda_sequ_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda序列流ID")
    private String camundaSequId;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * procdef_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "流程定义id")
    private Long procdefId;
}
