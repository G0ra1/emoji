package com.netwisd.base.wf.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
@Data
@ApiModel(value = "事件运行参数维护 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfEventParamDto extends IDto{

    /**
     * event_id
     * 事件ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="事件ID")
    @Valid(length = 20) 
    private Long eventId;

    /**
     * param_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @Valid(length = 50) 
    private String paramType;


    /**
     * var_name
     * 变量名称
     */
    @ApiModelProperty(value="变量名称")
    @Valid(length = 100) 
    private String paramName;

    /**
     * var_defalut_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    private String paramDefalutValue;

    /**
     * param_id
     * 参数id
     */
    @ApiModelProperty(value="参数id")
    private String paramId;

//    /**
//     * is_pull_down
//     * 是否列表选值
//     */
//    @ApiModelProperty(value="是否列表选值")
//    private Integer isPullDown ;

}
