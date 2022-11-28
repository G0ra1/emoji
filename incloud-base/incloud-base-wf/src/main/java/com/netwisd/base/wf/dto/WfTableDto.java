package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import lombok.Data;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 1:13 下午
 */
@Data
public class WfTableDto extends IDto {
    @Valid(length = 0)
    private String dsId;
    private String tableName;
    private String tableComment;
}
