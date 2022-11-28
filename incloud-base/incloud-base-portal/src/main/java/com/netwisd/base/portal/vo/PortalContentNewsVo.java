package com.netwisd.base.portal.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@Data
@ApiModel(value = "新闻通告类内容发布 Vo")
public class PortalContentNewsVo extends IVo {

    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long partId;
    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    private String partCode;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
    /**
     * title
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    private String description;
    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")
    private String contentUrl;
//    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//    private Integer auditStatus;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    private Long hits;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")
    private String ueditorContent;
    /**
     * pass_time
     * 审批通过时间
     */
    @ApiModelProperty(value = "审批通过时间")
    private LocalDateTime passTime;
    /**
     * apply_user_id
     * 发布人ID
     */
    @ApiModelProperty(value="发布人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 发布人名称
     */
    @ApiModelProperty(value="发布人名称")
    private String applyUserName;
    ///////////////////////////////////oa同步数据字段///////////////////////////////////////
    /**
     * oa_id
     * 同步oa数据的主键
     */
    @ApiModelProperty(value = "同步oa数据的主键")
    private String oaId;
    /**
     * plate_name
     * 所属板块
     */
    @ApiModelProperty(value = "所属板块")
    private String plateName;
    /**
     * oa_news_range_type
     * oa新闻范围类型 1：全集团 2：本单位
     */
    @ApiModelProperty(value = "oa新闻范围类型 1：全集团 2：本单位")
    private Integer oaNewsRangeType;
    /**
     * org_range
     * 机构范围
     */
    @ApiModelProperty(value = "机构范围")
    private String orgRange;

    /**
     * dept_range
     * 部门范围
     */
    @ApiModelProperty(value = "部门范围")
    private String deptRange;

    /**
     * user_range
     * 用户范围
     */
    @ApiModelProperty(value = "用户范围")
    private String userRange;

    /**
     * data_source
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String dataSource;
}
