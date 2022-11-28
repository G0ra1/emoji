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
 * @Description $表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
@Data
@Table(value = "incloud_base_wf_expre_param",comment = "表达式参数维护")
@TableName("incloud_base_wf_expre_param")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "表达式参数维护 Entity")
public class WfExpreParam extends IModel<WfExpreParam> {
    /**
     * param_var_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @TableField(value="param_var_type")
    @Column(type = DataType.VARCHAR, length = 16,  isNull = false, comment = "参数类型")
    private String paramVarType;
    /**
     * param_var_generics
     * 参数泛型
     */
    @ApiModelProperty(value="参数泛型")
    @TableField(value="param_var_generics")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "参数泛型")
    private Integer paramVarGenerics;
    /**
     * param_id
     * 参数ID
     */
    @ApiModelProperty(value="参数ID")
    @TableField(value="param_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "参数ID")
    private String paramId;
    /**
     * param_name
     * 参数名称
     */
    @ApiModelProperty(value="参数名称")
    @TableField(value="param_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "参数名称")
    private String paramName;
    /**
     * expre_id
     * 表达式ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表达式ID")
    @TableField(value="expre_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "表达式ID")
    private Long expreId;
    /**
     * remark
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="param_desc")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String paramDesc;

    /**
     * sequenceNum
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="sequence_num")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "排序")
    private Integer sequenceNum;

    /**
     * is_del
     * 是否可删除(内置的五个参数不可删除)
     */
    @ApiModelProperty(value="是否可删除")
    @TableField(value="is_del")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否可删除(内置的五个参数不可删除)")
    private Integer isDel;
}
