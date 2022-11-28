package com.netwisd.base.common.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@Data
@ApiModel(value = "资源管理 Vo")
public class MdmResourceVo extends IVo{

    /**
     * resource_code
     * 资源编码
     */
    
    @ApiModelProperty(value="资源编码")
    private String resourceCode;
    /**
     * resource_name
     * 资源名称
     */
    
    @ApiModelProperty(value="资源名称")
    private String resourceName;
    /**
     * resource_url
     * 资源url
     */
    
    @ApiModelProperty(value="资源url")
    private String resourceUrl;
    /**
     * resource_type
     * 资源类型
     */
    
    @ApiModelProperty(value="资源类型")
    private Integer resourceType;
    /**
     * sys_id
     * 子系统ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="子系统ID")
    private Long sysId;
    /**
     * sys_name
     * 子系统名称
     */
    
    @ApiModelProperty(value="子系统名称")
    private String sysName;
    /**
     * has_kids
     * 是否有子集
     */
    
    @ApiModelProperty(value="是否有子集")
    private Integer hasKids;
    /**
     * level
     * 层级
     */
    
    @ApiModelProperty(value="层级")
    private Integer level;
    /**
     * parent_id
     * 父级id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="父级id")
    private Long parentId;

    /**
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    private String parentName;

    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    private Integer status;

    /**
     * 图标
     */
    @ApiModelProperty(value="图标")
    private String icon;

    @ApiModelProperty(value="打开方式")
    private String openWay;

    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")

    private Integer sort;

    /**
     * 子节点(数据库中不存在该字段，仅用于传输数据使用)
     */
    private List<MdmResourceVo> children;
}
