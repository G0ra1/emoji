package com.netwisd.base.portal.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@Data
@ApiModel(value = "新闻通告类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentNewsDto extends IDto {

    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @Valid(length = 32)
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @Valid(length = 32)
    private String portalName;

    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @Valid(length = 32)
    private Long partId;

    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    @Valid(length = 32)
    private String partCode;

    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    @Valid(length = 32)
    private String partName;

    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @Valid(length = 32)
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @Valid(length = 32)
    private String partTypeName;

    /**
     * title
     * 标题
     */
    @ApiModelProperty(value="标题")
    @Valid(length = 32)
    private String title;

    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    @Valid(length = 32)
    private String description;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")
    @Valid(length = 32)
    private String contentUrl;

//    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//    @Valid(length = 32)
//    private Integer auditStatus;

    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    
    private Long hits;

    /**
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")

    private String ueditorContent;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")

    private String remark;

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

    ////////////////////////////移动端查询使用/////////////////////////////////

    /**
     * is_admin
     * 是否管理员
     */
    @ApiModelProperty(value = "是否管理员")
    private Integer isAdmin;
}
