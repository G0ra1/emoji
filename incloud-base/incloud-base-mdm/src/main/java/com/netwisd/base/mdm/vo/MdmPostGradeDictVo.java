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
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@Data
@ApiModel(value = "岗位职等 Vo")
public class MdmPostGradeDictVo extends IVo{

    /**
     * post_grade_name
     * 职等
     */
    
    @ApiModelProperty(value="职等")
    private String postGradeName;
    /**
     * post_sequ_id
     * 所属序列id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属序列id")
    private Long postSequId;
    /**
     * post_sequ_name
     * 所属序列名称
     */
    
    @ApiModelProperty(value="所属序列名称")
    private String postSequName;
}
