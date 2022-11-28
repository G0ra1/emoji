package com.netwisd.base.portal.vo;

import com.netwisd.base.portal.entity.PortalContentHisVideosSon;
import com.netwisd.base.portal.entity.PortalContentVideosSon;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description  视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
@Data
@ApiModel(value = " 视频类内容发布-历史表 Vo")
public class PortalContentHisVideosVo extends IVo{

    /**
     * link_videos_id
     * 主表关联id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="主表关联id")
    private Long linkVideosId;
    /**
     * portal_id
     * 所属门户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属门户ID")
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属栏目ID")
    private Long partId;
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;


    /**
     * audit_status
     * 审批状态
     */
    
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;

    /**
     * remark
     * 备注
     */
    
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * pass_time
     * 审批通过时间
     */
    
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime passTime;
    /**
     * up_pass_time
     * 流程编辑时间
     */
    
    @ApiModelProperty(value="流程编辑时间")
    private LocalDateTime upPassTime;


    /**
     * portalContentHisVideosSonList
     * 子表集合
     */
    @ApiModelProperty(value = "子表集合")
    private List<PortalContentHisVideosSon> portalContentHisVideosSonList;
}
