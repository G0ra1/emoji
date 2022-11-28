package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@ApiModel(value = "职务 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmDutyDto extends IDto{

    public MdmDutyDto(Args args){
        super(args);
    }

    /**
     * parent_dept_id
     * 所属部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="所属部门ID")
    private Long parentDeptId;

    /**
     * parent_dept_name
     * 所属部门名称
     */

    @Valid(length = 255)
    @ApiModelProperty(value="所属部门名称")
    private String parentDeptName;

    /**
     * parent_org_id
     * 所属机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="所属机构ID")
    private Long parentOrgId;

    /**
     * parent_org_name
     * 所属机构名称
     */


    @ApiModelProperty(value="所属机构名称")
    private String parentOrgName;

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
     * org_full_id
     * 父级组织全路径ID
     */

    @Valid(length = 4000)
    @ApiModelProperty(value="父级组织全路径ID")
    private String orgFullId;

    /**
     * org_full_name
     * 父级组织全路径名称
     */

    @Valid(length = 4000)
    @ApiModelProperty(value="父级组织全路径名称")
    private String orgFullName;

    /**
     * duty_name
     * 职务名称
     */

    @Valid(length = 255)
    @ApiModelProperty(value="职务名称")
    private String dutyName;

    /**
     * duty_code
     * 职务编号
     */

    @Valid(length = 255)
    @ApiModelProperty(value="职务编码")
    private String dutyCode;

    /**
     * duty_up_parent_id
     * 上级职务部门id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="上级职务部门id")
    private Long dutyUpParentId;

    /**
     * duty_up_parent_name
     * 上级职务部门名称
     */


    @ApiModelProperty(value="上级职务部门名称")
    private String dutyUpParentName;

    /**
     * duty_up_id
     * 上级职务id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="上级职务id")
    private Long dutyUpId;

    /**
     * duty_up_name
     * 上级职务
     */


    @ApiModelProperty(value="上级职务")
    private String dutyUpName;

    /**
     * duty_low_parent_id
     * 下级职务部门id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="下级职务部门id")
    private Long dutyLowParentId;

    /**
     * duty_low_parent_name
     * 下级职务部门名称
     */


    @ApiModelProperty(value="下级职务部门名称")
    private String dutyLowParentName;

    /**
     * duty_low_id
     * 下级职务id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="下级职务id")
    private Long dutyLowId;

    /**
     * duty_low_name
     * 下级职务
     */


    @ApiModelProperty(value="下级职务")
    private String dutyLowName;

    /**
     * duty_sequ_id
     * 职务序列id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="职务序列id")
    private Long dutySequId;

    /**
     * duty_sequ_name
     * 职务序列
     */


    @ApiModelProperty(value="职务序列")
    private String dutySequName;

    /**
     * duty_grade_id
     * 职务职等id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="职务职等id")
    private Long dutyGradeId;

    /**
     * duty_grade_name
     * 职务职等
     */


    @ApiModelProperty(value="职务职等")
    private String dutyGradeName;

    /**
     * duty_tag_id
     * 职务标识id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="职务标识id")
    private Long dutyTagId;

    /**
     * duty_tag_name
     * 职务标识
     */


    @ApiModelProperty(value="职务标识")
    private String dutyTagName;

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
     * duty_duty
     * 职务职责
     */


    @ApiModelProperty(value="职务职责")
    private String dutyDuty;

    /**
     * duty_ability
     * 职务胜任能力
     */


    @ApiModelProperty(value="职务胜任能力")
    private String dutyAbility;

    /**
     * duty_content
     * 职务工作内容
     */


    @ApiModelProperty(value="职务工作内容")
    private String dutyContent;

    /**
     * duty_check
     * 职务考核标准
     */


    @ApiModelProperty(value="职务考核标准")
    private String dutyCheck;

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

    @Valid(length = 2)
    @ApiModelProperty(value="状态标识")
    private Integer status;

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
