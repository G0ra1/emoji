package com.netwisd.base.wf.starter.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 6:13 下午
 */
@Data
public class BizInfoDto implements IValidation {
    @ApiModelProperty(value="事由")
    private String reason;

    @ApiModelProperty(value="业务key")
    private String bizKey;

    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty(value="业务json串")
    @Valid(nullMsg = "业务json串不能为空！")
    private String bizData;
}
