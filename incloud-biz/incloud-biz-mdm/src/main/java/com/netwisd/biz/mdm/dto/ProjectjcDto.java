package com.netwisd.biz.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 集采项目 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-19 14:17:49
 */
@Data
@Map("incloud_biz_mdm_projectjc")
@ApiModel(value = "集采项目 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectjcDto extends IDto{

    public ProjectjcDto(Args args){
        super(args);
    }
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
     * org_id
     * 所属组织机构ID
     */
    @ApiModelProperty(value="所属组织机构ID")
    private String orgId;
    /**
     * org_code
     * 所属组织机构编码
     */
    @ApiModelProperty(value="所属组织机构编码")
    private String orgCode;
    /**
     * org_name
     * 所属组织机构名称
     */
    @ApiModelProperty(value="所属组织机构名称")
    private String orgName;
    /**
     * suborg_ids
     * 二级单位ID
     */
    @ApiModelProperty(value="二级单位ID")
    private String suborgIds;
    /**
     * suborg_names
     * 二级单位名称
     */
    @ApiModelProperty(value="二级单位名称")
    private String suborgNames;
    /**
     * is_live
     * 是否在建（0：非在建，1：在建）
     */
    @ApiModelProperty(value="是否在建（0：非在建，1：在建）")
    private String isLive;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;


}
