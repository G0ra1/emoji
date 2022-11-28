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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@Data
@Table(value = "incloud_base_mdm_role",comment = "角色")
@TableName("incloud_base_mdm_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色 Entity")
public class MdmRole extends IModel<MdmRole> {

    /**
     * sys_id
     * 子系统ID
     */
//    @ApiModelProperty(value="子系统ID")
//    @TableField(value="sys_id")
//    @Column(type = DataType.VARCHAR, length = 500,  isNull = false, comment = "子系统ID")
//    private String sysId;
    /**
     * role_code
     * 角色编码
     */
    @ApiModelProperty(value="角色编码")
    @TableField(value="role_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "角色编码")
    private String roleCode;
    /**
     * role_name
     * 角色名称
     */
    @ApiModelProperty(value="角色名称")
    @TableField(value="role_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "角色名称")
    private String roleName;
    /**
     * role_type
     * 角色类型
     */
    @ApiModelProperty(value="角色类型")
    @TableField(value="role_type")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "角色类型")
    private Integer roleType;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "状态标识")
    private Integer status;
}
