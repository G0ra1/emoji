package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.base.portal.entity.PortalContentVideosSon;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.wf.starter.dto.WfDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description 视频类内容发布 功能描述...
 * @date 2021-08-23 14:08:28
 */
@Data
@ApiModel(value = " 视频类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentVideosDto extends IDto {


    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value = "所属门户ID")
    @Valid(length = 20)
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value = "门户名称")
    @Valid(length = 32)
    private String portalName;

    /**
     * title
     * 新闻标题
     */
    @ApiModelProperty(value="新闻标题")
    private String title;

    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value = "所属栏目ID")
    @Valid(length = 20)
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
    @ApiModelProperty(value = "所属栏目NAME")
    @Valid(length = 32)
    private String partName;

    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value = "栏目类型ID")
    @Valid(length = 20)
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value = "栏目类型名称")
    @Valid(length = 32)
    private String partTypeName;



    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")

    private Integer auditStatus;


    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value = "备注")

    private String remark;

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
     * portalContentVideosSonList
     * 子表集合
     */
    @ApiModelProperty(value = "子表集合")
    private List<PortalContentVideosSon> portalContentVideosSonList;


}
