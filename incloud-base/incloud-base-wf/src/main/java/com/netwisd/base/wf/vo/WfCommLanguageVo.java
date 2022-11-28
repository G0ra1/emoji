package com.netwisd.base.wf.vo;

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
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@Data
@ApiModel(value = "审批时常用语 Vo")
public class WfCommLanguageVo extends IVo{
    /**
     * use_user_id
     * 使用人id
     */
    @ApiModelProperty(value="使用人id")
    private String useUserId;
    /**
     * use_user_name
     * 使用人名称
     */
    @ApiModelProperty(value="使用人名称")
    private String useUserName;
    /**
     * content
     * 常用语内容
     */
    @ApiModelProperty(value="常用语内容")
    private String content;
    /**
     * is_general
     * 是否是通用 常用语
     */
    @ApiModelProperty(value="是否是通用 常用语")
    private Integer isGeneral;
}
