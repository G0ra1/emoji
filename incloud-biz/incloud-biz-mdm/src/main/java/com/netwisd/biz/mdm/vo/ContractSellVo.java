package com.netwisd.biz.mdm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
@Data
@ApiModel(value = "销售合同 Vo")
public class ContractSellVo extends IVo{

    /**
     * project_id
     * 项目id
     */
    
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * project_code
     * 项目编码
     */
    
    @ApiModelProperty(value="项目编码")
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
     * appraisal_code
     * 评审编号
     */
    
    @ApiModelProperty(value="评审编号")
    private String appraisalCode;
    /**
     * contract_date
     * 签约日期
     */
    
    @ApiModelProperty(value="签约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractDate;
    /**
     * contract_classify
     * 合同分类
     */
    
    @ApiModelProperty(value="合同分类")
    private String contractClassify;
    /**
     * type
     * 合同类型
     */
    
    @ApiModelProperty(value="合同类型")
    private String type;
    /**
     * contract_price
     * 合同总造价
     */
    
    @ApiModelProperty(value="合同总造价")
    private BigDecimal contractPrice;
    /**
     * contract_price_up
     * 总造价大写
     */
    
    @ApiModelProperty(value="总造价大写")
    private String contractPriceUp;
    /**
     * cash_deposit_ratio
     * 保证金比例
     */
    
    @ApiModelProperty(value="保证金比例")
    private String cashDepositRatio;
    /**
     * state
     * 合同状态
     */
    
    @ApiModelProperty(value="合同状态")
    private String state;
    /**
     * agent_name
     * 经办人名称
     */
    
    @ApiModelProperty(value="经办人名称")
    private String agentName;
    /**
     * agent_code
     * 经办人code
     */
    
    @ApiModelProperty(value="经办人code")
    private String agentCode;
    /**
     * records_code
     * 备案编号
     */
    
    @ApiModelProperty(value="备案编号")
    private String recordsCode;
    /**
     * tax
     * 税率
     */
    
    @ApiModelProperty(value="税率")
    private String tax;
    /**
     * use_qualification
     * 使用资质
     */
    
    @ApiModelProperty(value="使用资质")
    private String useQualification;
    /**
     * bidding_project
     * 中标项目
     */
    
    @ApiModelProperty(value="中标项目")
    private String biddingProject;
    /**
     * build_project
     * 施工项目
     */
    
    @ApiModelProperty(value="施工项目")
    private String buildProject;
    /**
     * state_onfile
     * 状态归档
     */
    
    @ApiModelProperty(value="状态归档")
    private String stateOnfile;
    /**
     * union_contract_price
     * 联合体合同总金额
     */
    
    @ApiModelProperty(value="联合体合同总金额")
    private BigDecimal unionContractPrice;
    /**
     * start_time
     * 合同开工日期
     */
    
    @ApiModelProperty(value="合同开工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * end_time
     * 合同竣工日期
     */
    
    @ApiModelProperty(value="合同竣工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * duration
     * 工期
     */
    
    @ApiModelProperty(value="工期")
    private String duration;
    /**
     * valuation
     * 合同计价形式
     */
    
    @ApiModelProperty(value="合同计价形式")
    private String valuation;
    /**
     * pay_form
     * 工程款支付形式
     */
    
    @ApiModelProperty(value="工程款支付形式")
    private String payForm;
    /**
     * dispute_solve
     * 争议解决方案
     */
    
    @ApiModelProperty(value="争议解决方案")
    private String disputeSolve;
    /**
     * pledge_year
     * 质押年限
     */
    
    @ApiModelProperty(value="质押年限")
    private String pledgeYear;
    /**
     * major_project
     * 重点工程
     */
    
    @ApiModelProperty(value="重点工程")
    private String majorProject;
    /**
     * money_source
     * 资金来源
     */
    
    @ApiModelProperty(value="资金来源")
    private String moneySource;
    /**
     * investor
     * 投资主体
     */
    
    @ApiModelProperty(value="投资主体")
    private String investor;
    /**
     * quality_target
     * 质量目标
     */
    
    @ApiModelProperty(value="质量目标")
    private String qualityTarget;
    /**
     * contract_mode
     * 承包模式
     */
    
    @ApiModelProperty(value="承包模式")
    private String contractMode;
    /**
     * territory
     * 地域
     */
    
    @ApiModelProperty(value="地域")
    private String territory;
    /**
     * project_type
     * 工程类别
     */
    
    @ApiModelProperty(value="工程类别")
    private String projectType;
    /**
     * data_source_id
     * 数据源id
     */

    @ApiModelProperty(value="数据源id")
    private String dataSourceId;

    /**
     * parent_id
     * 父级id
     */
    @ApiModelProperty(value="父级id")
    private String parentId;

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


    private List<ContractSellDetailsVo> detailsList;

    private List<ContractPartyaVo> partyaList;
    private List<ContractPartybVo> partybList;
    private List<ContractPartycVo> partycList;

}
