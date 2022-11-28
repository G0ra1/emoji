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
 * @Description $耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
@Data
@Table(value = "incloud_biz_asset_daybook_supplies",comment = "耗材流水表")
@TableName("incloud_biz_asset_daybook_supplies")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "耗材流水表 Entity")
public class DaybookSupplies extends IModel<DaybookSupplies> {

    /**
    * form_id
    * 表单id
    */
    @ApiModelProperty(value="表单id")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "表单id")
    private Long formId;

    /**
     * camunda_procins_id
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    @TableField("camunda_procins_id")
    @Column( type = DataType.VARCHAR, length = 64L, isNull = false, comment = "camunda流程实例ID" )
    private String camundaProcinsId;

    /**
    * assets_id
    * 资产台账id
    */
    @ApiModelProperty(value="资产台账id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产台账id")
    private Long assetsId;

    /**
    * assets_detail_id
    * 资产明细表id
    */
    @ApiModelProperty(value="资产明细表id")
    @TableField(value="assets_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产明细表id")
    private Long assetsDetailId;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
    private Long itemId;
    /**
    * item_code
    * 物资编码;物资编码
    */
    @ApiModelProperty(value="物资编码;物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码;物资编码")
    private String itemCode;

    /**
    * item_name
    * 物资名称;物资名称
    */
    @ApiModelProperty(value="物资名称;物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称;物资名称")
    private String itemName;

    /**
    * type
    * 业务类型;验收/领用等等
    */
    @ApiModelProperty(value="业务类型;验收/领用等等")
    @TableField(value="type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "业务类型;验收/领用等等")
    private String type;
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

}
