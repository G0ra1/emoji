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
 * @Description $历史 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:24:33
 */
@Data
@Table(value = "incloud_base_portal_content_his_picnews",comment = "历史 图片新闻类内容发布")
@TableName("incloud_base_portal_content_his_picnews")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "历史 图片新闻类内容发布 Entity")
public class PortalContentHisPicnews extends IModel<PortalContentHisPicnews> {

    /**
     * link_picnews_id
     * 所属主表ID
     */
    @ApiModelProperty(value="所属主表ID")
    @TableField(value="link_picnews_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属主表ID")
    private Long linkPicnewsId;
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
     * title
     * 标题
     */
    @ApiModelProperty(value="标题")
    @TableField(value="title")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "摘要")
    private String description;
    /**
     * img_url
     * 缩略图
     */
    @ApiModelProperty(value="缩略图")
    @TableField(value="img_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "缩略图")
    private String imgUrl;
    /**
     * file_id
     * 文件ID
     */
    @ApiModelProperty(value="文件ID")
    @TableField(value="file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "文件ID")
    private Long fileId;
    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")
    @TableField(value="content_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "内容URL 新闻、通告类对应html路径;")
    private String contentUrl;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    @TableField(value="audit_status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "审批状态")
    private Integer auditStatus;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    @TableField(value="hits")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "点击量")
    private Long hits;
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
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")
    @TableField(value="ueditor_content")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "富文本编辑器内容")
    private String ueditorContent;
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
