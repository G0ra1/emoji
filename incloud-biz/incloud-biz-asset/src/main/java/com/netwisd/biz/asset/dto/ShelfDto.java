package com.netwisd.biz.asset.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
@Data
@ApiModel(value = "货架号 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShelfDto extends IDto{

    public ShelfDto(Args args){
        super(args);
    }
    /**
     * warehouse_id
     * 仓库id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="仓库id")
    private Long warehouseId;

    /**
     * warehouse_name
     * 仓库名称
     */
    @ApiModelProperty(value="仓库名称")
    private String warehouseName;

    /**
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfName;

    /**
     * del_flag
     * 删除标识
     */
    @ApiModelProperty(value="删除标识")
    private String delFlag;

}
