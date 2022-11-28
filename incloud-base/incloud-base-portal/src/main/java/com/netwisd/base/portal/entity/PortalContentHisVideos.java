package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
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
 * @Description $ 视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
@Data
@Table(value = "incloud_base_portal_content_his_videos",comment = " 视频类内容发布-历史表")
@TableName("incloud_base_portal_content_his_videos")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = " 视频类内容发布-历史表 Entity")
public class PortalContentHisVideos extends IModel<PortalContentHisVideos> {

    /**
     * link_videos_id
     * 主表关联id
     */
    @ApiModelProperty(value="主表关联id")
    @TableField(value="link_videos_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "主表关联id")
    private Long linkVideosId;
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
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    @TableField(value="part_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @TableField(value="part_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "栏目类型ID")
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
     * up_pass_time
     * 流程编辑时间
     */
    @ApiModelProperty(value="流程编辑时间")
    @TableField(value="up_pass_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "流程编辑时间")
    private LocalDateTime upPassTime;


}
