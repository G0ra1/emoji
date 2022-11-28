package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
@Data
@ApiModel(value = "事件运行参数维护 Vo")
public class WfEventParamVo extends IVo{
    /**
     * event_id
     * 事件ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="事件ID")
    private Long eventId;
    /**
     * param_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    private String paramType;
    /**
     * var_name
     * 变量名称
     */
    @ApiModelProperty(value="变量名称")
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
