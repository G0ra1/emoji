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
 * @Description $物资分类sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 13:13:51
 */
@Data
@Table(value = "incloud_biz_mdm_item_sku",comment = "物资分类sku")
@TableName("incloud_biz_mdm_item_sku")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资分类sku Entity")
public class ItemSku extends IModel<ItemSku> {

    /**
    * SKU_NAME
    * 属性名称
    */
    @ApiModelProperty(value="属性名称")
    @TableField(value="SKU_NAME")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "属性名称")
    private String skuName;

    /**
    * CLASSIFY_ID
    * 分类id
    */
    @ApiModelProperty(value="分类id")
    @TableField(value="CLASSIFY_ID")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类id")
    private Long classifyId;

    /**
    * CLASSIFY_CODE
    * 分类编码
    */
    @ApiModelProperty(value="分类编码")
    @TableField(value="CLASSIFY_CODE")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类编码")
    private String classifyCode;

    /**
    * CLASSIFY_NAME
    * 分类名称
    */
    @ApiModelProperty(value="分类名称")
    @TableField(value="CLASSIFY_NAME")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类名称")
    private String classifyName;

    /**
     * DEL_FLAG
     * 删除标识
     */
    @ApiModelProperty(value="删除标识")
    @TableField(value="DEL_FLAG")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "删除标识")
    private String delFlag;


    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 3,  isNull = true, comment = "排序")
    private Integer sort;
}
