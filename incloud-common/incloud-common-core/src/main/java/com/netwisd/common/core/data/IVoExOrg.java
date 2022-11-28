package com.netwisd.common.core.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: current package com.netwisd.common.core.data
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/19 10:05 下午
 */
@ApiModel(value = "公共Vo")
@Data
public class IVoExOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

    /**
     * 创建日期
     */
    @ApiModelProperty( value="create_time" )
    public LocalDateTime createTime;

    /**
     * 修改日期
     */
    @ApiModelProperty( value="update_time" )
    public LocalDateTime updateTime;

    /**
     * ...
     */
}
