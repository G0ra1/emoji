package com.netwisd.base.portal.entity;

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
 * @Description $历史 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:40:29
 */
@Data
@Table(value = "incloud_base_portal_content_his_sysjoints",comment = "历史 系统集成类内容发布")
@TableName("incloud_base_portal_content_his_sysjoints")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "历史 系统集成类内容发布 Entity")
public class PortalContentHisSysjoints extends IModel<PortalContentHisSysjoints> {

    /**
     * link_main_sysjoints_id
     * 主表id
     */
    @ApiModelProperty(value="主表id")
    @TableField(value="link_main_sysjoints_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "主表id")
    private Long linkMainSysjointsId;
    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @TableField(value="portal_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @TableField(value="portal_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @TableField(value="part_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属栏目ID")
    private Long partId;
    /**
     * part_name
     * 所属栏目名称
     */
    @ApiModelProperty(value="所属栏目名称")
    @TableField(value="part_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所属栏目名称")
    private String partName;
    /**
     * part_type_id
     * 所属栏目类型ID
     */
    @ApiModelProperty(value="所属栏目类型ID")
    @TableField(value="part_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @TableField(value="part_type_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目类型名称")
    private String partTypeName;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    @TableField(value="audit_status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "审批状态")
    private Integer auditStatus;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
    /**
     * pass_time
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    @TableField(value="pass_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批通过时间")
    private LocalDateTime passTime;
    /**
     * apply_user_id
     * 申请人id
     */
    @ApiModelProperty(value="申请人id")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "申请人id")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人name
     */
    @ApiModelProperty(value="申请人name")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "申请人name")
    private String applyUserName;
}