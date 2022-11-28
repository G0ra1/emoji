package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 股份机构DTO 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:51:35
 */
@Data
@ApiModel(value = "股份机构 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GuFenOrgDto extends IDto{

    public GuFenOrgDto(Args args){
        super(args);
    }

    /**
     * orgcode
     * 机构编码
     */


    @ApiModelProperty(value="机构编码")
    private String orgcode;

    /**
     * orgname
     * 机构名称
     */

    @ApiModelProperty(value="机构名称")
    private String orgname;

    /**
     * orgtype
     * 机构类型
     */

    @ApiModelProperty(value="机构类型")
    private String orgtype;

    /**
     * 父级机构编码
     * asparorgcode
     */

    @ApiModelProperty(value="父级机构编码")
    private String asparorgcode;

    /**
     * 开始时间
     * starttime
     */
    @ApiModelProperty(value="开始时间")
    private String starttime;

    /**
     * end_date
     * 结束时间
     */

    @ApiModelProperty(value="结束时间")
    private String end_date;

    /**
     * lastupdatetime
     * 最后更新时间
     */
    @ApiModelProperty(value="最后更新时间")
    private String lastupdatetime;

    /**
     * institutional
     * 机构性质：XZ
     */
    @ApiModelProperty(value="机构性质：XZ")
    private String institutional;

    /**
     * norder
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer norder;

    /**
     * guFenOrgList
     * 下级
     */
    private List<GuFenOrgDto> guFenOrgList;

}
