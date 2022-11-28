package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@Data
@ApiModel(value = "资产派发任务表 Vo")
public class MaterialDistributeTaskVo extends IVo{

    /**
     * type
     * 任务类型
     */
    
    @ApiModelProperty(value="任务类型")
    private Integer type;
    /**
     * source_id
     * 关联id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="关联id")
    private Long sourceId;
    /**
     * code
     * 申请单号
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="申请单号")
    private Long code;
    /**
     * apply_time
     * 申请日期
     */
    
    @ApiModelProperty(value="申请日期")
    private LocalDateTime applyTime;
    /**
     * manage_user_id
     * 处理人id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="处理人id")
    private Long manageUserId;
    /**
     * manage_user_name
     * 处理人名称
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="处理人名称")
    private Long manageUserName;
    /**
     * reason
     * 事由
     */
    
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * form_url
     * 表单url
     */
    
    @ApiModelProperty(value="表单url")
    private String formUrl;
    /**
     * status
     * 状态
     */
    
    @ApiModelProperty(value="状态")
    private Integer status;
}
