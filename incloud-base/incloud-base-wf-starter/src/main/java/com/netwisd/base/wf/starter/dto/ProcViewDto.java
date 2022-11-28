package com.netwisd.base.wf.starter.dto;

import lombok.Data;

/**
 * 查询业务Dto
 */
@Data
public class ProcViewDto {
    private String camundaProcinsId; //流程实例id
    private Integer isCallActivity; //是否是子流程
}
