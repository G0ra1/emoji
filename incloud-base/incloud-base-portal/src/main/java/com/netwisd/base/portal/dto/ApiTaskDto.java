package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 云数网讯 XHL@netwisd.com
 * @Description 同步待办传阅Dto
 * @date 2021-01-14 09:51:13
 */
@Data
@ApiModel(value = "文件存储  Dto")
public class ApiTaskDto {


    /**
     * sys_biz_id
     * 所传数据的系统业务id
     */
    @ApiModelProperty(value = "所传数据的系统业务id")
    private String sysBizId;

    /**
     * sys_biz_code
     * 业务系统code
     */
    @ApiModelProperty(value = "sys_biz_code")
    private String sysBizCode;


}
