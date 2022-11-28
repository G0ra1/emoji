package com.netwisd.biz.mdm.entity;

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
 * @Description $物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
@Data
@Table(value = "incloud_biz_mdm_item_sku_line",comment = "物资sku行")
@TableName("incloud_biz_mdm_item_sku_line")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资sku行 Entity")
public class ItemSkuLine extends IModel<ItemSkuLine> {

    /**
     * item_id
     * 物资id
     */
    @ApiModelProperty(value="物资id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "物资id")
    private Long itemId;
    /**
     * item_code
     * 物资code
     */
    @ApiModelProperty(value="物资code")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资code")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称")
    private String itemName;

    /**
    * sku_code
    * sku编码
    */
    @ApiModelProperty(value="sku编码")
    @TableField(value="sku_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "sku编码")
    private String skuCode;

}
