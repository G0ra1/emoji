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
 * @Description $流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
@Data
@Table(value = "incloud_base_wf_expre_sequ_param_def",comment = "流程定义-序列流-表达式-参数")
@TableName("incloud_base_wf_expre_sequ_param_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-序列流-表达式-参数 Entity")
public class WfExpreSequParamDef extends IModel<WfExpreSequParamDef> {
    /**
     * expre_param_id
     * 表达式参数ID
     */
    @ApiModelProperty(value="表达式参数ID")
    @TableField(value="expre_param_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "表达式参数ID")
    private Long expreParamId;
    /**
     * expre_param_value
     * 表达式或过程变量或表单对应的参数值
     */
    @ApiModelProperty(value="表达式或过程变量或表单对应的参数值")
    @TableField(value="expre_param_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "表达式或过程变量或表单对应的参数值")
    private String expreParamValue;
    /**
     * expre_param_source
     * 参数来源
     */
    @ApiModelProperty(value="参数来源")
    @TableField(value="expre_param_source")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "参数来源")
    private Integer expreParamSource;
    /**
     * expre_sequ_def_id
     * 序列流 表达式def id
     */
    @ApiModelProperty(value="序列流 表达式def id")
    @TableField(value="expre_sequ_def_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "序列流 表达式def id")
    private Long expreSequDefId;

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

}
