package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.dto.WfProcdefTypeDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@Data
@ApiModel(value = "流程分类 Vo")
public class WfProcdefTypeVo extends IVo{
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
    @ApiModelProperty(value="上级节点id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentId;


    @ApiModelProperty(value = "子集")
    private List<WfProcdefTypeVo> kids = new ArrayList<>();
}
