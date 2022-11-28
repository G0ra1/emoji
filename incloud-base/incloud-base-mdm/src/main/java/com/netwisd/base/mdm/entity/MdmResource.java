package com.netwisd.base.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @Description $资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@Data
@Table(value = "incloud_base_mdm_resource",comment = "资源管理")
@TableName("incloud_base_mdm_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资源管理 Entity")
public class MdmResource extends IModel<MdmResource> {

    /**
     * resource_code
     * 资源编码
     */
    @ApiModelProperty(value="资源编码")
    @TableField(value="resource_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "资源编码")
    private String resourceCode;
    /**
     * resource_name
     * 资源名称
     */
    @ApiModelProperty(value="资源名称")
    @TableField(value="resource_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "资源名称")
    private String resourceName;
    /**
     * resource_url
     * 资源url
     */
    @ApiModelProperty(value="资源url")
    @TableField(value="resource_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资源url")
    private String resourceUrl;
    /**
     * resource_type
     * 资源类型
     */
    @ApiModelProperty(value="资源类型")
    @TableField(value="resource_type")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "资源类型")
    private Integer resourceType;
    /**
     * sys_id
     * 子系统ID
     */
    @ApiModelProperty(value="子系统ID")
    @TableField(value="sys_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "子系统ID")
    private Long sysId;
    /**
     * sys_name
     * 子系统名称
     */
    @ApiModelProperty(value="子系统名称")
    @TableField(value="sys_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "子系统名称")
    private String sysName;
    /**
     * has_kids
     * 是否有子集
     */
    @ApiModelProperty(value="是否有子集")
    @TableField(value="has_kids")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否有子集")
    private Integer hasKids;
    /**
     * level
     * 层级
     */
    @ApiModelProperty(value="层级")
    @TableField(value="level")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "层级")
    private Integer level;
    /**
     * parent_id
     * 父级id
     */
    @ApiModelProperty(value="父级id")
    @TableField(value="parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "父级id")
    private Long parentId;

    /**
     * sys_name
     * 子系统名称
     */
    @ApiModelProperty(value="父级名称")
    @TableField(value="parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "父级名称")
    private String parentName;

    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "状态标识")
    private Integer status;

    @ApiModelProperty(value="图标")
    @TableField(value="icon")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "图标")
    private String icon;

    @ApiModelProperty(value="打开方式")
    @TableField(value="open_way")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "打开方式")
    private String openWay;

    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
}
