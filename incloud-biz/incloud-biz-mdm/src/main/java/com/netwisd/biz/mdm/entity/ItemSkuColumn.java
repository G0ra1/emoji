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

/**
 * @Description $物资sku列 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:54:39
 */
@Data
@Table(value = "incloud_biz_mdm_item_sku_column",comment = "物资sku列")
@TableName("incloud_biz_mdm_item_sku_column")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资sku列 Entity")
public class ItemSkuColumn extends IModel<ItemSkuColumn> {

    /**
     * line_id
     * 行id
     */
    @ApiModelProperty(value="行id")
    @TableField(value="line_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "行id")
    private Long lineId;

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
     * sku_id
     * 属性id
     */
    @ApiModelProperty(value="属性id")
    @TableField(value="sku_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "属性id")
    private Long skuId;

    /**
    * sku_name
    * 属性名称
    */
    @ApiModelProperty(value="属性名称")
    @TableField(value="sku_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "属性名称")
    private String skuName;

    /**
    * sku_sort
    * 属性排序
    */
    @ApiModelProperty(value="属性排序")
    @TableField(value="sku_sort")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "属性排序")
    private Integer skuSort;

    /**
    * sku_value
    * 属性值
    */
    @ApiModelProperty(value="属性值")
    @TableField(value="sku_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "属性值")
    private String skuValue;

}
