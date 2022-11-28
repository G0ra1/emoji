package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@Data
@ApiModel(value = "资源管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmResourceDto extends IDto{

    public MdmResourceDto(Args args){
        super(args);
    }

    /**
     * resource_code
     * 资源编码
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="资源编码")
    private String resourceCode;

    /**
     * resource_name
     * 资源名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="资源名称")
    private String resourceName;

    /**
     * resource_url
     * 资源url
     */
    
    //@Valid(length = 255)
    @ApiModelProperty(value="资源url")
    private String resourceUrl;

    /**
     * resource_type
     * 资源类型
     */
    
    //@Valid(length = 11)
    @ApiModelProperty(value="资源类型")
    private Integer resourceType;

    /**
     * sys_id
     * 子系统ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    //@Valid(length = 20)
    @ApiModelProperty(value="子系统ID")
    private Long sysId;

    /**
     * sys_name
     * 子系统名称
     */
    
    //@Valid(length = 255)
    @ApiModelProperty(value="子系统名称")
    private String sysName;

    /**
     * has_kids
     * 是否有子集
     */
    
    @Valid(length = 1)
    @ApiModelProperty(value="是否有子集")
    private Integer hasKids;

    /**
     * level
     * 层级
     */
    
    @Valid(length = 11)
    @ApiModelProperty(value="层级")
    private Integer level;

    /**
     * parent_id
     * 父级id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="父级id")
    private Long parentId;
    /**
     * parent_name
     * 父级名称
     */
    @Valid(length = 255)
    @ApiModelProperty(value="父级名称")
    private String parentName;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")

    private Integer status;

    /**
     * 图标字段
     */
    //@Valid(length = 255)
    @ApiModelProperty(value="图标")
    private String icon;

    /**
     * 图标字段
     */
    //@Valid(length = 255)
    @ApiModelProperty(value="打开方式")
    private String openWay;

    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @Valid
    private Integer sort;
}
