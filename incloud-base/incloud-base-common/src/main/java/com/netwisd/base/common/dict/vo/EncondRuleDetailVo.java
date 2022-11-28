package com.netwisd.base.common.dict.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@ApiModel(value = "编码规则详情 Vo")
public class EncondRuleDetailVo extends IVo{

    /**
     * rule_id
     * 规则ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
