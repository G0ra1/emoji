package com.netwisd.biz.asset.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@Data
@ApiModel(value = "资产派发任务表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialDistributeTaskDto extends IDto{

    public MaterialDistributeTaskDto(Args args){
        super(args);
    }
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="关联id")
    private Long sourceId;

    /**
     * code
     * 申请单号
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="处理人id")
    private Long manageUserId;

    /**
     * manage_user_name
     * 处理人名称
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
