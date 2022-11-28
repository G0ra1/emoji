package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 集成友报账-已办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:59:08
 */
@Data
@ApiModel(value = "集成友报账-已办 Vo")
public class PortalYbzDoneTasksVo extends IVo{

    /**
     * id_card
     * 身份证号
     */
    
    @ApiModelProperty(value="身份证号")
    private String idCard;
    /**
     * phone_num
     * 手机号
     */
    
    @ApiModelProperty(value="手机号")
    private String phoneNum;
    /**
     * user_name_ch
     * 用户名称
     */
    
    @ApiModelProperty(value="用户名称")
    private String userNameCh;
    /**
     * title
     * 标题
     */
    
    @ApiModelProperty(value="标题")
    private String title;
    /**
     * content
     * 内容
     */
    
    @ApiModelProperty(value="内容")
    private String content;
    /**
     * content_url
     * 内容路径
     */
    
    @ApiModelProperty(value="内容路径")
    private String contentUrl;

    /**
     * ybz_id
     * 友报账唯一标识
     */
    @ApiModelProperty(value="友报账唯一标识")
    private String ybzId;
}
