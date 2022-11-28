package com.netwisd.base.wf.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@Data
@ApiModel(value = "流程表单定义 Vo")
public class WfFormDefVo extends IVo{
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * form_id
     * 表单ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表单ID")
    private Long formId;
    /**
     * form_name
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
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
     *
     * 打开表单时对应的表单权限信息
     */
    @ApiModelProperty(value="打开表单时对应的表单权限信息")
    private List<WfFormFieldsDefVo> formVarDefVoList;

    /**
     * page_url
     * 外链表单路径
     */
    @ApiModelProperty(value="外链表单路径")
    private String pageUrl;
}
