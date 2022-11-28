package com.netwisd.base.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@Data
@ApiModel(value = "岗位与用户关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmTransferDto extends IDto{

    public MdmTransferDto(Args args){
        super(args);
    }
    /**
     * parent_org_id
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    private String parentOrgName;
    /**
     * parent_dept_id
     * 父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    private String parentDeptName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="组织全路径岗位ID")
    @Valid(length = 4000) 
    private String orgFullId;

    /**
     * org_full_name
     * 组织全路径岗位名称
     */
    @ApiModelProperty(value="组织全路径岗位名称")
    @Valid(length = 4000)
    private String orgFullName;

    /**
     * parent_org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    private String parentOrgFullName;
    /**
     * parent_dept_full_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    private String parentDeptFullName;

    /**
     * post_id
     * 岗位ID
     */
    @ApiModelProperty(value="岗位ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @Valid(length = 20) 
    private Long postId;

    /**
     * post_code
     * 岗位code
     */
    @ApiModelProperty(value="岗位code")
    @Valid(length = 255) 
    private String postCode;

    /**
     * post_name
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    @Valid(length = 255) 
    private String postName;

    /**
     * user_id
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @Valid(length = 20) 
    private Long userId;

    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @Valid(length = 255) 
    private String userName;

    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    @Valid(length = 255) 
    private String userNameCh;

    /**
     * org_full_post_id
     * 组织全路径岗位ID
     */
    @ApiModelProperty(value="组织全路径岗位ID")
    private String orgFullPostId;

    /**
     * org_full_post_name
     * 组织全路径岗位名称
     */
    @ApiModelProperty(value="组织全路径岗位名称")
    private String orgFullPostName;

}
