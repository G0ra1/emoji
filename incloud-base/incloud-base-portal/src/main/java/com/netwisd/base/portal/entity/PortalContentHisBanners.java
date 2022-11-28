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
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description $banner类内容-历史表 功能描述...
 * @date 2021-08-30 03:09:59
 */
@Data
@Table(value = "incloud_base_portal_content_his_banners", comment = "banner类内容-历史表")
@TableName("incloud_base_portal_content_his_banners")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "banner类内容-历史表 Entity")
public class PortalContentHisBanners extends IModel<PortalContentHisBanners> {

    /**
     * link_banner_id
     * 主表关联id
     */
    @ApiModelProperty(value = "主表关联id")
    @TableField(value = "link_banner_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "主表关联id")
    private Long linkBannerId;
    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value = "所属门户ID")
    @TableField(value = "portal_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value = "门户名称")
    @TableField(value = "portal_name")
    @Column(type = DataType.VARCHAR, length = 32, isNull = false, comment = "门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value = "所属栏目ID")
    @TableField(value = "part_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "所属栏目ID")
    private Long partId;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value = "所属栏目NAME")
    @TableField(value = "part_name")
    @Column(type = DataType.VARCHAR, length = 32, isNull = false, comment = "所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value = "栏目类型ID")
    @TableField(value = "part_type_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value = "栏目类型名称")
    @TableField(value = "part_type_name")
    @Column(type = DataType.VARCHAR, length = 32, isNull = false, comment = "栏目类型名称")
    private String partTypeName;
    /**
     * title
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField(value = "title")
    @Column(type = DataType.VARCHAR, length = 64, isNull = false, comment = "标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @TableField(value = "description")
    @Column(type = DataType.VARCHAR, length = 255, isNull = false, comment = "摘要")
    private String description;
    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；
     */
    @ApiModelProperty(value = "内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    @TableField(value = "content_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = false, comment = "内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    private String contentUrl;
    /**
     * img_url
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    @TableField(value = "img_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "缩略图")
    private String imgUrl;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    @TableField(value = "audit_status")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "审批状态")
    private Integer auditStatus;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    @TableField(value = "hits")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "点击量")
    private Long hits;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "备注")
    private String remark;
    /**
     * editor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value = "富文本编辑器内容")
    @TableField(value = "editor_content")
    @Column(type = DataType.TEXT, length = 0, isNull = false, comment = "富文本编辑器内容")
    private String editorContent;
    /**
     * pass_time
     * 审批通过时间
     */
    @ApiModelProperty(value = "审批通过时间")
    @TableField(value = "pass_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "审批通过时间")
    private LocalDateTime passTime;
    /**
     * up_pass_time
     * 流程编辑时间
     */
    @ApiModelProperty(value = "流程编辑时间")
    @TableField(value = "up_pass_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "流程编辑时间")
    private LocalDateTime upPassTime;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value = "camunda流程定义key")
    @TableField(value = "camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50, isNull = false, comment = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value = "流程定义名称")
    @TableField(value = "procdef_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "流程定义名称")
    private String procdefName;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value = "camunda流程定义ID")
    @TableField(value = "camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64, isNull = false, comment = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value = "流程版本")
    @TableField(value = "procdef_version")
    @Column(type = DataType.INT, length = 2, isNull = true, comment = "流程版本")
    private Integer procdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value = "camunda流程实例ID")
    @TableField(value = "camunda_procins_id")
    @Column(type = DataType.VARCHAR, length = 64, isNull = false, comment = "camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * process_name
     * 流程实例名称
     */
    @ApiModelProperty(value = "流程实例名称")
    @TableField(value = "process_name")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "流程实例名称")
    private String processName;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value = "事由")
    @TableField(value = "reason")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "事由")
    private String reason;
    /**
     * biz_key
     * bizKey
     */
    @ApiModelProperty(value = "bizKey")
    @TableField(value = "biz_key")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "bizKey")
    private String bizKey;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value = "流程分类ID")
    @TableField(value = "procdef_type_id")
    @Column(type = DataType.BIGINT, length = 50, isNull = true, comment = "流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value = "流程分类名称")
    @TableField(value = "procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "流程分类名称")
    private String procdefTypeName;


    /**
     * file_id
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID")
    @TableField(value = "file_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "文件ID")
    private Long fileId;
}
