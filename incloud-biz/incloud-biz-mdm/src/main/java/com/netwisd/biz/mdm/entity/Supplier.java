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

import java.util.Date;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@Data
@Table(value = "incloud_biz_mdm_supplier",comment = "供应商")
@TableName("incloud_biz_mdm_supplier")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "供应商 Entity")
public class Supplier extends IModel<Supplier> {

    /**
     * supplier_name
     * 供应商名称
     */
    @ApiModelProperty(value="供应商名称")
    @TableField(value="supplier_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商名称")
    private String supplierName;
    /**
     * supplier_code
     * 供应商编码
     */
    @ApiModelProperty(value="供应商编码")
    @TableField(value="supplier_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商编码")
    private String supplierCode;
    /**
     * is_abroad
     * 境内/外:0境内，1境外
     */
    @ApiModelProperty(value="境内/外:0境内，1境外")
    @TableField(value="is_abroad")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "境内/外:0境内，1境外")
    private String isAbroad;
    /**
     * org_name
     * 合作单位
     */
    @ApiModelProperty(value="合作单位")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合作单位")
    private String orgName;
    /**
     * reg_type
     * 注册类别【0统一社会信用代码、1组织机构代码】
     */
    @ApiModelProperty(value="注册类别【0统一社会信用代码、1组织机构代码】")
    @TableField(value="reg_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "注册类别【0统一社会信用代码、1组织机构代码】")
    private String regType;
    /**
     * invoice_type
     * 发票类型
     */
    @ApiModelProperty(value="发票类型")
    @TableField(value="invoice_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "发票类型")
    private String invoiceType;
    /**
     * orgnization_code
     * 组织机构代码/统一社会信用代码，根据注册类别区分
     */
    @ApiModelProperty(value="组织机构代码/统一社会信用代码，根据注册类别区分")
    @TableField(value="orgnization_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织机构代码/统一社会信用代码，根据注册类别区分")
    private String orgnizationCode;
    /**
     * business_license
     * 税务登记号
     */
    @ApiModelProperty(value="税务登记号")
    @TableField(value="business_license")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "税务登记号")
    private String businessLicense;
    /**
     * reg_number
     * 工商注册号
     */
    @ApiModelProperty(value="工商注册号")
    @TableField(value="reg_number")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工商注册号")
    private String regNumber;
    /**
     * taxpayer_type
     * 纳税人类别：1一般纳税人，2小规模纳税人
     */
    @ApiModelProperty(value="纳税人类别：1一般纳税人，2小规模纳税人")
    @TableField(value="taxpayer_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "纳税人类别：1一般纳税人，2小规模纳税人")
    private String taxpayerType;
    /**
     * register_capital
     * 注册资金（万元）
     */
    @ApiModelProperty(value="注册资金（万元）")
    @TableField(value="register_capital")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "注册资金（万元）")
    private String registerCapital;
    /**
     * representative
     * 法人代表
     */
    @ApiModelProperty(value="法人代表")
    @TableField(value="representative")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "法人代表")
    private String representative;
    /**
     * currency
     * 币种：1人民币，2美元，3日元，4港元，5台币，6欧元，7英镑，8澳元，9韩元，10加拿大元
     */
    @ApiModelProperty(value="币种：1人民币，2美元，3日元，4港元，5台币，6欧元，7英镑，8澳元，9韩元，10加拿大元")
    @TableField(value="currency")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "币种：1人民币，2美元，3日元，4港元，5台币，6欧元，7英镑，8澳元，9韩元，10加拿大元")
    private String currency;
    /**
     * phone
     * 企业电话
     */
    @ApiModelProperty(value="企业电话")
    @TableField(value="phone")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "企业电话")
    private String phone;
    /**
     * fax
     * 企业传真
     */
    @ApiModelProperty(value="企业传真")
    @TableField(value="fax")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "企业传真")
    private String fax;
    /**
     * address
     * 企业地址
     */
    @ApiModelProperty(value="企业地址")
    @TableField(value="address")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "企业地址")
    private String address;
    /**
     * zipcode
     * 地区邮编
     */
    @ApiModelProperty(value="地区邮编")
    @TableField(value="zipcode")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "地区邮编")
    private String zipcode;
    /**
     * credential
     * 资质等级
     */
    @ApiModelProperty(value="资质等级")
    @TableField(value="credential")
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "资质等级")
    private String credential;
    /**
     * type_name
     * 类型名称：1生产商，2经销商/代理商，3其他
     */
    @ApiModelProperty(value="类型名称：1生产商，2经销商/代理商，3其他")
    @TableField(value="type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "类型名称：1生产商，2经销商/代理商，3其他")
    private String typeName;
    /**
     * property
     * 单位性质：1合伙企业，2私营企业，3联营企业，4集体企业，5国有企业，6股份合作制企业，7有限责任公司，8股份有限公司
     */
    @ApiModelProperty(value="单位性质：1合伙企业，2私营企业，3联营企业，4集体企业，5国有企业，6股份合作制企业，7有限责任公司，8股份有限公司")
    @TableField(value="property")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "单位性质：1合伙企业，2私营企业，3联营企业，4集体企业，5国有企业，6股份合作制企业，7有限责任公司，8股份有限公司")
    private String property;
    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value="data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String dataSourceId;

    /**
     * primary_business
     * 主营业务/经营范围
     */
    @ApiModelProperty(value="主营业务/经营范围")
    @TableField(value="primary_business")
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "主营业务/经营范围")
    private String primaryBusiness;//主营业务/经营范围

    /**
     * establish_date
     * 注册日期/成立日期 格式：yyyy-MM-dd
     */
    @ApiModelProperty(value="注册日期/成立日期 格式：yyyy-MM-dd")
    @TableField(value="establish_date")
    @Column(type = DataType.DATE, length = 255,  isNull = true, comment = "注册日期/成立日期 格式：yyyy-MM-dd")
    private Date establishDate;//注册日期/成立日期 格式：yyyy-MM-dd

    /**
     * is_use
     * 供应商状态0：不可用；1：可用
     */
    @ApiModelProperty(value="供应商状态0：不可用；1：可用")
    @TableField(value="is_use")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "供应商状态0：不可用；1：可用")
    private String isUse;//供应商状态0：不可用；1：可用


    /**
     * taxpayer_code
     * 纳税人识别号
     */
    @ApiModelProperty(value="纳税人识别号")
    @TableField(value="taxpayer_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "纳税人识别号")
    private String taxpayerCode;//纳税人识别号

    /**
     * org_id
     * 所属组织id
     */
    @ApiModelProperty(value="所属组织id")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属组织id")
    private String orgId;//所属组织id

}
