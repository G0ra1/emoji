package com.netwisd.base.wf.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@Data
@ApiModel(value = "流程表单定义 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfFormDefDto extends IDto{

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @Valid(length = 64) 
    private String camundaProcdefId;

    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @Valid(length = 50) 
    private String camundaProcdefKey;

    /**
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @Valid(length = 100)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long formId;

    /**
     * form_name
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    @Valid(length = 100) 
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
    private String camundaNodeDefId;

    /**
     * node_def_id
     * 节点定义ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="节点定义ID")
    private Long nodeDefId;

    /**
     * 表单对应的字段信息
     */
    @ApiModelProperty(value="表单对应的字段信息")
    private List<WfFormFieldsDefDto> wfFormFieldsDefDtoList;

    /**
     * 表单对应的映射变量信息
     */
    @ApiModelProperty(value="表单对应的映射变量信息")
    private List<WfVarDefDto> wfVarDefDtoList;

    /**
     * page_url
     * 外链表单路径
     */
    @ApiModelProperty(value="外链表单路径")
    private String pageUrl;

}
