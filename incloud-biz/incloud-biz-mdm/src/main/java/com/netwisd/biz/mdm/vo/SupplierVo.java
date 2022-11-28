package com.netwisd.biz.mdm.vo;

import com.netwisd.biz.mdm.dto.SupplierBankDto;
import com.netwisd.biz.mdm.dto.SupplierContactsDto;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@Data
@ApiModel(value = "供应商 Vo")
public class SupplierVo extends IVo{

    /**
     * supplier_name
     * 供应商名称
     */
    @ApiModelProperty(value="供应商名称")
    private String supplierName;
    /**
     * supplier_code
     * 供应商编码
     */
    @ApiModelProperty(value="供应商编码")
    private String supplierCode;
    /**
     * is_abroad
     * 境内/外:0境内，1境外
     */
    @ApiModelProperty(value="境内/外:0境内，1境外")
    private String isAbroad;
    /**
     * org_name
     * 合作单位
     */
    @ApiModelProperty(value="合作单位")
    private String orgName;
    /**
     * reg_type
     * 注册类别【0统一社会信用代码、1组织机构代码】
     */
    @ApiModelProperty(value="注册类别【0统一社会信用代码、1组织机构代码】")
    private String regType;
    /**
     * invoice_type
     * 发票类型
     */
    @ApiModelProperty(value="发票类型")
    private String invoiceType;
    /**
     * orgnization_code
     * 组织机构代码/统一社会信用代码，根据注册类别区分
     */
    @ApiModelProperty(value="组织机构代码/统一社会信用代码，根据注册类别区分")
    private String orgnizationCode;
    /**
     * business_license
     * 税务登记号
     */
    @ApiModelProperty(value="税务登记号")
    private String businessLicense;
    /**
     * reg_number
     * 工商注册号
     */
    @ApiModelProperty(value="工商注册号")
    private String regNumber;
    /**
     * taxpayer_type
     * 纳税人类别：1一般纳税人，2小规模纳税人
     */
    @ApiModelProperty(value="纳税人类别：1一般纳税人，2小规模纳税人")
    private String taxpayerType;
    /**
     * register_capital
     * 注册资金（万元）
     */
    @ApiModelProperty(value="注册资金（万元）")
    private String registerCapital;
    /**
     * representative
     * 法人代表
     */
    @ApiModelProperty(value="法人代表")
    private String representative;
    /**
     * currency
     * 币种：1人民币，2美元，3日元，4港元，5台币，6欧元，7英镑，8澳元，9韩元，10加拿大元
     */
    @ApiModelProperty(value="币种：1人民币，2美元，3日元，4港元，5台币，6欧元，7英镑，8澳元，9韩元，10加拿大元")
    private String currency;
    /**
     * phone
     * 企业电话
     */
    @ApiModelProperty(value="企业电话")
    private String phone;
    /**
     * fax
     * 企业传真
     */
    @ApiModelProperty(value="企业传真")
    private String fax;
    /**
     * address
     * 企业地址
     */
    @ApiModelProperty(value="企业地址")
    private String address;
    /**
     * zipcode
     * 地区邮编
     */
    @ApiModelProperty(value="地区邮编")
    private String zipcode;
    /**
     * credential
     * 资质等级
     */
    @ApiModelProperty(value="资质等级")
    private String credential;
    /**
     * type_name
     * 类型名称：1生产商，2经销商/代理商，3其他
     */
    @ApiModelProperty(value="类型名称：1生产商，2经销商/代理商，3其他")
    private String typeName;
    /**
     * property
     * 单位性质：1合伙企业，2私营企业，3联营企业，4集体企业，5国有企业，6股份合作制企业，7有限责任公司，8股份有限公司
     */
    @ApiModelProperty(value="单位性质：1合伙企业，2私营企业，3联营企业，4集体企业，5国有企业，6股份合作制企业，7有限责任公司，8股份有限公司")
    private String property;
    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    private String dataSourceId;

    /**
     * primary_business
     * 主营业务/经营范围
     */
    @ApiModelProperty(value="主营业务/经营范围")
    private String primaryBusiness;//主营业务/经营范围

    private List<SupplierBankVo> bankList;
    private List<SupplierContactsVo> contactsList;
}
