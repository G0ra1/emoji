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
 * @Description 甲方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:34:54
 */
@Data
@Table(value = "incloud_biz_mdm_contract_partya",comment = "甲方")
@TableName("incloud_biz_mdm_contract_partya")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "甲方 Entity")
public class ContractPartya extends IModel<ContractPartya> {

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
     * partya_id
     * 甲方id
     */
    @ApiModelProperty(value="甲方id")
    @TableField(value="partya_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "甲方id")
    private Long partyaId;
    /**
     * partya_code
     * 甲方编号
     */
    @ApiModelProperty(value="甲方编号")
    @TableField(value="partya_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "甲方编号")
    private String partyaCode;
    /**
     * partya_name
     * 甲方名称
     */
    @ApiModelProperty(value="甲方名称")
    @TableField(value="partya_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "甲方名称")
    private String partyaName;
    /**
     * partya_qualification
     * 甲方资质
     */
    @ApiModelProperty(value="甲方资质")
    @TableField(value="partya_qualification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "甲方资质")
    private String partyaQualification;
    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    @TableField(value="contract_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "合同类型（0,采购合同;1,销售合同）")
    private Integer contractType;
}
