package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
@Data
@ApiModel(value = "栏目类型字典 Vo")
public class PortalPartTypeVo extends IVo{

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
    /**
     * part_type_code
     * 栏目类型CODE
     */
    @ApiModelProperty(value="栏目类型CODE")
    private String partTypeCode;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
}
