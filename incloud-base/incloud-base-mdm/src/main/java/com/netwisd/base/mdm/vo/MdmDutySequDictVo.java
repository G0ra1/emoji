package com.netwisd.base.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@ApiModel(value = "职务序列字典 Vo")
public class MdmDutySequDictVo extends IVo{

    /**
     * duty_sequ_name
     * 职务序列
     */
    
    @ApiModelProperty(value="职务序列")
    private String dutySequName;
}
