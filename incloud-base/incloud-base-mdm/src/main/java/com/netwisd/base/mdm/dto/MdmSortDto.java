package com.netwisd.base.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/13 8:26 上午
 */
@ApiModel(value = "排序复合 Dto")
@Data
public class MdmSortDto implements IValidation {

    /**
     * 要排序的对象
     */
    @ApiModelProperty(value="要排序的对象")
    @Valid(nullMsg = "要排序的对象信息不能为空！")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long sourceId;

    /**
     * 要排序的目标对象组织
     */
    @ApiModelProperty(value="要排序的目标对象",notes = "如果index有值，则target为空不用传值")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long targetId;
    /**
     * first / last 代表最前面和最后面；
     */
    @ApiModelProperty(value="排序到第一个或最后一个",notes = "值为first / last")
    private String index;
    /**
     * first / last 代表放在目标前面还是后面，默认是前面；
     */
    /*@ApiModelProperty(value="要排序的方式",notes = "把source放到target的前面还是后面，默认是前面——目前只支持first，不用传值")
    private String targetIndex = "first";*/
}
