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
 * @Description $客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@Data
@Table(value = "incloud_biz_mdm_customer",comment = "客户")
@TableName("incloud_biz_mdm_customer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户 Entity")
public class Customer extends IModel<Customer> {

    /**
     * customer_name
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    @TableField(value="customer_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "客户名称")
    private String customerName;
    /**
     * customer_code
     * 客户编码
     */
    @ApiModelProperty(value="客户编码")
    @TableField(value="customer_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "客户编码")
    private String customerCode;
    /**
     * register_capital
     * 注册资金
     */
    @ApiModelProperty(value="注册资金")
    @TableField(value="register_capital")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "注册资金")
    private String registerCapital;
    /**
     * property
     * 企业性质
     */
    @ApiModelProperty(value="企业性质")
    @TableField(value="property")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "企业性质")
    private String property;
    /**
     * credential
     * 资质等级
     */
    @ApiModelProperty(value="资质等级")
    @TableField(value="credential")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资质等级")
    private String credential;
    /**
     * zipcode
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    @TableField(value="zipcode")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "邮编")
    private String zipcode;
    /**
     * business_scope
     * 经营范围
     */
    @ApiModelProperty(value="经营范围")
    @TableField(value="business_scope")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "经营范围")
    private String businessScope;
    /**
     * establish_date
     * 成立日期
     */
    @ApiModelProperty(value="成立日期")
    @TableField(value="establish_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "成立日期")
    private Date establishDate;
    /**
     * belong_customer
     * 所属大客户
     */
    @ApiModelProperty(value="所属大客户")
    @TableField(value="belong_customer")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属大客户")
    private String belongCustomer;
    /**
     * business_license
     * 营业执照
     */
    @ApiModelProperty(value="营业执照")
    @TableField(value="business_license")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "营业执照")
    private String businessLicense;
    /**
     * org_code
     * 组织机构代码
     */
    @ApiModelProperty(value="组织机构代码")
    @TableField(value="org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织机构代码")
    private String orgCode;
    /**
     * unify_code
     * 统一社会信用代码
     */
    @ApiModelProperty(value="统一社会信用代码")
    @TableField(value="unify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "统一社会信用代码")
    private String unifyCode;
    /**
     * taxpayer_type
     * 纳税人类别
     */
    @ApiModelProperty(value="纳税人类别")
    @TableField(value="taxpayer_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "纳税人类别")
    private String taxpayerType;
    /**
     * registration_number
     * 纳税人识别号
     */
    @ApiModelProperty(value="纳税人识别号")
    @TableField(value="registration_number")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "纳税人识别号")
    private String registrationNumber;
    /**
     * representative
     * 法人
     */
    @ApiModelProperty(value="法人")
    @TableField(value="representative")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "法人")
    private String representative;
    /**
     * representative_phone
     * 法人联系电话
     */
    @ApiModelProperty(value="法人联系电话")
    @TableField(value="representative_phone")
    @Column(type = DataType.VARCHAR, length = 15,  isNull = true, comment = "法人联系电话")
    private String representativePhone;
    /**
     * agent
     * 委托代理人
     */
    @ApiModelProperty(value="委托代理人")
    @TableField(value="agent")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "委托代理人")
    private String agent;
    /**
     * agent_phone
     * 委托代理人联系电话
     */
    @ApiModelProperty(value="委托代理人联系电话")
    @TableField(value="agent_phone")
    @Column(type = DataType.VARCHAR, length = 15,  isNull = true, comment = "委托代理人联系电话")
    private String agentPhone;
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
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value="data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String dataSourceId;

    /**
     * is_del
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableField(value="is_del")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "是否删除")
    private String isDel;

    /**
     * flow_state
     * 流程状态（提交/未提交）
     */
    @ApiModelProperty(value="流程状态（1提交/0未提交）")
    @TableField(value="flow_state")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "流程状态（1提交/0未提交）")
    private String flowState;
}
