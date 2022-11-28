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
 * @Description $角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@Data
@Table(value = "incloud_base_mdm_role_resource",comment = "角色与资源关系")
@TableName("incloud_base_mdm_role_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色与资源关系 Entity")
public class MdmRoleResource extends IModel<MdmRoleResource> {

    /**
     * role_id
     * 角色ID
     */
    @ApiModelProperty(value="角色ID")
    @TableField(value="role_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "角色ID")
    private Long roleId;
    /**
     * resource_id
     * 资源ID
     */
    @ApiModelProperty(value="资源ID")
    @TableField(value="resource_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "资源ID")
    private Long resourceId;
    /**
     * role_code
     * 角色Code
     */
    @ApiModelProperty(value="角色Code")
    @TableField(value="role_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "角色Code")
    private String roleCode;
}
