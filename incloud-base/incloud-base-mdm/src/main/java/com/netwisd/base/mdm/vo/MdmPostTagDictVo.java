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
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
@Data
@ApiModel(value = "岗位标识字典 Vo")
public class MdmPostTagDictVo extends IVo{

    /**
     * post_tag_name
     * 岗位标识
     */
    
    @ApiModelProperty(value="岗位标识")
    private String postTagName;
}
