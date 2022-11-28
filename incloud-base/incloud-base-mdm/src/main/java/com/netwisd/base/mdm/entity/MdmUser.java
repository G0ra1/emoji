package com.netwisd.base.mdm.entity;

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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $用户 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
@Data
@Table(value = "incloud_base_mdm_user",comment = "用户")
@TableName("incloud_base_mdm_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户 Entity")
public class MdmUser extends IModel<MdmUser> {
    /**
     * parent_org_id
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
    @TableField(value="parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "父级机构ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    @TableField(value="parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "父级机构名称")
    private String parentOrgName;
    /**
     * parent_dept_id
     * 父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @TableField(value="parent_dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "父级部门ID")
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    @TableField(value="parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "父级部门名称")
    private String parentDeptName;

    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="org_full_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "父级组织全路径ID")
    private String orgFullId;
    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="org_full_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "父级组织全路径名称")
    private String orgFullName;
    /**
     * parent_org_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="parent_org_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级组织全路径名称")
    private String parentOrgFullName;
    /**
     * parent_dept_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    @TableField(value="parent_dept_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级部门全路径名称")
    private String parentDeptFullName;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "用户名")
    private String userName;
    /**
     * pass_word
     * 用户密码
     */
    @ApiModelProperty(value="用户密码")
    @TableField(value="pass_word")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "用户密码")
    private String passWord;
    /**
     * user_name_ch
     * 用户中文姓名
     */
    @ApiModelProperty(value="用户中文姓名")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户中文姓名")
    private String userNameCh;
    /**
     * nationality
     * 国籍
     */
    @ApiModelProperty(value="国籍")
    @TableField(value="nationality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "国籍")
    private String nationality;
    /**
     * card_type
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    @TableField(value="card_type")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "证件类型")
    private Integer cardType;
    /**
     * sex
     * 性别
     */
    @ApiModelProperty(value="性别")
    @TableField(value="sex")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "性别 1 男   2女")
    private Integer sex;
    /**
     * id_card
     * 证件号;0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；
     */
    @ApiModelProperty(value="证件号;0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    @TableField(value="id_card")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "证件号;0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    private String idCard;
    /**
     * birthday
     * 生日
     */
    @ApiModelProperty(value="生日")
    @TableField(value="birthday")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "生日")
    private LocalDateTime birthday;
    /**
     * native_place
     * 籍贯
     */
    @ApiModelProperty(value="籍贯")
    @TableField(value="native_place")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "籍贯")
    private String nativePlace;
    /**
     * nation
     * 民族
     */
    @ApiModelProperty(value="民族")
    @TableField(value="nation")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "民族")
    private String nation;
    /**
     * marriage_status
     * 婚姻状况
     */
    @ApiModelProperty(value="婚姻状况")
    @TableField(value="marriage_status")
    @Column(type = DataType.VARCHAR, length = 2,  isNull = true, comment = "婚姻状况")
    private String marriageStatus;
    /**
     * politics_status
     * 政治面貌;0中共党员；中共预备役党员；共青团员；民革会员；民盟盟员；
     */
    @ApiModelProperty(value="政治面貌;0中共党员；1中共预备役党员；2共青团员；3民革会员；4民盟盟员；")
    @TableField(value="politics_status")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "政治面貌;0中共党员；1中共预备役党员；2共青团员；3民革会员；4民盟盟员；")
    private Integer politicsStatus;
    /**
     * is_fertility
     * 生育情况;0未育；1以育；
     */
    @ApiModelProperty(value="生育情况;0未育；1以育；")
    @TableField(value="is_fertility")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "生育情况;0未育；1以育；")
    private Integer isFertility;
    /**
     * religion
     * 宗教信仰;0无；1佛教；2喇嘛教；3道教；4天主教；5基督教；7东正教；8伊斯兰教；8其他；
     */
    @ApiModelProperty(value="宗教信仰;0无；1佛教；2喇嘛教；3道教；4天主教；5基督教；7东正教；8伊斯兰教；8其他；")
    @TableField(value="religion")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "宗教信仰;0无；1佛教；2喇嘛教；3道教；4天主教；5基督教；7东正教；8伊斯兰教；8其他；")
    private Integer religion;
    /**
     * health_condition
     * 健康状况;0健康或良好；1一般或较弱；2有慢性病；3残疾；
     */
    @ApiModelProperty(value="健康状况;0健康或良好；1一般或较弱；2有慢性病；3残疾；")
    @TableField(value="health_condition")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "健康状况;0健康或良好；1一般或较弱；2有慢性病；3残疾；")
    private Integer healthCondition;
    /**
     * personal_expertise
     * 个人专长
     */
    @ApiModelProperty(value="个人专长")
    @TableField(value="personal_expertise")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "个人专长")
    private String personalExpertise;
    /**
     * hobbies
     * 兴趣爱好
     */
    @ApiModelProperty(value="兴趣爱好")
    @TableField(value="hobbies")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "兴趣爱好")
    private String hobbies;
    /**
     * height
     * 身高;cm
     */
    @ApiModelProperty(value="身高;cm")
    @TableField(value="height")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "身高;cm")
    private String height;
    /**
     * body_weight
     * 体重;kg
     */
    @ApiModelProperty(value="体重;kg")
    @TableField(value="body_weight")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "体重;kg")
    private String bodyWeight;
    /**
     * blood_type
     * 血型;0A；1B;2AB;3O
     */
    @ApiModelProperty(value="血型;0A；1B;2AB;3O")
    @TableField(value="blood_type")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "血型;0A；1B;2AB;3O")
    private Integer bloodType;
    /**
     * phone_num
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    @TableField(value="phone_num")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "手机号")
    private String phoneNum;
    /**
     * office_phone
     * 办公电话
     */
    @ApiModelProperty(value="办公电话")
    @TableField(value="office_phone")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "办公电话")
    private String officePhone;
    /**
     * email
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    @TableField(value="email")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "邮箱")
    private String email;
    /**
     * qq
     * QQ
     */
    @ApiModelProperty(value="QQ")
    @TableField(value="qq")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "QQ")
    private String qq;
    /**
     * wechat
     * 微信
     */
    @ApiModelProperty(value="微信")
    @TableField(value="wechat")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "微信")
    private String wechat;
    /**
     * addr
     * 家庭住址
     */
    @ApiModelProperty(value="家庭住址")
    @TableField(value="addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "家庭住址")
    private String addr;
    /**
     * now_addr
     * 现居地址
     */
    @ApiModelProperty(value="现居地址")
    @TableField(value="now_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "现居地址")
    private String nowAddr;
    /**
     * now_addr_zipcode
     * 现居地邮编
     */
    @ApiModelProperty(value="现居地邮编")
    @TableField(value="now_addr_zipcode")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "现居地邮编")
    private String nowAddrZipcode;
    /**
     * employment_form
     * 用工形式;0试用；1正式；
     */
    @ApiModelProperty(value="用工形式;0试用；1正式；")
    @TableField(value="employment_form")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "用工形式;0试用；1正式；")
    private Integer employmentForm;
    /**
     * employment_type
     * 用工类别;0合同制；1派遣制；2外包制；3临时工；4实习生；
     */
    @ApiModelProperty(value="用工类别;0合同制（正式员工）；1派遣制；2外包制（外协员工）；3临时工；4实习生；；")
    @TableField(value="employment_type")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "用工类别;0合同制（正式员工）；1派遣制；2外包制（外协员工）；3临时工；4实习生；")
    private Integer employmentType;
    /**
     * status
     * 状态;1在职；2离职
     */
    @ApiModelProperty(value="状态;1在职；2离职；")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "状态;1在职；2离职；")
    private Integer status;
    /**
     * status_reason
     * 状态原因
     */
    @ApiModelProperty(value="状态原因")
    @TableField(value="status_reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "状态原因")
    private String statusReason;
    /**
     * birth_addr
     * 户口所在地
     */
    @ApiModelProperty(value="户口所在地")
    @TableField(value="birth_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "户口所在地")
    private String birthAddr;
    /**
     * birth_nature
     * 户口性质;0农业户口；1农业户口-集体户；2非农户口；3非农户口集体户；4未落常驻户口；5其他户口；
     */
    @ApiModelProperty(value="户口性质;0农业户口；1农业户口-集体户；2非农户口；3非农户口集体户；4未落常驻户口；5其他户口；")
    @TableField(value="birth_nature")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "户口性质;0农业户口；1农业户口-集体户；2非农户口；3非农户口集体户；4未落常驻户口；5其他户口；")
    private Integer birthNature;
    /**
     * birth_place
     * 出生地
     */
    @ApiModelProperty(value="出生地")
    @TableField(value="birth_place")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "出生地")
    private String birthPlace;
    /**
     * police_station
     * 所属派出所
     */
    @ApiModelProperty(value="所属派出所")
    @TableField(value="police_station")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属派出所")
    private String policeStation;
    /**
     * former_name
     * 曾用名
     */
    @ApiModelProperty(value="曾用名")
    @TableField(value="former_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "曾用名")
    private String formerName;
    /**
     * education
     * 学历;0小学；1初中；2高中、中专、技校；3大学专科；4大学本科；5硕士研究生；6博士研究生
     */
    @ApiModelProperty(value="学历;0小学；1初中；2高中、中专、技校；3大学专科；4大学本科；5硕士研究生；6博士研究生")
    @TableField(value="education")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "学历;0小学；1初中；2高中、中专、技校；3大学专科；4大学本科；5硕士研究生；6博士研究生")
    private String education;
    /**
     * school
     * 毕业院校
     */
    @ApiModelProperty(value="毕业院校")
    @TableField(value="school")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "毕业院校")
    private String school;
    /**
     * global_sort
     * 全局排序
     */
    @ApiModelProperty(value="全局排序")
    @TableField(value="global_sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "全局排序")
    private Integer globalSort;
    /**
     * global_sort_second
     * 辅助全局排序
     */
    @ApiModelProperty(value="辅助全局排序")
    @TableField(value="global_sort_second")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "辅助全局排序")
    private Integer globalSortSecond;
    /**
     * nc_user_name
     * NC用户名称
     */
    @ApiModelProperty(value="NC用户名称")
    @TableField(value="nc_user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "NC用户名称")
    private String ncUserName;

    /**
     * nc_user_code
     * NC用户Code
     */
    @ApiModelProperty(value="NC用户Code")
    @TableField(value="nc_user_code")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "NC用户Code")
    private String ncUserCode;

    /**
     * photo_file_id
     * 用户头像-上传的文件id
     */
    @ApiModelProperty(value="用户头像-上传的文件id")
    @TableField(value="photo_file_id")
    @Column(type = DataType.VARCHAR, length = 128,  isNull = true, comment = "用户头像-上传的文件id")
    private String photoFileId;

    /**
     * user_class
     * 用户分类
     */
    @ApiModelProperty(value="用户分类")
    @TableField(value="user_class")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "用户分类")
    private Integer userClass;

    //===============================股份人员数据开始===================================
    /**
     * gf_hr_code
     * 人力人员编码
     */
    @ApiModelProperty(value="人力人员编码")
    @TableField(value="gf_hr_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "人力人员编码")
    private String gfHrCode;
    /**
     * gf_post
     * 职位编码
     */
    @ApiModelProperty(value="职位编码")
    @TableField(value="gf_post")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "职位编码")
    private String gfPost;
    /**
     * gf_entry_time
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    @TableField(value="gf_entry_time")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "入职时间")
    private String gfEntryTime;
    /**
     * gf_age
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    @TableField(value="gf_age")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "年龄")
    private Integer gfAge;
    /**
     * gf_title
     * 头衔
     */
    @ApiModelProperty(value="头衔")
    @TableField(value="gf_title")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "头衔")
    private String gfTitle;
    /**
     * gf_title_level
     * 头衔级别
     */
    @ApiModelProperty(value="头衔级别")
    @TableField(value="gf_title_level")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "头衔级别")
    private String gfTitleLevel;
    /**
     * gf_employee_type
     * 员工类别
     */
    @ApiModelProperty(value="员工类别")
    @TableField(value="gf_employee_type")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "员工类别")
    private String gfEmployeeType;
    /**
     * gf_employee_dict
     * 人员分类
     */
    @ApiModelProperty(value="人员分类")
    @TableField(value="gf_employee_dict")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "人员分类")
    private String gfEmployeeDict;
    /**
     * gf_employee_status
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    @TableField(value="gf_employee_status")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "员工状态")
    private String gfEmployeeStatus;
    /**
     * gf_political_outlook
     * 政治前景
     */
    @ApiModelProperty(value="政治前景")
    @TableField(value="gf_political_outlook")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "政治前景")
    private String gfPoliticalOutlook;
    /**
     * gf_graduation_time
     * 毕业时间
     */
    @ApiModelProperty(value="毕业时间")
    @TableField(value="gf_graduation_time")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "毕业时间")
    private String gfGraduationTime;
    /**
     * gf_degree_education
     * 学位
     */
    @ApiModelProperty(value="学位")
    @TableField(value="gf_degree_education")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "学位")
    private String gfDegreeEducation;
    /**
     * gf_job_time
     * 工作时间
     */
    @ApiModelProperty(value="工作时间")
    @TableField(value="gf_job_time")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "工作时间")
    private String gfJobTime;
    /**
     * gf_jobclassification
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    @TableField(value="gf_jobclassification")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "岗位")
    private String gfJobclassification;
    /**
     * gf_talentcategory
     * 人才类别
     */
    @ApiModelProperty(value="人才类别")
    @TableField(value="gf_talentcategory")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "人才类别")
    private String gfTalentcategory;
    /**
     * gf_province
     * 人员省份编码
     */
    @ApiModelProperty(value="人员省份编码")
    @TableField(value="gf_province")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "人员省份编码")
    private String gfProvince;
    /**
     * gf_quit_time
     * 离职时间
     */
    @ApiModelProperty(value="离职时间")
    @TableField(value="gf_quit_time")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "离职时间")
    private String gfQuitTime;
    /**
     * gf_administrative_org
     * 行政组织：UM
     */
    @ApiModelProperty(value="行政组织：UM")
    @TableField(value="gf_administrative_org")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "行政组织：UM")
    private String gfAdministrativeOrg;
    /**
     * gf_norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="gf_norder")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "排序")
    private Integer gfNorder;

    //===============================股份人员数据开始===================================

    //===============================在线学习人员数据开始===================================
    /**
     * unit_name
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    @TableField(value="unit_name")
    @Column(type = DataType.INT, length = 20,  isNull = true, comment = "单位名称")
    private String unitName;

    /**
     * user_condition_code
     * 人员情况
     */
    @ApiModelProperty(value="人员情况")
    @TableField(value="user_condition_code")
    @Column(type = DataType.INT, length = 50,  isNull = true, comment = "人员情况")
    private String userConditionCode;

    /**
     * user_condition_name
     * 人员情况
     */
    @ApiModelProperty(value="人员情况")
    @TableField(value="user_condition_name")
    @Column(type = DataType.INT, length = 20,  isNull = true, comment = "人员情况")
    private String userConditionName;

    //===============================在线学习人员数据结束===================================
}
