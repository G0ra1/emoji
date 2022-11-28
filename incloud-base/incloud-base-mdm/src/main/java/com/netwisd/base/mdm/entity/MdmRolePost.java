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
 * @Description $角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@Data
@Table(value = "incloud_base_mdm_role_post",comment = "角色与岗位关系")
@TableName("incloud_base_mdm_role_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色与岗位关系 Entity")
public class MdmRolePost extends IModel<MdmRolePost> {

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
     * post_id
     * 岗位的ID
     */
    @ApiModelProperty(value="岗位的ID")
    @TableField(value="post_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "岗位的ID")
    private Long postId;
    /**
     * post_code
     * 岗位的code
     */
    @ApiModelProperty(value="岗位的code")
    @TableField(value="post_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位的code")
    private String postCode;
    /**
     * post_name
     * 岗位的名称
     */
    @ApiModelProperty(value="岗位的名称")
    @TableField(value="post_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位的名称")
    private String postName;
}
