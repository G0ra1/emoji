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
 * @Description $岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@Data
@Table(value = "incloud_base_mdm_post_user",comment = "岗位与用户关系")
@TableName("incloud_base_mdm_post_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位与用户关系 Entity")
public class MdmPostUser extends IModel<MdmPostUser> {

    /**
     * org_full_post_id
     * 组织全路径岗位ID
     */
    @ApiModelProperty(value="组织全路径岗位ID")
    @TableField(value="org_full_post_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "组织全路径岗位ID")
    private String orgFullPostId;
    /**
     * org_full_post_name
     * 组织全路径岗位名称
     */
    @ApiModelProperty(value="组织全路径岗位名称")
    @TableField(value="org_full_post_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "组织全路径岗位名称")
    private String orgFullPostName;
    /**
     * post_id
     * 岗位ID
     */
    @ApiModelProperty(value="岗位ID")
    @TableField(value="post_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "岗位ID")
    private Long postId;
    /**
     * post_code
     * 岗位code
     */
    @ApiModelProperty(value="岗位code")
    @TableField(value="post_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位code")
    private String postCode;
    /**
     * post_name
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    @TableField(value="post_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位名称")
    private String postName;
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
    /**
     * is_master
     * 是否主岗
     */
    @ApiModelProperty(value="是否主岗")
    @TableField(value="is_master")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否主岗")
    private Integer isMaster;
}
