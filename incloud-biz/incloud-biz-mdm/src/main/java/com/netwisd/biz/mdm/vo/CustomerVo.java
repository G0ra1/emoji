package com.netwisd.biz.mdm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@Data
@ApiModel(value = "客户 Vo")
public class CustomerVo extends IVo{

    /**
     * customer_name
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String customerName;
    /**
     * customer_code
     * 客户编码
     */
    @ApiModelProperty(value="客户编码")
    private String customerCode;
    /**
     * register_capital
     * 注册资金
     */
    @ApiModelProperty(value="注册资金")
    private String registerCapital;
    /**
     * property
     * 企业性质
     */
    @ApiModelProperty(value="企业性质")
    private String property;
    /**
     * credential
     * 资质等级
     */
    @ApiModelProperty(value="资质等级")
    private String credential;
    /**
     * zipcode
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String zipcode;
    /**
     * business_scope
     * 经营范围
     */
    @ApiModelProperty(value="经营范围")
    private String businessScope;
    /**
     * establish_date
     * 成立日期
     */
    @ApiModelProperty(value="成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date establishDate;
    /**
     * belong_customer
     * 所属大客户
     */
    @ApiModelProperty(value="所属大客户")
    private String belongCustomer;
    /**
     * business_license
     * 营业执照
     */
    @ApiModelProperty(value="营业执照")
    private String businessLicense;
    /**
     * org_code
     * 组织机构代码
     */
    @ApiModelProperty(value="组织机构代码")
    private String orgCode;
    /**
     * unify_code
     * 统一社会信用代码
     */
    @ApiModelProperty(value="统一社会信用代码")
    private String unifyCode;
    /**
     * taxpayer_type
     * 纳税人类别
     */
    @ApiModelProperty(value="纳税人类别")
    private String taxpayerType;
    /**
     * registration_number
     * 纳税人识别号
     */
    @ApiModelProperty(value="纳税人识别号")
    private String registrationNumber;
    /**
     * representative
     * 法人
     */
    @ApiModelProperty(value="法人")
    private String representative;
    /**
     * representative_phone
     * 法人联系电话
     */
    @ApiModelProperty(value="法人联系电话")
    private String representativePhone;
    /**
     * agent
     * 委托代理人
     */
    @ApiModelProperty(value="委托代理人")
    private String agent;
    /**
     * agent_phone
     * 委托代理人联系电话
     */
    @ApiModelProperty(value="委托代理人联系电话")
    private String agentPhone;
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
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    private String dataSourceId;

    /**
     * category_id
     * 客户类别id
     */
    @ApiModelProperty(value="客户类别id")
    private String categoryId;

    /**
     * is_del
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    private String isDel;

    /**
     * flow_state
     * 流程状态（提交/未提交）
     */
    @ApiModelProperty(value="流程状态（提交/未提交）")
    private String flowState;

    private List<CustomerBankVo> bankList;
    private List<CustomerContactsVo> contactsList;

}
