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
 * @Description $物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
@Data
@Table(value = "incloud_biz_mdm_project",comment = "项目")
@TableName("incloud_biz_mdm_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目 Entity")
public class Project extends IModel<Project> {

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
     * short_name
     * 项目简称
     */
    @ApiModelProperty(value="项目简称")
    @TableField(value="short_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目简称")
    private String shortName;
    /**
     * construction_dept
     * 施工项目部
     */
    @ApiModelProperty(value="施工项目部")
    @TableField(value="construction_dept")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "施工项目部")
    private String constructionDept;
    /**
     * construction_units
     * 施工单位
     */
    @ApiModelProperty(value="施工单位")
    @TableField(value="construction_units")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "施工单位")
    private String constructionUnits;
    /**
     * catagory
     * 工程类别
     */
    @ApiModelProperty(value="工程类别")
    @TableField(value="catagory")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工程类别")
    private String catagory;
    /**
     * territory
     * 地域
     */
    @ApiModelProperty(value="地域")
    @TableField(value="territory")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "地域")
    private String territory;
    /**
     * site
     * 工程地点
     */
    @ApiModelProperty(value="工程地点")
    @TableField(value="site")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工程地点")
    private String site;
    /**
     * priority_project
     * 重点工程
     */
    @ApiModelProperty(value="重点工程")
    @TableField(value="priority_project")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "重点工程")
    private String priorityProject;
    /**
     * project_manager
     * 项目经理
     */
    @ApiModelProperty(value="项目经理")
    @TableField(value="project_manager")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目经理")
    private String projectManager;
    /**
     * project_leader
     * 项目负责人
     */
    @ApiModelProperty(value="项目负责人")
    @TableField(value="project_leader")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目负责人")
    private String projectLeader;
    /**
     * build_organization
     * 建设单位
     */
    @ApiModelProperty(value="建设单位")
    @TableField(value="build_organization")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "建设单位")
    private String buildOrganization;
    /**
     * build_organ_category
     * 建设单位类别
     */
    @ApiModelProperty(value="建设单位类别")
    @TableField(value="build_organ_category")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "建设单位类别")
    private String buildOrganCategory;
    /**
     * design_organ
     * 设计单位
     */
    @ApiModelProperty(value="设计单位")
    @TableField(value="design_organ")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设计单位")
    private String designOrgan;
    /**
     * supervisor_organ
     * 监理单位
     */
    @ApiModelProperty(value="监理单位")
    @TableField(value="supervisor_organ")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "监理单位")
    private String supervisorOrgan;
    /**
     * plan_permission_num
     * 规划许可号
     */
    @ApiModelProperty(value="规划许可号")
    @TableField(value="plan_permission_num")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "规划许可号")
    private String planPermissionNum;
    /**
     * construction_num
     * 施工许可号
     */
    @ApiModelProperty(value="施工许可号")
        @TableField(value="construction_num")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "施工许可号")
    private String constructionNum;
    /**
     * complet_record_time
     * 竣工备案日期
     */
    @ApiModelProperty(value="竣工备案日期")
    @TableField(value="complet_record_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "竣工备案日期")
    private Date completRecordTime;
    /**
     * contract_start_time
     * 合同开工日期
     */
    @ApiModelProperty(value="合同开工日期")
    @TableField(value="contract_start_time")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "合同开工日期")
    private Date contractStartTime;
    /**
     * contract_end_time
     * 合同竣工日期
     */
    @ApiModelProperty(value="合同竣工日期")
    @TableField(value="contract_end_time")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "合同竣工日期")
    private Date contractEndTime;
    /**
     * project_time
     * 工期
     */
    @ApiModelProperty(value="工期")
    @TableField(value="project_time")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "工期")
    private String projectTime;
    /**
     * start_time_actual
     * 实际开工日期
     */
    @ApiModelProperty(value="实际开工日期")
    @TableField(value="start_time_actual")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "实际开工日期")
    private Date startTimeActual;
    /**
     * end_time_actual
     * 实际竣工日期
     */
    @ApiModelProperty(value="实际竣工日期")
    @TableField(value="end_time_actual")
    @Column(type = DataType.DATE, length = 0,  isNull = true, comment = "实际竣工日期")
    private Date endTimeActual;
    /**
     * project_time_actual
     * 实际工期
     */
    @ApiModelProperty(value="实际工期")
    @TableField(value="project_time_actual")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "实际工期")
    private String projectTimeActual;
    /**
     * state
     * 项目状态
     */
    @ApiModelProperty(value="项目状态")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目状态")
    private String state;
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
