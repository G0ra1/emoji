package com.netwisd.common.core.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: current package com.netwisd.common.core.data
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/19 10:05 下午
 */
@ApiModel(value = "公共Vo")
@Data
public class IVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

    /**
     * 创建日期
     */
    @ApiModelProperty( value="create_time" )
    public LocalDateTime createTime;

    /**
     * 修改日期
     */
    @ApiModelProperty( value="update_time" )
    public LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @ApiModelProperty(value="父级机构ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    public Long createUserParentOrgId;

    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    public String createUserParentOrgName;

    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    public Long createUserParentDeptId;

    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    public String createUserParentDeptName;

    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    public String createUserOrgFullId;

    /**
     * ...
     */
}
