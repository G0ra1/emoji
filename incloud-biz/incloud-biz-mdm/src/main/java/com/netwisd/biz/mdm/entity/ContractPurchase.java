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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description $采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Data
@Table(value = "incloud_biz_mdm_contract_purchase",comment = "采购合同")
@TableName("incloud_biz_mdm_contract_purchase")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "采购合同 Entity")
public class ContractPurchase extends IModel<ContractPurchase> {

    /**
     * project_id
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    @TableField(value="project_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目id")
    private String projectId;
    /**
     * project_code
     * 项目编号
     */
    @ApiModelProperty(value="项目编号")
    @TableField(value="project_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目编号")
    private String projectCode;
    /**
     * project_name
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    @TableField(value="project_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目名称")
    private String projectName;
    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同编号")
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
     * type
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    @TableField(value="type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同类型")
    private String type;
    /**
     * state
     * 合同状态
     */
    @ApiModelProperty(value="合同状态")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "合同状态")
    private String state;
    /**
     * contract_date
     * 签订日期
     */
    @ApiModelProperty(value="签订日期")
    @TableField(value="contract_date")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "签订日期")
    private Date contractDate;
    /**
     * start_time
     * 合同开始日期
     */
    @ApiModelProperty(value="合同开始日期")
    @TableField(value="start_time")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "合同开始日期")
    private Date startTime;
    /**
     * end_time
     * 合同结束日期
     */
    @ApiModelProperty(value="合同结束日期")
    @TableField(value="end_time")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "合同结束日期")
    private Date endTime;
    /**
     * contract_price
     * 含税总额
     */
    @ApiModelProperty(value="含税总额")
    @TableField(value="contract_price")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "含税总额")
    private BigDecimal contractPrice;
    /**
     * notax_amount
     * 未税金额
     */
    @ApiModelProperty(value="未税金额")
    @TableField(value="notax_amount")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "未税金额")
    private BigDecimal notaxAmount;
    /**
     * tax
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax")
    @Column(type = DataType.DECIMAL, length = 3, precision = 1 , isNull = true, comment = "税率")
    private BigDecimal tax;
    /**
     * employee_id
     * 编制人id
     */
    @ApiModelProperty(value="编制人id")
    @TableField(value="employee_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "编制人id")
    private String employeeId;
    /**
     * employee_name
     * 编制人姓名
     */
    @ApiModelProperty(value="编制人姓名")
    @TableField(value="employee_name")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "编制人姓名")
    private String employeeName;
    /**
     * obj_type
     * 品类
     */
    @ApiModelProperty(value="品类")
    @TableField(value="obj_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "品类")
    private String objType;
    /**
     * content
     * 合同正文
     */
    @ApiModelProperty(value="合同正文")
    @TableField(value="content")
    @Column(type = DataType.MEDIUMTEXT,  length = 0,  isNull = true, comment = "合同正文")
    private String content;
    /**
     * orgId
     * 需求单位id
     */
    @ApiModelProperty(value="需求单位id")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "需求单位id")
    private String orgId;
    /**
     * org_code
     * 需求单位编号
     */
    @ApiModelProperty(value="需求单位编号")
    @TableField(value="org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "需求单位编号")
    private String orgCode;
    /**
     * org_name
     * 需求单位名称
     */
    @ApiModelProperty(value="需求单位名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "需求单位名称")
    private String orgName;
    /**
     * subject
     * 采购方式，1：自采，2：集采
     */
    @ApiModelProperty(value="采购方式，1：自采，2：集采")
    @TableField(value="subject")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购方式，1：自采，2：集采")
    private String subject;
    /**
     * contract_category
     * (物资)合同类型(2.框架协议 1.订购合同 3订购合同 4项下合同 5项下订单) 2、(工程、服务)合同类型 (2.框架协议 6.常规合同 4项下合同 5项下订单)
     */
    @ApiModelProperty(value="(物资)合同类型(2.框架协议 1.订购合同 3订购合同 4项下合同 5项下订单) 2、(工程、服务)合同类型 (2.框架协议 6.常规合同 4项下合同 5项下订单)")
    @TableField(value="contract_category")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "(物资)合同类型(2.框架协议 1.订购合同 3订购合同 4项下合同 5项下订单) 2、(工程、服务)合同类型 (2.框架协议 6.常规合同 4项下合同 5项下订单)")
    private String contractCategory;
    /**
     * purchase_type
     * 采购形式(1分散采购 2集中采购)
     */
    @ApiModelProperty(value="采购形式(1分散采购 2集中采购)")
    @TableField(value="purchase_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购形式(1分散采购 2集中采购)")
    private String purchaseType;
    /**
     * execute_price
     * 执行金额
     */
    @ApiModelProperty(value="执行金额")
    @TableField(value="execute_price")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "执行金额")
    private BigDecimal executePrice;
    /**
     * approval_date
     * 审批时间
     */
    @ApiModelProperty(value="审批时间")
    @TableField(value="approval_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批时间")
    private Date approvalDate;
    /**
     * framecontract_id
     * 项下合同/订单源ID
     */
    @ApiModelProperty(value="项下合同/订单源ID")
    @TableField(value="framecontract_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "项下合同/订单源ID")
    private String framecontractId;
    /**
     * sourcecontract_id
     * 补充协议源ID
     */
    @ApiModelProperty(value="补充协议源ID")
    @TableField(value="sourcecontract_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "补充协议源ID")
    private String sourcecontractId;
    /**
     * addround
     * 补充协议轮次
     */
    @ApiModelProperty(value="补充协议轮次")
    @TableField(value="addround")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "补充协议轮次")
    private String addround;
    /**
     * signtype
     * 合同签订形式(6.固定总价合同 7.固定单价合同 8.可调价合同 9.成本加酬金合同 10.其他形式合同)
     */
    @ApiModelProperty(value="合同签订形式(6.固定总价合同 7.固定单价合同 8.可调价合同 9.成本加酬金合同 10.其他形式合同)")
    @TableField(value="signtype")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同签订形式(6.固定总价合同 7.固定单价合同 8.可调价合同 9.成本加酬金合同 10.其他形式合同)")
    private String signtype;
    /**
     *  data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value=" data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String  dataSourceId;
    /**
     *  project_type
     * 项目类型
     */
    @ApiModelProperty(value="项目类型")
    @TableField(value=" project_type")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "项目类型")
    private String projectType;

    /**
     * sourceOrgId
     * 需求单位id
     */
    @ApiModelProperty(value="需求单位id")
    @TableField(value="source_org_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "需求单位id")
    private String sourceOrgId;
    /**
     * sourceOrgCode
     * 数据源需求单位编号
     */
    @ApiModelProperty(value="数据源需求单位编号")
    @TableField(value="source_org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "需求单位编号")
    private String sourceOrgCode;
    /**
     * source_org_name
     * 数据源需求单位名称
     */
    @ApiModelProperty(value="数据源需求单位名称")
    @TableField(value="source_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "需求单位名称")
    private String sourceOrgName;
    
    /**
     * source_project_id
     * 数据源项目id
     */
    @ApiModelProperty(value="数据源项目id")
    @TableField(value="source_project_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "数据源项目id")
    private String sourceProjectId;
}
