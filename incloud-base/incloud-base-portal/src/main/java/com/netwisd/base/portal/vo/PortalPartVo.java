package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 19:27:46
 */
@Data
@ApiModel(value = "栏目管理 Vo")
public class PortalPartVo extends IVo{

    /**
     * portal_id
     * 所属门户
     */
    @ApiModelProperty(value="所属门户")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * part_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long partTypeId;
    /**
     * part_type_code
     * 栏目类型code
     */
    @ApiModelProperty(value="栏目类型code")
    private String partTypeCode;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
    /**
     * portal_code
     * 栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹
     */
    @ApiModelProperty(value="栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹")
    private String partCode;
    /**
     * is_enable
     * 是否启用 0否1是;是否启用
     */
    @ApiModelProperty(value="是否启用 0否1是;是否启用")
    private Integer isEnable;
    /**
     * is_ds
     * 是否使用数据源
     */
    @ApiModelProperty(value="是否使用数据源")
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

    private PortalPartDsVo portalPartDsVo;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Long hits;
}
