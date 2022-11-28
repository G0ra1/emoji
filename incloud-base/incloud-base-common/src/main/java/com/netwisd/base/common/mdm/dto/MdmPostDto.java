package com.netwisd.base.common.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description 岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-25 15:15:37
 */
@Data
@ApiModel(value = "岗位 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmPostDto extends IDto{

    public MdmPostDto(Args args){
        super(args);
    }

    /**
     * parent_id
     * 父级ID
     */
    @ApiModelProperty(value="部门ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long parentDeptId;

    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="部门名称")
    @Valid(length = 255)
    private String parentDeptName;

    /**
     * parent_org_id
     * 父级ID
     */
    @ApiModelProperty(value="父级机构ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级名称
     */
    @ApiModelProperty(value="父级机构名称")

    private String parentOrgName;

    /**
     * parent_org_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="部门父级组织全路径名称")

    private String parentOrgFullName;

    /**
     * parent_dept_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="部门父级部门全路径名称")

    private String parentDeptFullName;


    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="部门父级组织全路径ID")
    @Valid
    private String orgFullId;

    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="部门父级组织全路径名称")
    @Valid
    private String orgFullName;

    /**
     * post_name
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    @Valid(length = 255) 
    private String postName;

    /**
     * post_code
     * 岗位编号
     */
    @ApiModelProperty(value="岗位code")
    @Valid(length = 255) 
    private String postCode;

    /**
     * post_up_parent_id
     * 上级部门id
     */
    @ApiModelProperty(value="上级部门id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postUpParentId;
    /**
     * post_up_parent_name
     * 上级部门name
     */
    @ApiModelProperty(value="上级部门name")

    private String postUpParentName;
    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="上级岗位id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postUpId;

    /**
     * post_up_name
     * 上级岗位
     */
    @ApiModelProperty(value="上级岗位")

    private String postUpName;

    /**
     * post_up_parent_id
     * 下级部门id
     */
    @ApiModelProperty(value="下级部门id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postLowParentId;

    /**
     * post_up_parent_name
     * 下级部门id
     */
    @ApiModelProperty(value="下级部门name")

    private String postLowParentName;

    /**
     * post_low_id
     * 下级岗位id
     */
    @ApiModelProperty(value="下级岗位id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postLowId;

    /**
     * post_low
     * 下级岗位
     */
    @ApiModelProperty(value="下级岗位")
    
    private String postLowName;

    /**
     * post_sequ_id
     * 岗位序列id
     */
    @ApiModelProperty(value="岗位序列id")

    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postSequId;

    /**
     * post_sequ_name
     * 岗位序列
     */
    @ApiModelProperty(value="岗位序列")

    private String postSequName;

    /**
     * post_grade_id
     * 岗位职等id
     */
    @ApiModelProperty(value="岗位职等id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postGradeId;

    /**
     * post_grade_name
     * 岗位职等
     */
    @ApiModelProperty(value="岗位职等")

    private String postGradeName;

    /**
     * post_tag_id
     * 岗位标识id
     */
    @ApiModelProperty(value="岗位标识id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long postTagId;

    /**
     * post_tag_name
     * 岗位标识
     */
    @ApiModelProperty(value="岗位标识")

    private String postTagName;

    /**
     * master_number
     * 主岗人数
     */
    @ApiModelProperty(value="主岗人数")
    
    private Integer masterNumber;

    /**
     * part_number
     * 兼岗人数
     */
    @ApiModelProperty(value="兼岗人数")
    
    private Integer partNumber;

    /**
     * post_duty
     * 岗位职责
     */
    @ApiModelProperty(value="岗位职责")
    
    private String postDuty;

    /**
     * post_ability
     * 岗位胜任能力
     */
    @ApiModelProperty(value="岗位胜任能力")
    
    private String postAbility;

    /**
     * post_content
     * 岗位工作内容
     */
    @ApiModelProperty(value="岗位工作内容")
    
    private String postContent;

    /**
     * post_check
     * 岗位考核标准
     */
    @ApiModelProperty(value="岗位考核标准")
    
    private String postCheck;

    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")

    private Integer sort;

    /**
     * is_ref
     * 是否被引用
     */
    @ApiModelProperty(value="是否被引用")

    private Integer isRef;

    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @Valid(length = 2) 
    private Integer status;

    /**
     * valid_start_time
     * 岗位有效开始时间
     */
    @ApiModelProperty(value="岗位有效开始时间")
    private LocalDateTime validStartTime;

    /**
     * valid_end_time
     * 岗位有效结束时间
     */
    @ApiModelProperty(value="岗位有效结束时间")
    private LocalDateTime validEndTime;

    /**
     * 查询修改时间区间的开始时间
     */
    @ApiModelProperty( value="查询修改时间区间的开始时间" )
    public LocalDateTime sUpdateTime;

    /**
     * 查询修改时间区间的结束时间
     */
    @ApiModelProperty( value="查询修改时间区间的结束时间" )
    public LocalDateTime eUpdateTime;

}
