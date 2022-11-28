package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 股份机构DTO 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:51:35
 */
@Data
@ApiModel(value = "股份机构 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GuFenUserDto extends IDto{

    public GuFenUserDto(Args args){
        super(args);
    }

    /**
     * hr_code
     * 人力人员编码
     */


    @ApiModelProperty(value="人力人员编码")
    private String hr_code;

    /**
     * employee_name
     * 人力人员名称
     */

    @ApiModelProperty(value="人力人员名称")
    private String employee_name;

    /**
     * id_card_number
     * 身份证号
     */

    @ApiModelProperty(value="身份证号")
    private String id_card_number;

    /**
     * 所属单位编码
     * own_unitcode
     */

    @ApiModelProperty(value="所属单位编码")
    private String own_unitcode;

    /**
     * own_deptcode
     * 所属部门编码
     */
    @ApiModelProperty(value="所属部门编码")
    private String own_deptcode;

    /**
     * post
     * 职位编码
     */

    @ApiModelProperty(value="职位编码")
    private String post;

    /**
     * gender
     * 性别：1男2女
     */
    @ApiModelProperty(value="性别：1男2女")
    private String gender;

    /**
     * email
     * 电子邮箱
     */
    @ApiModelProperty(value="电子邮箱")
    private String email;

    /**
     * entry_time
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private String entry_time;

    /**
     * phone
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String phone;

    /**
     * contact_phone
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String contact_phone;

    /**
     * age
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private String age;

    /**
     * ethnic_group
     * 民族
     */
    @ApiModelProperty(value="民族")
    private String ethnic_group;

    /**
     * native_place
     * 籍贯
     */
    @ApiModelProperty(value="籍贯")
    private String native_place;

    /**
     * registered_residence
     * 户口
     */
    @ApiModelProperty(value="户口")
    private String registered_residence;

    /**
     * registered_residence_type
     * 户口类型
     */
    @ApiModelProperty(value="户口类型")
    private String registered_residence_type;

    /**
     * title
     * 头衔
     */
    @ApiModelProperty(value="头衔")
    private String title;

    /**
     * title_level
     * 头衔级别
     */
    @ApiModelProperty(value="头衔级别")
    private String title_level;

    /**
     * employee_type
     * 员工类别
     */
    @ApiModelProperty(value="员工类别")
    private String employee_type;

    /**
     * employee_dict
     * 人员分类
     */
    @ApiModelProperty(value="人员分类")
    private String employee_dict;

    /**
     * employee_status
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private String employee_status;

    /**
     * political_outlook
     * 政治前景
     */
    @ApiModelProperty(value="政治前景")
    private String political_outlook;

    /**
     * marital_status
     * 婚姻状况
     */
    @ApiModelProperty(value="婚姻状况")
    private String marital_status;

    /**
     * graduation_time
     * 毕业时间
     */
    @ApiModelProperty(value="毕业时间")
    private String graduation_time;

    /**
     * degree_education
     * 学位
     */
    @ApiModelProperty(value="学位")
    private String degree_education;

    /**
     * job_time
     * 工作时间
     */
    @ApiModelProperty(value="工作时间")
    private String job_time;

    /**
     * jobclassification
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private String jobclassification;

    /**
     * positionlevel
     * 职位级别
     */
    @ApiModelProperty(value="职位级别")
    private String positionlevel;

    /**
     * talentcategory
     * 人才类别
     */
    @ApiModelProperty(value="人才类别")
    private String talentcategory;

    /**
     * lastupdatetime
     * 最后更新时间
     */
    @ApiModelProperty(value="最后更新时间")
    private String lastupdatetime;

    /**
     * province
     * 人员省份编码
     */
    @ApiModelProperty(value="人员省份编码")
    private String province;

    /**
     * quit_time
     * 离职时间
     */
    @ApiModelProperty(value="离职时间")
    private String quit_time;

    /**
     * administrative_org
     * 行政组织：UM
     */
    @ApiModelProperty(value="行政组织：UM")
    private String administrative_org;

    /**
     * norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    private String norder;
}
