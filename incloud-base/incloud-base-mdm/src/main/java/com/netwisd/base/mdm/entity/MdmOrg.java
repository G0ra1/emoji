package com.netwisd.base.mdm.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModelExOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * @Description $组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-24 11:36:38
 */
@Data
@Table(value = "incloud_base_mdm_org",comment = "组织")
@TableName("incloud_base_mdm_org")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "组织 Entity")
public class MdmOrg extends IModelExOrg<MdmOrg> {

    /**
     * org_code
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    @TableField(value="org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "组织代码")
    private String orgCode;
    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "组织名称")
    private String orgName;
    /**
     * org_Type
     * 组织节点类型
     */
    @ApiModelProperty(value="组织节点类型")
    @TableField(value="org_Type")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "组织节点类型")
    private Integer orgType;
    /**
     * parent_org_id
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    @TableField(value="parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "父级ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    @TableField(value="parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "父级名称")
    private String parentOrgName;
    /**
     * parent_id
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    @TableField(value="parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "父级ID")
    private Long parentId;
    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    @TableField(value="parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "父级名称")
    private String parentName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="org_full_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "父级组织全路径ID")
    private String orgFullId;
    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="org_full_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "父级组织全路径名称")
    private String orgFullName;
    /**
     * parent_org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="parent_org_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级组织全路径名称")
    private String parentOrgFullName;
    /**
     * parent_dept_full_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    @TableField(value="parent_dept_full_name",updateStrategy = FieldStrategy.IGNORED,insertStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级部门全路径名称")
    private String parentDeptFullName;
    /**
     * level
     * 层级
     */
    @ApiModelProperty(value="层级")
    @TableField(value="level")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "层级")
    private Integer level;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
    /**
     * has_kids
     * 是否有子集
     */
    @ApiModelProperty(value="是否有子集")
    @TableField(value="has_kids")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否有子集")
    private Integer hasKids;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "状态标识")
    private Integer status;
    /**
     * org_level_id
     * 组织层级——对应业务上的组织类型- 一级部门 二级部门之类的
     */
    @ApiModelProperty(value="组织层级ID——对应业务上的组织类型- 一级部门 二级部门之类的")
    @TableField(value="org_level_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "组织层级ID——对应业务上的组织类型- 一级部门 二级部门之类的")
    private Integer orgLevelId;
    /**
     * org_level
     * 组织层级——对应业务上的组织类型- 一级部门 二级部门之类的
     */
    @ApiModelProperty(value="组织层级——对应业务上的组织类型- 一级部门 二级部门之类的")
    @TableField(value="org_level")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "组织层级——对应业务上的组织类型- 一级部门 二级部门之类的")
    private Integer orgLevel;
    /**
     * org_property
     * 组织性质
     */
    @ApiModelProperty(value="组织性质")
    @TableField(value="org_property")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "组织性质")
    private Integer orgProperty;
    /**
     * satrap_id
     * 主管人ID
     */
    @ApiModelProperty(value="主管人ID")
    @TableField(value="satrap_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "主管人ID")
    private Long satrapId;
    /**
     * satrap_name
     * 主管人姓名
     */
    @ApiModelProperty(value="主管人姓名")
    @TableField(value="satrap_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "主管人姓名")
    private String satrapName;
    /**
     * lead_time
     * 筹建时间
     */
    @ApiModelProperty(value="筹建时间")
    @TableField(value="lead_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "筹建时间")
    private LocalDateTime leadTime;
    /**
     * lead_approve_code
     * 筹建批准文号
     */
    @ApiModelProperty(value="筹建批准文号")
    @TableField(value="lead_approve_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "筹建批准文号")
    private String leadApproveCode;
    /**
     * setup_time
     * 成立时间
     */
    @ApiModelProperty(value="成立时间")
    @TableField(value="setup_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "成立时间")
    private LocalDateTime setupTime;
    /**
     * setup_appove_code
     * 成立批准文号
     */
    @ApiModelProperty(value="成立批准文号")
    @TableField(value="setup_appove_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "成立批准文号")
    private String setupAppoveCode;
    /**
     * org_addr
     * 组织地址
     */
    @ApiModelProperty(value="组织地址")
    @TableField(value="org_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织地址")
    private String orgAddr;
    /**
     * org_region
     * 地级所在地区
     */
    @ApiModelProperty(value="地级所在地区")
    @TableField(value="org_region")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "地级所在地区")
    private String orgRegion;
    /**
     * org_post
     * 邮政编码
     */
    @ApiModelProperty(value="邮政编码")
    @TableField(value="org_post")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "邮政编码")
    private String orgPost;
    /**
     * org_phone
     * 电话号码
     */
    @ApiModelProperty(value="电话号码")
    @TableField(value="org_phone")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "电话号码")
    private String orgPhone;
    /**
     * org_fax
     * 传真号码
     */
    @ApiModelProperty(value="传真号码")
    @TableField(value="org_fax")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "传真号码")
    private String orgFax;
    /**
     * org_duty
     * 组织职责
     */
    @ApiModelProperty(value="组织职责")
    @TableField(value="org_duty")
    @Column(type = DataType.VARCHAR, length = 800,  isNull = true, comment = "组织职责")
    private String orgDuty;
    /**
     * register_name
     * 注册名称
     */
    @ApiModelProperty(value="注册名称")
    @TableField(value="register_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "注册名称")
    private String registerName;
    /**
     * register_credit_code
     * 统一社会代码
     */
    @ApiModelProperty(value="统一社会代码")
    @TableField(value="register_credit_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "统一社会代码")
    private String registerCreditCode;
    /**
     * register_legal_person_id
     * 法人ID
     */
    @ApiModelProperty(value="法人ID")
    @TableField(value="register_legal_person_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "法人ID")
    private Long registerLegalPersonId;
    /**
     * register_legal_person_name
     * 法人名称
     */
    @ApiModelProperty(value="法人名称")
    @TableField(value="register_legal_person_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "法人名称")
    private String registerLegalPersonName;
    /**
     * register_register_time
     * 注册时间
     */
    @ApiModelProperty(value="注册时间")
    @TableField(value="register_register_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "注册时间")
    private LocalDateTime registerRegisterTime;
    /**
     * register_capital
     * 注册资本
     */
    @ApiModelProperty(value="注册资本")
    @TableField(value="register_capital")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "注册资本")
    private String registerCapital;
    /**
     * register_biz_addr
     * 营业地址
     */
    @ApiModelProperty(value="营业地址")
    @TableField(value="register_biz_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "营业地址")
    private String registerBizAddr;
    /**
     * register_biz_time
     * 营业期限
     */
    @ApiModelProperty(value="营业期限")
    @TableField(value="register_biz_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "营业期限")
    private LocalDateTime registerBizTime;
    /**
     * register_biz_scope
     * 经营范围
     */
    @ApiModelProperty(value="经营范围")
    @TableField(value="register_biz_scope")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "经营范围")
    private String registerBizScope;
    /**
     * register_certificate_time
     * 发证时间
     */
    @ApiModelProperty(value="发证时间")
    @TableField(value="register_certificate_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "发证时间")
    private LocalDateTime registerCertificateTime;
    /**
     * register_register_org
     * 登记单位
     */
    @ApiModelProperty(value="登记单位")
    @TableField(value="register_register_org")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "登记单位")
    private String registerRegisterOrg;
    /**
     * register_scan_file_id
     * 扫描件
     */
    @ApiModelProperty(value="扫描件")
    @TableField(value="register_scan_file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "扫描件")
    private Long registerScanFileId;
    /**
     * register_scan_file_url
     * 扫描件地址
     */
    @ApiModelProperty(value="扫描件地址")
    @TableField(value="register_scan_file_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "扫描件地址")
    private String registerScanFileUrl;
    /**
     * license_code
     * 许可证编码
     */
    @ApiModelProperty(value="许可证编码")
    @TableField(value="license_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "许可证编码")
    private String licenseCode;
    /**
     * license_time
     * 注册时间
     */
    @ApiModelProperty(value="注册时间")
    @TableField(value="license_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "注册时间")
    private LocalDateTime licenseTime;
    /**
     * license_addr
     * 注册地址
     */
    @ApiModelProperty(value="注册地址")
    @TableField(value="license_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "注册地址")
    private String licenseAddr;
    /**
     * license_certificate_org
     * 发证机关
     */
    @ApiModelProperty(value="发证机关")
    @TableField(value="license_certificate_org")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "发证机关")
    private String licenseCertificateOrg;
    /**
     * license_certificate_time
     * 发证时间
     */
    @ApiModelProperty(value="发证时间")
    @TableField(value="license_certificate_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "发证时间")
    private LocalDateTime licenseCertificateTime;
    /**
     * license_scan_file_id
     * 扫描件
     */
    @ApiModelProperty(value="扫描件")
    @TableField(value="license_scan_file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "扫描件")
    private Long licenseScanFileId;
    /**
     * license_scan_file_url
     * 扫描件地址
     */
    @ApiModelProperty(value="扫描件地址")
    @TableField(value="license_scan_file_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "扫描件地址")
    private String licenseScanFileUrl;

    //=============股份字段============================================
    /**
     * gf_orgcode
     * 机构编码
     */
    @ApiModelProperty(value="机构编码")
    @TableField(value="gf_orgcode")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "机构编码")
    private String gfOrgcode;

    /**
     * gf_orgname
     * 机构名称
     */
    @ApiModelProperty(value="机构名称")
    @TableField(value="gf_orgname")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "机构名称")
    private String gfOrgname;

    /**
     * gf_orgtype
     * 机构类型
     */
    @ApiModelProperty(value="机构类型")
    @TableField(value="gf_orgtype")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "机构类型")
    private String gfOrgtype;

    /**
     * gf_asparorgcode
     * 父级机构编码
     */
    @ApiModelProperty(value="父级机构编码")
    @TableField(value="gf_asparorgcode")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "父级机构编码")
    private String gfAsparorgcode;

    /**
     * gf_starttime
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    @TableField(value="gf_starttime")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "开始时间")
    private String gfStarttime;

    /**
     * gf_end_date
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    @TableField(value="gf_end_date")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "结束时间")
    private String gfEndDate;

    /**
     * gf_lastupdatetime
     * 最后更新时间
     */
    @ApiModelProperty(value="最后更新时间")
    @TableField(value="gf_lastupdatetime")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "最后更新时间")
    private String gfLastupdatetime;

    /**
     * gf_institutional
     * 机构性质：XZ
     */
    @ApiModelProperty(value="机构性质：XZ")
    @TableField(value="gf_institutional")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "机构性质：XZ")
    private String gfInstitutional;

    /**
     * gf_norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="gf_norder")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "排序")
    private Integer gfNorder;
    //=============股份字段结束============================================


    /**
     * modify_time
     * 变更时间
     */
    @ApiModelProperty(value="变更时间")
    @TableField(value="modify_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "变更时间")
    private LocalDateTime modifyTime;

    /**
     * modify_reason
     * 变更原因
     */
    @ApiModelProperty(value="变更原因")
    @TableField(value="modify_reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "变更原因")
    private String modifyReason;

    /**
     * nc_org_name
     * NC组织名称
     */
    @ApiModelProperty(value="NC组织名称")
    @TableField(value="nc_org_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "NC组织名称")
    private String ncOrgName;

    /**
     * nc_org_code
     * NC组织Code
     */
    @ApiModelProperty(value="NC组织Code")
    @TableField(value="nc_org_code")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "NC组织Code")
    private String ncOrgCode;

    /**
     * valid_start_time
     * 机构有效开始时间
     */
    @ApiModelProperty(value="机构有效开始时间")
    @TableField(value="valid_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "机构有效开始时间")
    private LocalDateTime validStartTime;

    /**
     * valid_end_time
     * 机构有效结束时间
     */
    @ApiModelProperty(value="机构有效结束时间")
    @TableField(value="valid_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "机构有效结束时间")
    private LocalDateTime validEndTime;


    /**
     * lv_type
     * geps 和 oa 使用的级别类型
     */
    @ApiModelProperty(value="geps 和 oa 使用的级别类型; 1建设公司;2 分公司;3项目部;4职能部门")
    @TableField(value="lv_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "geps 和 oa 使用的级别类型;1建设公司;2 分公司;3项目部;4职能部门")
    private Integer lvType;

    /**
     * geps_jc_org_name
     * geps集采机构名称
     */
    @ApiModelProperty(value="geps集采机构名称")
    @TableField(value="geps_jc_org_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "geps集采机构名称")
    private String gepsJcOrgName;

    /**
     * geps_jc_org_id
     * geps集采机构id
     */
    @ApiModelProperty(value="geps集采机构id")
    @TableField(value="geps_jc_org_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "geps集采机构id")
    private String gepsJcOrgId;

    /**
     * oa_lv_type
     * 单独 oa使用的级别类型
     */
    @ApiModelProperty(value="oa 使用的级别类型; 1建设公司;2 分公司;3项目部;4职能部门")
    @TableField(value="oa_lv_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "oa 使用的级别类型;1建设公司;2 分公司;3项目部;4职能部门")
    private Integer oaLvType;


    /**
     * qy_we_chat_dept_id
     * 企业微信部门id
     */
    @ApiModelProperty(value="企业微信部门id")
    @TableField(value="qy_we_chat_dept_id")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "企业微信部门id")
    private Integer qyWeChatDeptId;

    /**
     * org_class
     * 机构分类
     */
    @ApiModelProperty(value="机构分类")
    @TableField(value="org_class")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "机构分类")
    private Integer orgClass;

}
