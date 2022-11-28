package com.netwisd.base.common.mdm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IVoExOrg;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-24 11:36:38
 */
@Data
@ApiModel(value = "组织 Vo")
public class MdmOrgVo extends IVoExOrg {

    /**
     * org_code
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    String orgCode;
    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    String orgName;
    /**
     * node_type
     * 组织类型
     */
    @ApiModelProperty(value="组织类型")
    @Valid(length = 1)
    Integer orgType;
    /**
     * parent_org_id
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    private String parentOrgName;
    /**
     * parent_id
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    Long parentId;
    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    String parentName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    String orgFullId;
    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    String orgFullName;
    /**
     * parent_org_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    String parentOrgFullName;
    /**
     * parent_dept_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    String parentDeptFullName;
    /**
     * level
     * 层级
     */
    @ApiModelProperty(value="层级")
    Integer level;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    Integer sort;
    /**
     * has_kids
     * 是否有子集
     */
    @ApiModelProperty(value="是否有子集")
    Integer hasKids;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    Integer status;
    /**
     * org_level_id
     * 组织层级——对应业务上的组织类型- 一级部门 二级部门之类的
     */
    @ApiModelProperty(value="组织层级ID——对应业务上的组织类型- 一级部门 二级部门之类的")
    Integer orgLevelId;
    /**
     * org_type
     * 组织层级——对应业务上的组织类型- 一级部门 二级部门之类的
     */
    @ApiModelProperty(value="组织层级——对应业务上的组织类型- 一级部门 二级部门之类的")
    Integer orgLevel;
    /**
     * org_property
     * 组织性质
     */
    @ApiModelProperty(value="组织性质")
    Integer orgProperty;
    /**
     * satrap_id
     * 主管人ID
     */
    @ApiModelProperty(value="主管人ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    Long satrapId;
    /**
     * satrap_name
     * 主管人姓名
     */
    @ApiModelProperty(value="主管人姓名")
    String satrapName;
    /**
     * lead_time
     * 筹建时间
     */
    @ApiModelProperty(value="筹建时间")
    LocalDateTime leadTime;
    /**
     * lead_approve_code
     * 筹建批准文号
     */
    @ApiModelProperty(value="筹建批准文号")
    String leadApproveCode;
    /**
     * setup_time
     * 成立时间
     */
    @ApiModelProperty(value="成立时间")
    LocalDateTime setupTime;
    /**
     * setup_appove_code
     * 成立批准文号
     */
    @ApiModelProperty(value="成立批准文号")
    String setupAppoveCode;
    /**
     * org_addr
     * 组织地址
     */
    @ApiModelProperty(value="组织地址")
    String orgAddr;
    /**
     * org_region
     * 地级所在地区
     */
    @ApiModelProperty(value="地级所在地区")
    String orgRegion;
    /**
     * org_post
     * 邮政编码
     */
    @ApiModelProperty(value="邮政编码")
    String orgPost;
    /**
     * org_phone
     * 电话号码
     */
    @ApiModelProperty(value="电话号码")
    String orgPhone;
    /**
     * org_fax
     * 传真号码
     */
    @ApiModelProperty(value="传真号码")
    String orgFax;
    /**
     * org_duty
     * 组织职责
     */
    @ApiModelProperty(value="组织职责")
    String orgDuty;
    /**
     * register_name
     * 注册名称
     */
    @ApiModelProperty(value="注册名称")
    String registerName;
    /**
     * register_credit_code
     * 统一社会代码
     */
    @ApiModelProperty(value="统一社会代码")
    String registerCreditCode;
    /**
     * register_legal_person_id
     * 法人ID
     */
    @ApiModelProperty(value="法人ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    Long registerLegalPersonId;
    /**
     * register_legal_person_name
     * 法人名称
     */
    @ApiModelProperty(value="法人名称")
    String registerLegalPersonName;
    /**
     * register_register_time
     * 注册时间
     */
    @ApiModelProperty(value="注册时间")
    LocalDateTime registerRegisterTime;
    /**
     * register_capital
     * 注册资本
     */
    @ApiModelProperty(value="注册资本")
    String registerCapital;
    /**
     * register_biz_addr
     * 营业地址
     */
    @ApiModelProperty(value="营业地址")
    String registerBizAddr;
    /**
     * register_biz_time
     * 营业期限
     */
    @ApiModelProperty(value="营业期限")
    LocalDateTime registerBizTime;
    /**
     * register_biz_scope
     * 经营范围
     */
    @ApiModelProperty(value="经营范围")
    String registerBizScope;
    /**
     * register_certificate_time
     * 发证时间
     */
    @ApiModelProperty(value="发证时间")
    LocalDateTime registerCertificateTime;
    /**
     * register_register_org
     * 登记单位
     */
    @ApiModelProperty(value="登记单位")
    String registerRegisterOrg;
    /**
     * register_scan_file_id
     * 扫描件
     */
    @ApiModelProperty(value="扫描件")
    @JsonSerialize(using = IdTypeSerializer.class)
    Long registerScanFileId;
    /**
     * register_scan_file_url
     * 扫描件地址
     */
    @ApiModelProperty(value="扫描件地址")
    String registerScanFileUrl;
    /**
     * license_code
     * 许可证编码
     */
    @ApiModelProperty(value="许可证编码")
    String licenseCode;
    /**
     * license_time
     * 注册时间
     */
    @ApiModelProperty(value="注册时间")
    LocalDateTime licenseTime;
    /**
     * license_addr
     * 注册地址
     */
    @ApiModelProperty(value="注册地址")
    String licenseAddr;
    /**
     * license_certificate_org
     * 发证机关
     */
    @ApiModelProperty(value="发证机关")
    String licenseCertificateOrg;
    /**
     * license_certificate_time
     * 发证时间
     */
    @ApiModelProperty(value="发证时间")
    LocalDateTime licenseCertificateTime;
    /**
     * license_scan_file_id
     * 扫描件
     */
    @ApiModelProperty(value="扫描件")
    @JsonSerialize(using = IdTypeSerializer.class)
    Long licenseScanFileId;
    /**
     * license_scan_file_url
     * 扫描件地址
     */
    @ApiModelProperty(value="扫描件地址")
    String licenseScanFileUrl;

    /**
     * modify_time
     * 变更时间
     */
    @ApiModelProperty(value="变更时间")
    private LocalDateTime modifyTime;

    /**
     * modify_reason
     * 变更原因
     */
    @ApiModelProperty(value="变更原因")
    private String modifyReason;

    /**
     * nc_org_name
     * NC组织名称
     */
    @ApiModelProperty(value="NC组织名称")
    private String ncOrgName;

    /**
     * nc_org_code
     * NC组织Code
     */
    @ApiModelProperty(value="NC组织Code")
    private String ncOrgCode;

    //=============股份字段============================================
    /**
     * gf_orgcode
     * 机构编码
     */
    @ApiModelProperty(value="机构编码")
    private String gfOrgcode;

    /**
     * gf_orgname
     * 机构名称
     */
    @ApiModelProperty(value="机构名称")
    private String gfOrgname;

    /**
     * gf_orgtype
     * 机构类型
     */
    @ApiModelProperty(value="机构类型")
    private String gfOrgtype;

    /**
     * gf_asparorgcode
     * 父级机构编码
     */
    @ApiModelProperty(value="父级机构编码")
    private String gfAsparorgcode;

    /**
     * gf_starttime
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private String gfStarttime;

    /**
     * gf_end_date
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String gfEndDate;

    /**
     * gf_lastupdatetime
     * 最后更新时间
     */
    @ApiModelProperty(value="最后更新时间")
    private String gfLastupdatetime;

    /**
     * gf_institutional
     * 机构性质：XZ
     */
    @ApiModelProperty(value="机构性质：XZ")
    private String gfInstitutional;

    /**
     * gf_norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer gfNorder;
    //=============股份字段结束============================================

    /**
     * valid_start_time
     * 机构有效开始时间
     */
    @ApiModelProperty(value="机构有效开始时间")
    private LocalDateTime validStartTime;

    /**
     * valid_end_time
     * 机构有效结束时间
     */
    @ApiModelProperty(value="机构有效结束时间")
    private LocalDateTime validEndTime;

    /**
     * qy_we_chat_dept_id
     * 企业微信部门id
     */
    @ApiModelProperty(value="企业微信部门id")
    private Integer qyWeChatDeptId;

    /**
     * org_class
     * 机构分类
     */
    @ApiModelProperty(value="机构分类")
    private Integer orgClass;

    /**
     * lv_type
     * geps 和 oa 使用的级别类型
     */
    @ApiModelProperty(value="geps 和 oa 使用的级别类型; 1建设公司;2 分公司;3项目部;4职能部门")
    @TableField(value="lv_type")
    private Integer lvType;

    /**
     * geps_jc_org_name
     * geps集采机构名称
     */
    @ApiModelProperty(value="geps集采机构名称")
    private String gepsJcOrgName;

    /**
     * geps_jc_org_id
     * geps集采机构id
     */
    @ApiModelProperty(value="geps集采机构id")
    private String gepsJcOrgId;

    /**
     * oa_lv_type
     * 单独 oa使用的级别类型
     */
    @ApiModelProperty(value="oa 使用的级别类型; 1建设公司;2 分公司;3项目部;4职能部门")
    private Integer oaLvType;
}
