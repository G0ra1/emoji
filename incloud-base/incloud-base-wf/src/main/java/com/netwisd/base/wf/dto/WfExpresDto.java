package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Data
@ApiModel(value = "表达式维护 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfExpresDto implements IValidation {

    @Valid
    private WfExpreDto wfExpreDto;

    private List<WfExpreParamDto> list;


}
