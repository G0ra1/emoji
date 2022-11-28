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
 * @Description $角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@Data
@Table(value = "incloud_base_mdm_role_sys",comment = "角色对应的功能权限子系统")
@TableName("incloud_base_mdm_role_sys")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色对应的功能权限子系统 Entity")
public class MdmRoleSys extends IModel<MdmRoleSys> {

    /**
     * role_code
     * 角色code
     */
    @ApiModelProperty(value="角色code")
    @TableField(value="role_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "角色code")
    private String roleCode;
    /**
     * role_id
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    @TableField(value="role_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "角色id")
    private Long roleId;
    /**
     * sys_id
     * 子系统id
     */
    @ApiModelProperty(value="子系统id")
    @TableField(value="sys_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "子系统id")
    private Long sysId;
}
