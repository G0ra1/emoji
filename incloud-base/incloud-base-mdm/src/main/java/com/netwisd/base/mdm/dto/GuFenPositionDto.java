package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 股份兼岗DTO 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:51:35
 */
@Data
@ApiModel(value = "股份兼岗DTO Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GuFenPositionDto extends IDto{

    public GuFenPositionDto(Args args){
        super(args);
    }

    /**
     * hr_code
     * 员工编号
     */
    @ApiModelProperty(value="员工编号")
    private String hr_code;

    /**
     * postcode
     * 兼任岗位
     */

    @ApiModelProperty(value="兼任岗位")
    private String postcode;

    /**
     * orgcode
     * 兼任部门
     */

    @ApiModelProperty(value="兼任部门")
    private String orgcode;

    /**
     * 兼任单位
     * comcode
     */

    @ApiModelProperty(value="兼任单位")
    private String comcode;

    /**
     * starttime
     * 兼职开始时间
     */
    @ApiModelProperty(value="兼职开始时间")
    private String starttime;

    /**
     * endtime
     * 兼职结束时间
     */

    @ApiModelProperty(value="兼职结束时间")
    private String endtime;

    /**
     * modtime
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private String modtime;

    /**
     * id_card_number
     * 身份证号
     */
    @ApiModelProperty(value="身份证号")
    private String id_card_number;
}
