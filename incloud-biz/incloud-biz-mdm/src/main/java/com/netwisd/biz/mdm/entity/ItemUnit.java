package com.netwisd.biz.mdm.entity;

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
 * @Description $物资辅计量单位 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-31 20:23:36
 */
@Data
@Table(value = "incloud_biz_mdm_item_unit",comment = "物资辅计量单位")
@TableName("incloud_biz_mdm_item_unit")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资辅计量单位 Entity")
public class ItemUnit extends IModel<ItemUnit> {

    /**
    * item_id
    * 物资id
    */
    @ApiModelProperty(value="物资id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资id")
    private Long itemId;

    /**
     * unit_code
     * 单位编码
     */
    @ApiModelProperty(value="单位编码")
    @TableField(value="unit_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "单位编码")
    private String unitCode;

    /**
    * unit_name
    * 单位
    */
    @ApiModelProperty(value="单位")
    @TableField(value="unit_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "单位")
    private String unitName;

    /**
    * conversion_rate
    * 换算率
    */
    @ApiModelProperty(value="换算率")
    @TableField(value="conversion_rate")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "换算率")
    private String conversionRate;

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
    * data_source_state
    * 数据源接入数据问题
    */
    @ApiModelProperty(value="数据源接入数据问题")
    @TableField(value="data_source_state")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "数据源接入数据问题")
    private String dataSourceState;

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
