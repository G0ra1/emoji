package com.netwisd.biz.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
@Data
@ApiModel(value = "项目 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectDto extends IDto {

    public ProjectDto(Args args){
        super(args);
    }

    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    @Valid(length = 255,nullMsg = "项目编码不能为空")
    private String projectCode;

    /**
     * project_name
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    @Valid(length = 255,nullMsg = "项目名称不能为空")
    private String projectName;

    /**
     * short_name
     * 项目简称
     */
    @ApiModelProperty(value="项目简称")
    
    private String shortName;

    /**
     * construction_dept
     * 施工项目部
     */
    @ApiModelProperty(value="施工项目部")
    
    private String constructionDept;

    /**
     * construction_units
     * 施工单位
     */
    @ApiModelProperty(value="施工单位")
    
    private String constructionUnits;

    /**
     * catagory
     * 工程类别
     */
    @ApiModelProperty(value="工程类别")
    
    private String catagory;

    /**
     * territory
     * 地域
     */
    @ApiModelProperty(value="地域")
    
    private String territory;

    /**
     * site
     * 工程地点
     */
    @ApiModelProperty(value="工程地点")
    
    private String site;

    /**
     * priority_project
     * 重点工程
     */
    @ApiModelProperty(value="重点工程")
    
    private String priorityProject;

    /**
     * project_manager
     * 项目经理
     */
    @ApiModelProperty(value="项目经理")
    
    private String projectManager;

    /**
     * project_leader
     * 项目负责人
     */
    @ApiModelProperty(value="项目负责人")
    
    private String projectLeader;

    /**
     * build_organization
     * 建设单位
     */
    @ApiModelProperty(value="建设单位")
    
    private String buildOrganization;

    /**
     * build_organ_category
     * 建设单位类别
     */
    @ApiModelProperty(value="建设单位类别")
    
    private String buildOrganCategory;

    /**
     * design_organ
     * 设计单位
     */
    @ApiModelProperty(value="设计单位")
    
    private String designOrgan;

    /**
     * supervisor_organ
     * 监理单位
     */
    @ApiModelProperty(value="监理单位")
    
    private String supervisorOrgan;

    /**
     * plan_permission_num
     * 规划许可号
     */
    @ApiModelProperty(value="规划许可号")
    
    private String planPermissionNum;

    /**
     * construction_num
     * 施工许可号
     */
    @ApiModelProperty(value="施工许可号")
    
    private String constructionNum;

    /**
     * complet_record_time
     * 竣工备案日期
     */
    @ApiModelProperty(value="竣工备案日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completRecordTime;

    /**
     * contract_start_time
     * 合同开工日期
     */
    @ApiModelProperty(value="合同开工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractStartTime;

    /**
     * contract_end_time
     * 合同竣工日期
     */
    @ApiModelProperty(value="合同竣工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEndTime;

    /**
     * project_time
     * 工期
     */
    @ApiModelProperty(value="工期")
    
    private String projectTime;

    /**
     * start_time_actual
     * 实际开工日期
     */
    @ApiModelProperty(value="实际开工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTimeActual;

    /**
     * end_time_actual
     * 实际竣工日期
     */
    @ApiModelProperty(value="实际竣工日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTimeActual;

    /**
     * project_time_actual
     * 实际工期
     */
    @ApiModelProperty(value="实际工期")
    
    private String projectTimeActual;

    /**
     * state
     * 项目状态
     */
    @ApiModelProperty(value="项目状态")
    
    private String state;

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

    private List<ProjectFileDto> files;

}
