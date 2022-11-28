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
 * @Description $表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Data
@Table(value = "incloud_base_wf_expre",comment = "表达式维护")
@TableName("incloud_base_wf_expre")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "表达式维护 Entity")
public class WfExpre extends IModel<WfExpre> {
    /**
     * procdef_type_id
     * 流程分类code
     */
    @ApiModelProperty(value="流程分类Id")
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 50,  isNull = false, comment = "流程分类Id")
    private String procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程分类名称")
    private String procdefTypeName;
    /**
     * expre_name
     * 表达式名称
     */
    @ApiModelProperty(value="表达式名称")
    @TableField(value="expre_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "表达式名称")
    private String expreName;
    /**
     * expre_value
     * 表达式
     */
    @ApiModelProperty(value="表达式")
    @TableField(value="expre_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "表达式")
    private String expreValue;
    /**
     * expre_return_type
     * 表达式返回类型
     */
    @ApiModelProperty(value="表达式返回类型")
    @TableField(value="expre_return_type")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "表达式返回类型")
    private Integer expreReturnType;

    /**
     * expre_return_generics
     * 表达式返回泛型
     */
    @ApiModelProperty(value="表达式返回泛型")
    @TableField(value="expre_return_generics")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "表达式返回泛型")
    private Integer expreReturnGenerics;
    /**
     * expre_remark
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="expre_remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String expreRemark;
}
