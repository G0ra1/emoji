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
 * @Description $采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
@Data
@Table(value = "incloud_biz_mdm_contract_purchase_file",comment = "采购合同附件")
@TableName("incloud_biz_mdm_contract_purchase_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "采购合同附件 Entity")
public class ContractPurchaseFile extends IModel<ContractPurchaseFile> {

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
     * 合同编码
     */
    @ApiModelProperty(value="合同编码")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同编码")
    private String contractCode;
    /**
     * contract_name
     * 合同名称
     */
    @ApiModelProperty(value="合同名称")
    @TableField(value="contract_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同名称")
    private String contractName;
    /**
     * file_name
     * 附件名称
     */
    @ApiModelProperty(value="附件名称")
    @TableField(value="file_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件名称")
    private String fileName;
    /**
     * file_url
     * 附件地址
     */
    @ApiModelProperty(value="附件地址")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件地址")
    private String fileUrl;
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
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "创建人父级机构ID")
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
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "创建人父级部门ID")
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
