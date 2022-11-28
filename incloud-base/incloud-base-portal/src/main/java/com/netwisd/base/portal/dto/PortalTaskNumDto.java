package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@Data
@ApiModel(value = "文件下载类型内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalTaskNumDto extends IDto {

    /**
     * 所属系统code
     */
    @ApiModelProperty(value="所属系统code")
    private String sysCode;

    /**
     * 数据量
     */
    @ApiModelProperty(value="数据量")
    private Integer num;

    /**
     * 主数据用户id
     */
    @ApiModelProperty(value="主数据用户id")
    private Long userId;

    /**
     * 用户中文名
     */
    @ApiModelProperty(value="用户中文名")
    private String userNameCh;

    /**
     * 用户身份证号
     */
    @ApiModelProperty(value="用户身份证号")
    private String idCard;

}
