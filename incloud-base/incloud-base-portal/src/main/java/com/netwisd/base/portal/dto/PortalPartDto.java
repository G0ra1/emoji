package com.netwisd.base.portal.dto;


import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;


/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-15 19:27:46
 */
@Data
@ApiModel(value = "栏目管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalPartDto extends IDto {

    public PortalPartDto(Args args){
        super(args);
    }

    /**
     * portal_id
     * 所属门户
     */
    @ApiModelProperty(value="所属门户")
    @Valid
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @Valid
    private String portalName;

    /**
     * part_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    @Valid
    private String partName;

    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @Valid
    private Long partTypeId;

    /**
     * part_type_code
     * 栏目类型code
     */
    @ApiModelProperty(value="栏目类型code")
    @Valid
    private String partTypeCode;

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @Valid
    private String partTypeName;

    /**
     * portal_code
     * 栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹
     */
    @ApiModelProperty(value="栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹")
    @Valid
    private String partCode;

    /**
     * is_enable
     * 是否启用 0否1是;是否启用
     */
    @ApiModelProperty(value="是否启用 0否1是;是否启用")
    @Valid
    private Integer isEnable;

    /**
     * is_ds
     * 是否使用数据源
     */
    @ApiModelProperty(value="是否使用数据源")
    @Valid
    private Integer isDs;

    /**
     * front_data
     * 前端保留字段
     */
    @ApiModelProperty(value="前端保留字段")
    
    private String frontData;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

    /**
     * PortalPartDsDto portalPartDsDto;
     * 数据源信息
     */
    @ApiModelProperty(value = "数据源信息")

    private PortalPartDsDto portalPartDsDto;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Long hits;
}
