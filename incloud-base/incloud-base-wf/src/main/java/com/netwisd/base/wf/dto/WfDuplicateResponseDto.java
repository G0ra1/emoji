package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 传阅回复 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
@Data
@ApiModel(value = "传阅回复 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfDuplicateResponseDto extends IDto{


    /**
     * in_duplicate_task_id
     * 发起的任务id
     */
    @ApiModelProperty(value="发起的任务id")
    @Valid(length = 20) 
    private Long inDuplicateTaskId;

    /**
     * ownner
     * 发起人id
     */
    @ApiModelProperty(value="发起人id")
    
    private String ownner;

    /**
     * ownner_name
     * 发起人名称
     */
    @ApiModelProperty(value="发起人名称")
    
    private String ownnerName;

    /**
     * out_duplicate_task_id
     * 接收人任务id
     */
    @ApiModelProperty(value="接收人任务id")
    
    private Long outDuplicateTaskId;

    /**
     * assignee
     * 接收人id
     */
    @ApiModelProperty(value="接收人id")
    
    private String assignee;

    /**
     * assignee_name
     * 接收人名称
     */
    @ApiModelProperty(value="接收人名称")
    
    private String assigneeName;

    /**
     * content
     * 内容
     */
    @ApiModelProperty(value="内容")
    
    private String content;

}
