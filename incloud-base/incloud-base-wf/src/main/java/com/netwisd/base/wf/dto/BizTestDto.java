package com.netwisd.base.wf.dto;

import com.netwisd.base.wf.starter.dto.WfDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/28 12:08 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizTestDto extends WfDto {
    private String userName;
    private String taskId;
    private String targetActivityId;
    private String targetAssignee;
    private LocalDateTime time;
}
