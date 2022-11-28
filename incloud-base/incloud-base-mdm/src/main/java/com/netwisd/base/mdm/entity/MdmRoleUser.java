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
 * @Description $角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@Data
@Table(value = "incloud_base_mdm_role_user",comment = "角色与用户关系")
@TableName("incloud_base_mdm_role_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色与用户关系 Entity")
public class MdmRoleUser extends IModel<MdmRoleUser> {

    /**
     * role_id
     * 角色ID
     */
    @ApiModelProperty(value="角色ID")
    @TableField(value="role_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "角色ID")
    private Long roleId;
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
     * user_id
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "用户ID")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户名称")
    private String userName;
    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户中文名称")
    private String userNameCh;
}
