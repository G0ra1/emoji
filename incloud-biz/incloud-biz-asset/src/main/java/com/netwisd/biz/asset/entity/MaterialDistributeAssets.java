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
    import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $资产派发明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 10:51:28
 */
@Data
@Table(value = "incloud_biz_asset_material_distribute_assets",comment = "资产派发明细")
@TableName("incloud_biz_asset_material_distribute_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产派发明细 Entity")
public class MaterialDistributeAssets extends IModel<MaterialDistributeAssets> {

    /**
     * distribute_id
     * 派发id
     */
    @ApiModelProperty(value="派发id")
    @TableField(value="distribute_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_material_distribute" ,fkFieldName = "id" , isNull = true, comment = "派发id")
    private Long distributeId;
    /**
     * distribute_code
     * 派发编号
     */
    @ApiModelProperty(value="派发编号")
    @TableField(value="distribute_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "派发编号")
    private String distributeCode;
    /**
     * source_id
     * 数据来源id
     */
    @ApiModelProperty(value="数据来源id")
    @TableField(value="source_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "数据来源id")
    private Long sourceId;
    /**
     * assets_id
     * 资产Id
     */
    @ApiModelProperty(value="资产Id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产Id")
    private Long assetsId;
    /**
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类id")
    private Long classifyId;
    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类名称")
    private String classifyName;
    /**
     * route
     * 物资分类全路径
     */
    @ApiModelProperty(value="物资分类全路径")
    @TableField(value="route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径")
    private String route;
    /**
     * route_name
     * 物资分类全路径名称
     */
    @ApiModelProperty(value="物资分类全路径名称")
    @TableField(value="route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径名称")
    private String routeName;
    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称")
    private String itemName;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
    /**
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    @TableField(value="desclong")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    @TableField(value="descshort")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资短描述")
    private String descshort;
    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    @TableField(value="unit_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位编码")
    private String unitCode;
    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    @TableField(value="unit_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位名称")
    private String unitName;
    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资材质")
    private String materialQuality;
    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资标准")
    private String standard;
    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资规格")
    private String specs;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    @TableField(value="distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "派发数量")
    private BigDecimal distributeNumber;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "备注")
    private String remark;
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
    /**
     * source_code
     * 来源code
     */
    @ApiModelProperty(value="来源code")
    @TableField(value="source_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "来源code")
    private String sourceCode;
    /**
     * source_assets_id
     * 来源明细id
     */
    @ApiModelProperty(value="来源明细id")
    @TableField(value="source_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "来源明细id")
    private Long sourceAssetsId;
    /**
     * item_id
     * 物资id
     */
    @ApiModelProperty(value="物资id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资id")
    private Long itemId;
    /**
     * apply_number
     * 申请数量
     */
    @ApiModelProperty(value="申请数量")
    @TableField(value="apply_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "申请数量")
    private BigDecimal applyNumber;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @ApiModelProperty(value="申请人ID")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @ApiModelProperty(value="申请人机构ID")
    @TableField(value="apply_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人机构ID")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    @TableField(value="apply_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人机构名称")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @ApiModelProperty(value="申请人部门ID")
    @TableField(value="apply_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    @TableField(value="apply_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人部门名称")
    private String applyUserDeptName;
    /**
     * assets_user_id
     * 物资申请人id
     */
    @ApiModelProperty(value="物资申请人id")
    @TableField(value="assets_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资申请人id")
    private Long assetsUserId;
    /**
     * assets_user_name
     * 物资申请人名称
     */
    @ApiModelProperty(value="物资申请人名称")
    @TableField(value="assets_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资申请人名称")
    private String assetsUserName;
    /**
     * assets__user_org_id
     * 物资申请人机构id
     */
    @ApiModelProperty(value="物资申请人机构id")
    @TableField(value="assets_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资申请人机构id")
    private Long assetsUserOrgId;
    /**
     * assets__user_org_name
     * 物资申请人机构名称
     */
    @ApiModelProperty(value="物资申请人机构名称")
    @TableField(value="assets_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资申请人机构名称")
    private String assetsUserOrgName;
    /**
     * assets__user_dept_id
     * 物资申请人部门
     */
    @ApiModelProperty(value="物资申请人部门")
    @TableField(value="assets_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资申请人部门")
    private Long assetsUserDeptId;
    /**
     * assets__user_dept_name
     * 物资申请人部门名称
     */
    @ApiModelProperty(value="物资申请人部门名称")
    @TableField(value="assets_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资申请人部门名称")
    private String assetsUserDeptName;

}
