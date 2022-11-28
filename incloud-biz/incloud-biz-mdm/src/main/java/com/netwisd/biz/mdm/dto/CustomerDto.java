package com.netwisd.biz.mdm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@Data
@ApiModel(value = "客户 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerDto extends IDto{

    public CustomerDto(Args args){
        super(args);
    }

    /**
     * customer_name
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    @Valid(length = 255,nullMsg = "客户名称不能为空")
    private String customerName;

    /**
     * customer_code
     * 客户编码
     */
    @ApiModelProperty(value="客户编码")
    @Valid(length = 255,nullMsg = "客户编码不能为空")
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

    @ApiModelProperty(value="开户银行")
    private String bankName;

    @ApiModelProperty(value="银行账号")
    private String bankAccount;

    @ApiModelProperty(value="联系人ID")
    private Integer contactsId;

    @ApiModelProperty(value="联系人姓名")
    private String contactsName;

    @ApiModelProperty(value="联系人电话")
    private String contactsPhone;

    @ApiModelProperty(value="数据源id")
    private String dataSourceId;

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

    private List<CustomerBankDto> bankList;
    private List<CustomerContactsDto> contactsList;

    /**
     * 查询修改时间区间的开始时间
     */
    @ApiModelProperty( value="查询修改时间区间的开始时间" )
    public LocalDateTime sUpdateTime;

    /**
     * 查询修改时间区间的结束时间
     */
    @ApiModelProperty( value="查询修改时间区间的结束时间" )
    public LocalDateTime eUpdateTime;

}
