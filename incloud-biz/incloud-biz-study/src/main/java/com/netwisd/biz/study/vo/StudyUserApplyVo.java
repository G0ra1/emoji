package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 在线学习人员申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-03-23 16:06:26
 */
@Data
@ApiModel(value = "在线学习人员申请表 Vo")
public class StudyUserApplyVo {

    /**
     * id
     * 主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

    /**
     * user_name_ch
     * 用户中文姓名
     */
    @ApiModelProperty(value="用户中文姓名")
    private String userNameCh;

    /**
     * sex
     * 性别 1男，2女
     */
    @ApiModelProperty(value="性别 1男，2女")
    private Integer sex;

    /**
     * phone_num
     * 用户手机号
     */
    @ApiModelProperty(value="用户手机号")
    private String phoneNum;

    /**
     * card_type
     * 证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；
     */
    @ApiModelProperty(value="证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    private Integer cardType;

    /**
     * id_card
     * 证件号
     */
    @ApiModelProperty(value="证件号")
    private String idCard;

    /**
     * unit_name
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String unitName;

    /**
     * user_condition_code
     * 人员情况
     */
    @ApiModelProperty(value="人员情况")
    private String userConditionCode;

    /**
     * user_condition_name
     * 人员情况
     */
    @ApiModelProperty(value="人员情况")
    private String userConditionName;

    /**
     * user_type
     * 人员角色 1讲师、2学员
     */
    @ApiModelProperty(value="人员角色 1讲师、2学员")
    private String userType;

    /**
     * parent_org_id
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
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
    @ApiModelProperty(value="父级组织全路径ID")
    private String orgFullId;

    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    private String orgFullName;

    /**
     * parent_org_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    private String parentOrgFullName;

    /**
     * parent_dept_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    private String parentDeptFullName;

    /**
     * description
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * user_status
     * 人员审核状态 0未审核，1已审核，2审核不通过
     */
    @ApiModelProperty(value="人员审核状态 0未审核，1已审核，2审核不通过")
    private Integer userStatus;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "create_time")
    public LocalDateTime createTime;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "update_time")
    public LocalDateTime updateTime;
}
