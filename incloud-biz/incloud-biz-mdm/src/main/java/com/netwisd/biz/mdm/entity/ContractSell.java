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
 * @Description $销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
@Data
@Table(value = "incloud_biz_mdm_contract_sell",comment = "销售合同")
@TableName("incloud_biz_mdm_contract_sell")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "销售合同 Entity")
public class ContractSell extends IModel<ContractSell> {

    /**
     * project_id
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    @TableField(value="project_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "项目id")
    private String projectId;
    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    @TableField(value="project_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目编码")
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
     * appraisal_code
     * 评审编号
     */
    @ApiModelProperty(value="评审编号")
    @TableField(value="appraisal_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "评审编号")
    private String appraisalCode;
    /**
     * contract_date
     * 签约日期
     */
    @ApiModelProperty(value="签约日期")
    @TableField(value="contract_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "签约日期")
    private Date contractDate;
    /**
     * contract_classify
     * 合同分类
     */
    @ApiModelProperty(value="合同分类")
    @TableField(value="contract_classify")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "合同分类")
    private String contractClassify;
    /**
     * type
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    @TableField(value="type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同类型")
    private String type;
    /**
     * contract_price
     * 合同总造价
     */
    @ApiModelProperty(value="合同总造价")
    @TableField(value="contract_price")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "合同总造价")
    private BigDecimal contractPrice;
    /**
     * contract_price_up
     * 总造价大写
     */
    @ApiModelProperty(value="总造价大写")
    @TableField(value="contract_price_up")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "总造价大写")
    private String contractPriceUp;
    /**
     * cash_deposit_ratio
     * 保证金比例
     */
    @ApiModelProperty(value="保证金比例")
    @TableField(value="cash_deposit_ratio")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "保证金比例")
    private String cashDepositRatio;
    /**
     * state
     * 合同状态
     */
    @ApiModelProperty(value="合同状态")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "合同状态")
    private String state;
    /**
     * agent_name
     * 经办人名称
     */
    @ApiModelProperty(value="经办人名称")
    @TableField(value="agent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "经办人名称")
    private String agentName;
    /**
     * agent_code
     * 经办人id
     */
    @ApiModelProperty(value="经办人code")
    @TableField(value="agent_code")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "经办人code")
    private String agentCode;
    /**
     * records_code
     * 备案编号
     */
    @ApiModelProperty(value="备案编号")
    @TableField(value="records_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备案编号")
    private String recordsCode;
    /**
     * tax
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "税率")
    private String tax;
    /**
     * use_qualification
     * 使用资质
     */
    @ApiModelProperty(value="使用资质")
    @TableField(value="use_qualification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用资质")
    private String useQualification;
    /**
     * bidding_project
     * 中标项目
     */
    @ApiModelProperty(value="中标项目")
    @TableField(value="bidding_project")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "中标项目")
    private String biddingProject;
    /**
     * build_project
     * 施工项目
     */
    @ApiModelProperty(value="施工项目")
    @TableField(value="build_project")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "施工项目")
    private String buildProject;
    /**
     * state_onfile
     * 状态归档
     */
    @ApiModelProperty(value="状态归档")
    @TableField(value="state_onfile")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "状态归档")
    private String stateOnfile;
    /**
     * union_contract_price
     * 联合体合同总金额
     */
    @ApiModelProperty(value="联合体合同总金额")
    @TableField(value="union_contract_price")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "联合体合同总金额")
    private BigDecimal unionContractPrice;
    /**
     * start_time
     * 合同开工日期
     */
    @ApiModelProperty(value="合同开工日期")
    @TableField(value="start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "合同开工日期")
    private Date startTime;
    /**
     * end_time
     * 合同竣工日期
     */
    @ApiModelProperty(value="合同竣工日期")
    @TableField(value="end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "合同竣工日期")
    private Date endTime;
    /**
     * duration
     * 工期
     */
    @ApiModelProperty(value="工期")
    @TableField(value="duration")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "工期")
    private String duration;
    /**
     * valuation
     * 合同计价形式
     */
    @ApiModelProperty(value="合同计价形式")
    @TableField(value="valuation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同计价形式")
    private String valuation;
    /**
     * pay_form
     * 工程款支付形式
     */
    @ApiModelProperty(value="工程款支付形式")
    @TableField(value="pay_form")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工程款支付形式")
    private String payForm;
    /**
     * dispute_solve
     * 争议解决方案
     */
    @ApiModelProperty(value="争议解决方案")
    @TableField(value="dispute_solve")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "争议解决方案")
    private String disputeSolve;
    /**
     * pledge_year
     * 质押年限
     */
    @ApiModelProperty(value="质押年限")
    @TableField(value="pledge_year")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质押年限")
    private String pledgeYear;
    /**
     * major_project
     * 重点工程
     */
    @ApiModelProperty(value="重点工程")
    @TableField(value="major_project")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "重点工程")
    private String majorProject;
    /**
     * money_source
     * 资金来源
     */
    @ApiModelProperty(value="资金来源")
    @TableField(value="money_source")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资金来源")
    private String moneySource;
    /**
     * investor
     * 投资主体
     */
    @ApiModelProperty(value="投资主体")
    @TableField(value="investor")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "投资主体")
    private String investor;
    /**
     * quality_target
     * 质量目标
     */
    @ApiModelProperty(value="质量目标")
    @TableField(value="quality_target")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质量目标")
    private String qualityTarget;
    /**
     * contract_mode
     * 承包模式
     */
    @ApiModelProperty(value="承包模式")
    @TableField(value="contract_mode")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "承包模式")
    private String contractMode;
    /**
     * territory
     * 地域
     */
    @ApiModelProperty(value="地域")
    @TableField(value="territory")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "地域")
    private String territory;
    /**
     * project_type
     * 工程类别
     */
    @ApiModelProperty(value="工程类别")
    @TableField(value="project_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工程类别")
    private String projectType;
    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value="data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String dataSourceId;
    /**
     * parent_id
     * 父级id
     */
    @ApiModelProperty(value="父级id")
    @TableField(value="parent_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "父级id")
    private String parentId;

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
