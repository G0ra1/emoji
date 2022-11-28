package com.netwisd.base.common.mdm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 用户 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
@Data
@ApiModel(value = "用户 Vo")
public class MdmUserVo extends IVo{
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
     * orgFullId
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    private String orgFullId;
    /**
     * orgFullName
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
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
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    private Integer sort;
    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;
    /**
     * pass_word
     * 用户密码
     */
    @ApiModelProperty(value="用户密码")
    private String passWord;
    /**
     * user_name_ch
     * 用户中文姓名
     */
    @ApiModelProperty(value="用户中文姓名")
    private String userNameCh;
    /**
     * nationality
     * 国籍
     */
    @ApiModelProperty(value="国籍")
    private String nationality;
    /**
     * card_type
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    private Integer cardType;
    /**
     * sex
     * 性别
     */
    @ApiModelProperty(value="性别")
    private Integer sex;
    /**
     * id_card
     * 证件号;0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；
     */
    @ApiModelProperty(value="证件号;0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    private String idCard;
    /**
     * birthday
     * 生日
     */
    @ApiModelProperty(value="生日")
    private LocalDateTime birthday;
    /**
     * native_place
     * 籍贯
     */
    @ApiModelProperty(value="籍贯")
    private String nativePlace;
    /**
     * nation
     * 民族
     */
    @ApiModelProperty(value="民族")
    private String nation;
    /**
     * marriage_status
     * 婚姻状况
     */
    @ApiModelProperty(value="婚姻状况")
    private String marriageStatus;
    /**
     * politics_status
     * 政治面貌;0中共党员；中共预备役党员；共青团员；民革会员；民盟盟员；
     */
    @ApiModelProperty(value="政治面貌;0中共党员；中共预备役党员；共青团员；民革会员；民盟盟员；")
    private Integer politicsStatus;
    /**
     * is_fertility
     * 生育情况;0未育；1以育；
     */
    @ApiModelProperty(value="生育情况;0未育；1以育；")
    private Integer isFertility;
    /**
     * religion
     * 宗教信仰;0无；1佛教；2喇嘛教；3道教；4天主教；5基督教；7东正教；8伊斯兰教；8其他；
     */
    @ApiModelProperty(value="宗教信仰;0无；1佛教；2喇嘛教；3道教；4天主教；5基督教；7东正教；8伊斯兰教；8其他；")
    private Integer religion;
    /**
     * health_condition
     * 健康状况;0健康或良好；1一般或较弱；2有慢性病；3残疾；
     */
    @ApiModelProperty(value="健康状况;0健康或良好；1一般或较弱；2有慢性病；3残疾；")
    private Integer healthCondition;
    /**
     * personal_expertise
     * 个人专长
     */
    @ApiModelProperty(value="个人专长")
    private String personalExpertise;
    /**
     * hobbies
     * 兴趣爱好
     */
    @ApiModelProperty(value="兴趣爱好")
    private String hobbies;
    /**
     * height
     * 身高;cm
     */
    @ApiModelProperty(value="身高;cm")
    private String height;
    /**
     * body_weight
     * 体重;kg
     */
    @ApiModelProperty(value="体重;kg")
    private String bodyWeight;
    /**
     * blood_type
     * 血型;0A；1B;2AB;3O
     */
    @ApiModelProperty(value="血型;0A；1B;2AB;3O")
    private Integer bloodType;
    /**
     * phone_num
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phoneNum;
    /**
     * office_phone
     * 办公电话
     */
    @ApiModelProperty(value="办公电话")
    private String officePhone;
    /**
     * email
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * qq
     * QQ
     */
    @ApiModelProperty(value="QQ")
    private String qq;
    /**
     * wechat
     * 微信
     */
    @ApiModelProperty(value="微信")
    private String wechat;
    /**
     * addr
     * 家庭住址
     */
    @ApiModelProperty(value="家庭住址")
    private String addr;
    /**
     * now_addr
     * 现居地址
     */
    @ApiModelProperty(value="现居地址")
    private String nowAddr;
    /**
     * now_addr_zipcode
     * 现居地邮编
     */
    @ApiModelProperty(value="现居地邮编")
    private String nowAddrZipcode;
    /**
     * employment_form
     * 用工形式;0试用；1正式；
     */
    @ApiModelProperty(value="用工形式;0试用；1正式；")
    private Integer employmentForm;
    /**
     * employment_type
     * 用工类别;0合同制（正式员工）；1派遣制；2外包制（外协员工）；3临时工；4实习生；
     */
    @ApiModelProperty(value="用工类别;0合同制（正式员工）；1派遣制；2外包制（外协员工）；3临时工；4实习生；")
    private Integer employmentType;
    /**
     * status
     * 状态;1在职；2离职；
     */
    @ApiModelProperty(value="状态;1在职；2离职；")
    private Integer status;
    /**
     * status_reason
     * 状态原因
     */
    @ApiModelProperty(value="状态原因")
    private String statusReason;
    /**
     * birth_addr
     * 户口所在地
     */
    @ApiModelProperty(value="户口所在地")
    private String birthAddr;
    /**
     * birth_nature
     * 户口性质;0农业户口；1农业户口-集体户；2非农户口；3非农户口集体户；4未落常驻户口；5其他户口；
     */
    @ApiModelProperty(value="户口性质;0农业户口；1农业户口-集体户；2非农户口；3非农户口集体户；4未落常驻户口；5其他户口；")
    private Integer birthNature;
    /**
     * birth_place
     * 出生地
     */
    @ApiModelProperty(value="出生地")
    private String birthPlace;
    /**
     * police_station
     * 所属派出所
     */
    @ApiModelProperty(value="所属派出所")
    private String policeStation;
    /**
     * former_name
     * 曾用名
     */
    @ApiModelProperty(value="曾用名")
    private String formerName;
    /**
     * education
     * 学历;0小学；1初中；2高中、中专、技校；3大学专科；4大学本科；5硕士研究生；6博士研究生
     */
    @ApiModelProperty(value="学历;0小学；1初中；2高中、中专、技校；3大学专科；4大学本科；5硕士研究生；6博士研究生")
    private String education;
    /**
     * school
     * 毕业院校
     */
    @ApiModelProperty(value="毕业院校")
    private String school;
    /**
     * global_sort
     * 全局排序
     */
    @ApiModelProperty(value="全局排序")
    private Integer globalSort;
    /**
     * global_sort_second
     * 辅助全局排序
     */
    @ApiModelProperty(value="辅助全局排序")
    private Integer globalSortSecond;


    /**
     * postName
     * 主岗名称
     */
    @ApiModelProperty(value="主岗名称")
    private String postName;

    /**
     * dutyName
     * 主职名称
     */
    @ApiModelProperty(value="主职名称")
    private String dutyName;
    /**
     * school
     * 兼岗名称
     */
    @ApiModelProperty(value="兼岗名称")
    private String andPostName;

    /**
     * MdmPostUser
     * 用户岗位信息
     */
    @ApiModelProperty(value="用户岗位信息")
    private List<MdmPostUserVo> mdmPosts;

    /**
     * MdmDutyUser
     * 用户职务信息
     */
    @ApiModelProperty(value="用户职务信息")
    private List<MdmDutyUserVo> mdmDutys;

    /**
     * nc_user_name
     * NC用户名称
     */
    @ApiModelProperty(value="NC用户名称")
    private String ncUserName;

    /**
     * nc_user_code
     * NC用户Code
     */
    @ApiModelProperty(value="NC用户Code")
    private String ncUserCode;

    /**
     * photo_file_id
     * 用户头像-上传的文件id
     */
    @ApiModelProperty(value="用户头像-上传的文件id")
    private String photoFileId;

    /**
     * user_class
     * 用户分类
     */
    @ApiModelProperty(value="用户分类")
    private Integer userClass;

    //private String photo_file_id;
    private List<MdmDutyUserVo> mdmDutyUsers;

    //===============================股份人员数据开始===================================
    /**
     * gf_hr_code
     * 人力人员编码
     */
    @ApiModelProperty(value="人力人员编码")
    private String gfHrCode;
    /**
     * gf_post
     * 职位编码
     */
    @ApiModelProperty(value="职位编码")
    private String gfPost;
    /**
     * gf_entry_time
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private String gfEntryTime;
    /**
     * gf_age
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private Integer gfAge;
    /**
     * gf_title
     * 头衔
     */
    @ApiModelProperty(value="头衔")
    private String gfTitle;
    /**
     * gf_title_level
     * 头衔级别
     */
    @ApiModelProperty(value="头衔级别")
    private String gfTitleLevel;
    /**
     * gf_employee_type
     * 员工类别
     */
    @ApiModelProperty(value="员工类别")
    private String gfEmployeeType;
    /**
     * gf_employee_dict
     * 人员分类
     */
    @ApiModelProperty(value="人员分类")
    private String gfEmployeeDict;
    /**
     * gf_employee_status
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private String gfEmployeeStatus;
    /**
     * gf_political_outlook
     * 政治前景
     */
    @ApiModelProperty(value="政治前景")
    private String gfPoliticalOutlook;
    /**
     * gf_graduation_time
     * 毕业时间
     */
    @ApiModelProperty(value="毕业时间")
    private String gfGraduationTime;
    /**
     * gf_degree_education
     * 学位
     */
    @ApiModelProperty(value="学位")
    private String gfDegreeEducation;
    /**
     * gf_job_time
     * 工作时间
     */
    @ApiModelProperty(value="工作时间")
    private String gfJobTime;
    /**
     * gf_jobclassification
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private String gfJobclassification;
    /**
     * gf_talentcategory
     * 人才类别
     */
    @ApiModelProperty(value="人才类别")
    private String gfTalentcategory;
    /**
     * gf_province
     * 人员省份编码
     */
    @ApiModelProperty(value="人员省份编码")
    private String gfProvince;
    /**
     * gf_quit_time
     * 离职时间
     */
    @ApiModelProperty(value="离职时间")
    private String gfQuitTime;
    /**
     * gf_administrative_org
     * 行政组织：UM
     */
    @ApiModelProperty(value="行政组织：UM")
    private String gfAdministrativeOrg;
    /**
     * gf_norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer gfNorder;

    //===============================股份人员数据开始===================================

    //===============================在线学习人员数据开始===================================
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

    //===============================在线学习人员数据结束===================================
}
