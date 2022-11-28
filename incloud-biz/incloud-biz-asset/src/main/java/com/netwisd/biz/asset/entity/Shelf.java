package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
@Data
@Table(value = "incloud_biz_asset_shelf",comment = "货架号")
@TableName("incloud_biz_asset_shelf")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "货架号 Entity")
public class Shelf extends IModel<Shelf> {

    /**
    * warehouse_id
    * 仓库id
    */
    @ApiModelProperty(value="仓库id")
    @TableField(value="warehouse_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "仓库id")
    private Long warehouseId;

    /**
    * warehouse_name
    * 仓库名称
    */
    @ApiModelProperty(value="仓库名称")
    @TableField(value="warehouse_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "仓库名称")
    private String warehouseName;

    /**
    * shelf_name
    * 货架号
    */
    @ApiModelProperty(value="货架号")
    @TableField(value="shelf_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "货架号")
    private String shelfName;

    /**
    * del_flag
    * 删除标识
    */
    @ApiModelProperty(value="删除标识")
    @TableField(value="del_flag")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "删除标识")
    private String delFlag;

}
