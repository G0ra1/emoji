package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
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
 * @Description $新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@Data
@Table(value = "incloud_base_portal_content_news",comment = "新闻通告类内容发布")
@TableName("incloud_base_portal_content_news")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新闻通告类内容发布 Entity")
public class PortalContentNews extends IModel<PortalContentNews> {

    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @TableField(value="portal_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @TableField(value="portal_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @TableField(value="part_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属栏目ID")
    private Long partId;
    /**
     * part_code
     * 所属栏目Code
     */
    @ApiModelProperty(value="所属栏目code")
    @TableField(value="part_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "所属栏目code")
    private String partCode;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    @TableField(value="part_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @TableField(value="part_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @TableField(value="part_type_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "栏目类型名称")
    private String partTypeName;
    /**
     * title
     * 标题
     */
    @ApiModelProperty(value="标题")
    @TableField(value="title")
    @Column(type = DataType.VARCHAR, length = 1024,  isNull = true, comment = "标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "摘要")
    private String description;
    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")
    @TableField(value="content_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "内容URL 新闻、通告类对应html路径;")
    private String contentUrl;
//    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//    @TableField(value="audit_status")
//    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "审批状态")
//    private Integer auditStatus;
    /**
     * pass_time
     * 审批通过时间
     */
    @ApiModelProperty(value = "审批通过时间")
    @TableField(value = "pass_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "审批通过时间")
    private LocalDateTime passTime;
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
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")
    @TableField(value="ueditor_content")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "富文本编辑器内容")
    private String ueditorContent;
    ///////////////////////////////////oa同步数据字段///////////////////////////////////////
    /**
     * oa_id
     * 同步oa数据的主键
     */
    @ApiModelProperty(value = "同步oa数据的主键")
    @TableField(value = "oa_id")
    @Column(type = DataType.VARCHAR, length = 20, isNull = true, comment = "同步oa数据的主键")
    private String oaId;
    /**
     * plate_name
     * 所属板块
     */
    @ApiModelProperty(value = "所属板块")
    @TableField(value = "plate_name")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "所属板块")
    private String plateName;
    /**
     * oa_news_range_type
     * oa新闻范围类型 1：全集团 2：本单位
     */
    @ApiModelProperty(value = "oa新闻范围类型 1：全集团 2：本单位")
    @TableField(value = "oa_news_range_type")
    @Column(type = DataType.VARCHAR, length = 1, isNull = true, comment = "oa新闻范围类型 1：全集团 2：本单位")
    private Integer oaNewsRangeType;
    /**
     * org_range
     * 机构范围
     */
    @ApiModelProperty(value = "机构范围")
    @TableField(value = "org_range")
    @Column(type = DataType.TEXT, length = 0, isNull = true, comment = "机构范围")
    private String orgRange;
    /**
     * dept_range
     * 部门范围
     */
    @ApiModelProperty(value = "部门范围")
    @TableField(value = "dept_range")
    @Column(type = DataType.TEXT, length = 0, isNull = true, comment = "部门范围")
    private String deptRange;
    /**
     * user_range
     * 人员范围
     */
    @ApiModelProperty(value = "人员范围")
    @TableField(value = "user_range")
    @Column(type = DataType.TEXT, length = 0, isNull = true, comment = "人员范围")
    private String userRange;
    /**
     * data_source
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    @TableField(value = "data_source")
    @Column(type = DataType.VARCHAR, length = 25, isNull = true, comment = "数据来源")
    private String dataSource;

}
