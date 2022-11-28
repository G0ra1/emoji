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
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@Data
@ApiModel(value = "岗位序列 Vo")
public class MdmPostSequDictVo extends IVo{

    /**
     * post_sequ_name
     * 岗位序列
     */
    
    @ApiModelProperty(value="岗位序列")
    private String postSequName;
}
