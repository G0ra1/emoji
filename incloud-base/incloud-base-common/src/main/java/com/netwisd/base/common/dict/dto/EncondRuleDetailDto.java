package com.netwisd.base.common.dict.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@ApiModel(value = "编码规则详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EncondRuleDetailDto extends IDto{

    public EncondRuleDetailDto(Args args){
        super(args);
    }
    /**
     * rule_id
     * 规则ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="规则ID")
    private Long ruleId;
    /**
     * encond_type
     * 编号方式
     */
    @ApiModelProperty(value="编号方式")
    private String encondType;
    /**
     * content
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;
    /**
     * reset_cycle
     * 重置周期
     */
    @ApiModelProperty(value="重置周期")
    private String resetCycle;
    /**
     * initial_value
     * 初始值
     */
    @ApiModelProperty(value="初始值")
    private String initialValue;
    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * formName
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    private String formName;

    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    private String formNameCh;

    /**
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long formId;

    /**
     * form_type
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型")
    private String formType;


}
