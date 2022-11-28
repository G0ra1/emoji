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
 * @Description 股份字典DTO 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:51:35
 */
@Data
@ApiModel(value = "股份字典DTO Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GuFenDictDto extends IDto{

    public GuFenDictDto(Args args){
        super(args);
    }

    /**
     * dicttypeid
     * 字典分类id
     */
    @ApiModelProperty(value="字典分类id")
    private String dicttypeid;

    /**
     * dictcode
     * 字典code
     */
    @ApiModelProperty(value="字典code")
    private String dictcode;

    /**
     * dictname
     * 字典名称
     */
    @ApiModelProperty(value="字典名称")
    private String dictname;


}
