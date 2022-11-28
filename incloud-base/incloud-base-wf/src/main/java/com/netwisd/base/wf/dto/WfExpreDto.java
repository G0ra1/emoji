package com.netwisd.base.wf.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Data
@ApiModel(value = "表达式维护 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfExpreDto extends IDto{

    /**
     * procdef_type_code
     * 流程分类code
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类code")
    @Valid(length = 20)
    private Long procdefTypeId;

    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @Valid(length = 50) 
    private String procdefTypeName;

    /**
     * expre_name
     * 表达式名称
     */
    @ApiModelProperty(value="表达式名称")
    @Valid(length = 255) 
    private String expreName;

    /**
     * expre_value
     * 表达式
     */
    @ApiModelProperty(value="表达式")
    @Valid(length = 255) 
    private String expreValue;

    /**
     * expre_return_type
     * 表达式返回类型
     */
    @ApiModelProperty(value="表达式返回类型")
    @Valid(length = 2) 
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

    private List<WfExpreParamDto> expreParamList;

}
