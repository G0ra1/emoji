package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import lombok.Data;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 1:13 下午
 */
@Data
public class WfTestValidateDto implements IValidation {
    @Valid
    private String str;
    @Valid
    private List<WfTestValidateDtoDetail> list;
}
