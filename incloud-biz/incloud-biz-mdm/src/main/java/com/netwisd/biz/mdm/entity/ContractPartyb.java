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

/**
 * @Description $合同乙方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-30 11:09:06
 */
@Data
@Table(value = "incloud_biz_mdm_contract_partyb",comment = "合同乙方")
@TableName("incloud_biz_mdm_contract_partyb")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "合同乙方 Entity")
public class ContractPartyb extends IModel<ContractPartyb> {

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
     * partyb_id
     * 乙方id
     */
    @ApiModelProperty(value="乙方id")
    @TableField(value="partyb_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "乙方id")
    private Long partybId;
    /**
     * partyb_code
     * 乙方编号
     */
    @ApiModelProperty(value="乙方编号")
    @TableField(value="partyb_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "乙方编号")
    private String partybCode;
    /**
     * partyb_name
     * 乙方名称
     */
    @ApiModelProperty(value="乙方名称")
    @TableField(value="partyb_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "乙方名称")
    private String partybName;
    /**
     * partyb_qualification
     * 乙方资质
     */
    @ApiModelProperty(value="乙方资质")
    @TableField(value="partyb_qualification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "乙方资质")
    private String partybQualification;
    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    @TableField(value="contract_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "合同类型（0,采购合同;1,销售合同）")
    private Integer contractType;
}
