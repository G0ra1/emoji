package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
@Data
@ApiModel(value = "模板管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalTemplateDto extends IDto {

    public PortalTemplateDto(Args args){
        super(args);
    }

    /**
     * portal_id
     * 所属门户
     */
    @ApiModelProperty(value="所属门户")
    
    private Long portalId;

    /**
     * portal_name
     * 所属门户名称
     */
    @ApiModelProperty(value="所属门户名称")
    
    private String portalName;

    /**
     * template_name
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    
    private String templateName;

    /**
     * template_code
     * 模板CODE
     */
    @ApiModelProperty(value="模板CODE")
    
    private String templateCode;

    /**
     * terminal
     * 所属终端 0PC 1移动；所属终端
     */
    @ApiModelProperty(value="所属终端 0PC 1移动；所属终端")
    
    private Integer terminal;

    /**
     * template_data
     * 模板内容
     */
    @ApiModelProperty(value="模板内容")
    
    private String templateData;

    /**
     * template_version
     * 模板版本
     */
    @ApiModelProperty(value="模板版本")
    
    private Integer templateVersion;

    /**
     * crr_version
     * 当前版本
     */
    @ApiModelProperty(value="当前版本")
    
    private Integer crrVersion;

    /**
     * start_enable
     * 是否生效 1生效 0不生效
     */
    @ApiModelProperty(value="是否生效 1生效 0不生效")

    private Integer startEnable;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
