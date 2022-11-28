package com.netwisd.base.portal.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.wf.starter.dto.WfDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description banner类内容-调整表 功能描述...
 * @date 2021-08-30 00:02:49
 */
@Data
@ApiModel(value = "banner类内容-调整表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentAdjBannersDto extends WfDto {


    /**
     * link_banner_id
     * 主表关联id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value = "主表关联id")
    private Long linkBannerId;

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value = "所属门户ID")
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */

    @Valid(length = 32)
    @ApiModelProperty(value = "门户名称")
    private String portalName;

    /**
     * part_id
     * 所属栏目ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value = "所属栏目ID")
    private Long partId;

    /**
     * part_name
     * 所属栏目NAME
     */

    @Valid(length = 32)
    @ApiModelProperty(value = "所属栏目NAME")
    private String partName;

    /**
     * part_type_id
     * 栏目类型ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value = "栏目类型ID")
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */

    @Valid(length = 32)
    @ApiModelProperty(value = "栏目类型名称")
    private String partTypeName;

    /**
     * title
     * 标题
     */

    @Valid(length = 64)
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * description
     * 摘要
     */

    @Valid(length = 255)
    @ApiModelProperty(value = "摘要")
    private String description;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；
     */

    @Valid(length = 255)
    @ApiModelProperty(value = "内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    private String contentUrl;

    /**
     * img_url
     * 缩略图
     */


    @ApiModelProperty(value = "缩略图")
    private String imgUrl;

    /**
     * audit_status
     * 审批状态
     */


    @ApiModelProperty(value = "审批状态")
    private Integer auditStatus;

    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value = "点击量")
    private Long hits;

    /**
     * remark
     * 备注
     */


    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * editor_content
     * 富文本编辑器内容
     */

    @Valid(length = 0)
    @ApiModelProperty(value = "富文本编辑器内容")
    private String editorContent;

    /**
     * pass_time
     * 审批通过时间
     */


    @ApiModelProperty(value = "审批通过时间")
    private LocalDateTime passTime;

    /**
     * up_pass_time
     * 流程编辑时间
     */


    @ApiModelProperty(value = "流程编辑时间")
    private LocalDateTime upPassTime;

    /**
     * file_id
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID")

    private Long fileId;

}
