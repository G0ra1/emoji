package com.netwisd.biz.mdm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.biz.mdm.entity.ContractPurchase;
import com.netwisd.biz.mdm.entity.ContractPurchaseExecution;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Data
@ApiModel(value = "采购合同 Vo")
public class ContractPurchaseVo extends IVo{

    /**
     * project_id
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * project_code
     * 项目编号
     */
    @ApiModelProperty(value="项目编号")
    private String projectCode;
    /**
     * project_name
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    private String projectName;
    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contractCode;
    /**
     * contract_name
     * 合同名称
     */
    @ApiModelProperty(value="合同名称")
    private String contractName;
    /**
     * type
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    private String type;
    /**
     * state
     * 合同状态
     */
    @ApiModelProperty(value="合同状态")
    private String state;
    /**
     * contract_date
     * 签订日期
     */
    @ApiModelProperty(value="签订日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractDate;
    /**
     * start_time
     * 合同开始日期
     */
    @ApiModelProperty(value="合同开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * end_time
     * 合同结束日期
     */
    @ApiModelProperty(value="合同结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * contract_price
     * 含税总额
     */
    @ApiModelProperty(value="含税总额")
    private BigDecimal contractPrice;
    /**
     * notax_amount
     * 未税金额
     */
    @ApiModelProperty(value="未税金额")
    private BigDecimal notaxAmount;
    /**
     * tax
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal tax;
    /**
     * employee_id
     * 编制人id
     */
    @ApiModelProperty(value="编制人id")
    private String employeeId;
    /**
     * employee_name
     * 编制人姓名
     */
    @ApiModelProperty(value="编制人姓名")
    private String employeeName;
    /**
     * obj_type
     * 品类
     */
    @ApiModelProperty(value="品类")
    private String objType;
    /**
     * content
     * 合同正文
     */
    @ApiModelProperty(value="合同正文")
    private String content;
    /**
     * orgId
     * 需求单位id
     */
    @ApiModelProperty(value="需求单位id")
    private String orgId;
    /**
     * org_code
     * 需求单位编号
     */
    @ApiModelProperty(value="需求单位编号")
    private String orgCode;
    /**
     * org_name
     * 需求单位名称
     */
    @ApiModelProperty(value="需求单位名称")
    private String orgName;
    /**
     * subject
     * 采购方式，1：自采，2：集采
     */
    @ApiModelProperty(value="采购方式，1：自采，2：集采")
    private String subject;
    /**
     * contract_category
     * (物资)合同类型(2.框架协议 1.订购合同 3订购合同 4项下合同 5项下订单) 2、(工程、服务)合同类型 (2.框架协议 6.常规合同 4项下合同 5项下订单)
     */
    @ApiModelProperty(value="(物资)合同类型(2.框架协议 1.订购合同 3订购合同 4项下合同 5项下订单) 2、(工程、服务)合同类型 (2.框架协议 6.常规合同 4项下合同 5项下订单)")
    private String contractCategory;
    /**
     * purchase_type
     * 采购形式(1分散采购 2集中采购)
     */
    @ApiModelProperty(value="采购形式(1分散采购 2集中采购)")
    private String purchaseType;
    /**
     * execute_price
     * 执行金额
     */
    @ApiModelProperty(value="执行金额")
    private BigDecimal executePrice;
    /**
     * approval_date
     * 审批时间
     */
    @ApiModelProperty(value="审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalDate;
    /**
     * framecontract_id
     * 项下合同/订单源ID
     */
    @ApiModelProperty(value="项下合同/订单源ID")
    private String framecontractId;
    /**
     * sourcecontract_id
     * 补充协议源ID
     */
    @ApiModelProperty(value="补充协议源ID")
    private String sourcecontractId;
    /**
     * addround
     * 补充协议轮次
     */
    @ApiModelProperty(value="补充协议轮次")
    private String addround;
    /**
     * signtype
     * 合同签订形式(6.固定总价合同 7.固定单价合同 8.可调价合同 9.成本加酬金合同 10.其他形式合同)
     */
    @ApiModelProperty(value="合同签订形式(6.固定总价合同 7.固定单价合同 8.可调价合同 9.成本加酬金合同 10.其他形式合同)")
    private String signtype;
    /**
     *  data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    private String  dataSourceId;
    private String projectType;

    /**
     * sourceOrgId
     * 需求单位id
     */
    @ApiModelProperty(value="需求单位id")
    private String sourceOrgId;
    /**
     * sourceOrgCode
     * 数据源需求单位编号
     */
    @ApiModelProperty(value="数据源需求单位编号")
    private String sourceOrgCode;
    /**
     * source_org_name
     * 数据源需求单位名称
     */
    @ApiModelProperty(value="数据源需求单位名称")
    private String sourceOrgName;

    /**
     * source_project_id
     * 数据源项目id
     */
    @ApiModelProperty(value="数据源项目id")
    private String sourceProjectId;

    private List<ContractPurchaseDetailsVo> detailsList;
    private List<ContractPurchaseExecutionVo> executionList;
    private List<ContractPartyaVo> partyaList;
    private List<ContractPartybVo> partybList;
    private List<ContractPartycVo> partycList;
    private List<ContractPurchaseFileVo> fileList;

    List<ContractPurchaseVo> childList;

    private boolean hasKids;//是否有子级

}
