package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/3 10:25 上午
 */
@Data
public class WfEventParamRuntimeVo extends IVo {
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
    private String paramValue;

    /**
     * param_id
     * 参数id
     */
    @ApiModelProperty(value="参数id")
    private String paramId;
}
