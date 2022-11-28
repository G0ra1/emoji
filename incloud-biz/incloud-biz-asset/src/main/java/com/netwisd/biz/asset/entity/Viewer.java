package com.netwisd.biz.asset.entity;

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
 * @Description $物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Data
@Table(value = "incloud_biz_asset_viewer",comment = "物资数据权限人员表")
@TableName("incloud_biz_asset_viewer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资数据权限人员表 Entity")
public class Viewer extends IModel<Viewer> {

    /**
     * user_id
     * 人员id
     */
    @ApiModelProperty(value="人员id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "人员id")
    private Long userId;
    /**
     * user_name
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "人员名称")
    private String userName;
    /**
     * user_name_ch
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "人员名称")
    private String userNameCh;
    /**
     * viewer_type
     * 查看这类型;1人员，2角色
     */
    @ApiModelProperty(value="查看这类型;1人员，2角色")
    @TableField(value="viewer_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "查看者类型（1人员2角色）")
    private Integer viewerType;
    /**
     * business_type
     * 查看业务类型（字典）
     */
    @ApiModelProperty(value="查看业务类型（字典）")
    @TableField(value="business_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "查看业务类型（字典）")
    private Integer businessType;
    /**
     * business_type_name
     * 查看业务类型（字典）
     */
    @ApiModelProperty(value="查看业务类型（字典）")
    @TableField(value="business_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "查看业务类型（字典）")
    private String businessTypeName;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    @TableField(value="create_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="创建人父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "创建人父级组织全路径ID")
    private String createUserOrgFullId;

}
