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
 * @Description $仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@Data
@Table(value = "incloud_biz_asset_warehouse",comment = "仓库")
@TableName("incloud_biz_asset_warehouse")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "仓库 Entity")
public class Warehouse extends IModel<Warehouse> {

    /**
    * warehouse_name
    * 仓库名称
    */
    @ApiModelProperty(value="仓库名称")
    @TableField(value="warehouse_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "仓库名称")
    private String warehouseName;

    /**
     * org_id
     * 所属机构
     */
    @ApiModelProperty(value="所属机构")
    @TableField(value="org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属机构")
    private Long orgId;

    /**
     * org_name
     * 所属机构
     */
    @ApiModelProperty(value="所属机构")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属机构")
    private String orgName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = false, comment = "父级组织全路径ID")
    private String orgFullId;
    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="org_full_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "父级组织全路径名称")
    private String orgFullName;

    /**
    * dept_id
    * 所属部门
    */
    @ApiModelProperty(value="所属部门")
    @TableField(value="dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属部门")
    private Long deptId;

    /**
    * dept_name
    * 所属部门
    */
    @ApiModelProperty(value="所属部门")
    @TableField(value="dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属部门")
    private String deptName;

    /**
    * address
    * 仓库地点
    */
    @ApiModelProperty(value="仓库地点")
    @TableField(value="address")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "仓库地点")
    private String address;

    /**
    * respond_user_id
    * 责任人
    */
    @ApiModelProperty(value="责任人")
    @TableField(value="respond_user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "责任人")
    private Long respondUserId;

    /**
     * respond_user_name
     * 责任人
     */
    @ApiModelProperty(value="责任人")
    @TableField(value="respond_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "责任人")
    private String respondUserName;

    /**
     * del_flag
     * 删除标识
     */
    @ApiModelProperty(value="删除标识")
    @TableField(value="del_flag")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "删除标识")
    private String delFlag;

    /**
     * house_type
     * 类型
     */
    @ApiModelProperty(value="类型")
    @TableField(value="house_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "类型")
    private String houseType;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;

}
