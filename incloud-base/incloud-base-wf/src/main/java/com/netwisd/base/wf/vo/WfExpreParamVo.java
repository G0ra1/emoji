package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
@Data
@ApiModel(value = "表达式参数维护 Vo")
public class WfExpreParamVo extends IVo{
    /**
     * param_var_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    private String paramVarType;
    /**
     * param_var_generics
     * 参数泛型
     */
    @ApiModelProperty(value="参数泛型")
    private Integer paramVarGenerics;
    /**
     * param_id
     * 参数ID
     */
    @ApiModelProperty(value="参数ID")
    private String paramId;
    /**
     * param_name
     * 参数名称
     */
    @ApiModelProperty(value="参数名称")
    private String paramName;
    /**
     * expre_id
     * 表达式ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表达式ID")
    private Long expreId;
    /**
     * paramDesc
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String paramDesc;

    /**
     * sequenceNum
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sequenceNum;

    /**
     * is_del
     * 是否不可删除(内置的五个参数不可删除)
     */
    @ApiModelProperty(value="是否可删除")
    private Integer isDel;
}
