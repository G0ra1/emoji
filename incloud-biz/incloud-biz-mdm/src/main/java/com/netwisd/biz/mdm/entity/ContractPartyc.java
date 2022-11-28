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
 * @Description $丙方 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-14 15:34:03
 */
@Data
@Table(value = "incloud_biz_mdm_contract_partyc",comment = "丙方")
@TableName("incloud_biz_mdm_contract_partyc")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "丙方 Entity")
public class ContractPartyc extends IModel<ContractPartyc> {

    /**
     * contract_id
     * 合同id
     */
    @ApiModelProperty(value="合同id")
    @TableField(value="contract_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "合同id")
    private Long contractId;
    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同编号")
    private String contractCode;
    /**
     * partyc_id
     * 丙方id
     */
    @ApiModelProperty(value="丙方id")
    @TableField(value="partyc_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "丙方id")
    private Long partycId;
    /**
     * partyc_code
     * 丙方编号
     */
    @ApiModelProperty(value="丙方编号")
    @TableField(value="partyc_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "丙方编号")
    private String partycCode;
    /**
     * partyc_name
     * 丙方名称
     */
    @ApiModelProperty(value="丙方名称")
    @TableField(value="partyc_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "丙方名称")
    private String partycName;
    /**
     * partyc_qualification
     * 丙方资质
     */
    @ApiModelProperty(value="丙方资质")
    @TableField(value="partyc_qualification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "丙方资质")
    private String partycQualification;
    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    @TableField(value="contract_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "合同类型（0,采购合同;1,销售合同）")
    private Integer contractType;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "创建人ID")
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
     * parent_org_id
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
    @TableField(value="parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父级机构ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    @TableField(value="parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "父级机构名称")
    private String parentOrgName;
    /**
     * parent_dept_id
     * 父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @TableField(value="parent_dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父级部门ID")
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    @TableField(value="parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "父级部门名称")
    private String parentDeptName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="org_full_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "父级组织全路径ID")
    private String orgFullId;
}
