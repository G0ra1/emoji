package com.netwisd.base.wf.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@Data
@ApiModel(value = "流程分类 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfProcdefTypeDto extends IDto{

    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    
    private String procdefTypeName;

    /**
     * procdef_type_code
     * 流程分类code
     */
//    @ApiModelProperty(value="流程分类code")
//    private String procdefTypeCode;

    /**
     * is_enable
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    private Integer isEnable;

    /**
     * parentId
     * 上级节点id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="上级节点id")
    private Long parentId;

}
