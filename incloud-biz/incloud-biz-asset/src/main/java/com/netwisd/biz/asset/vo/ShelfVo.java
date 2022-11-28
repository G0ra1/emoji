package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
@Data
@ApiModel(value = "货架号 Vo")
public class ShelfVo extends IVo{

    /**
     * warehouse_id
     * 仓库id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
