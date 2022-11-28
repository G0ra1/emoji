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
import java.util.List;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Data
@ApiModel(value = "表达式维护 Vo")
public class WfExpreVo extends IVo{
    /**
     * procdef_type_code
     * 流程分类code
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类code")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
    /**
     * expre_name
     * 表达式名称
     */
    @ApiModelProperty(value="表达式名称")
    private String expreName;
    /**
     * expre_value
     * 表达式
     */
    @ApiModelProperty(value="表达式")
    private String expreValue;
    /**
     * expre_return_type
     * 表达式返回类型
     */
    @ApiModelProperty(value="表达式返回类型")
    private Integer expreReturnType;
    /**
     * expre_return_generics
     * 表达式返回泛型
     */
    @ApiModelProperty(value="表达式返回泛型")
    private Integer expreReturnGenerics;
    /**
     * expre_remark
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String expreRemark;

    /**
     * wfExpreParamVoList
     * 参数列表
     */
    @ApiModelProperty(value="参数列表")
    private List<WfExpreParamVo> expreParamList;
}
