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
 * @Description $物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@Table(value = "incloud_biz_mdm_item",comment = "物资")
@TableName("incloud_biz_mdm_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资 Entity")
public class Item extends IModel<Item> {


    /**
     * 全路径
     */
    @ApiModelProperty(value="全路径")
    @TableField(value = "route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径")
    private String route;

    /**
     * 全路径名称
     */
    @ApiModelProperty(value="全路径名称")
    @TableField(value = "route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径名称")
    private String routeName;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "物资名称")
    private String itemName;
    /**
     * item_code
     * 物资编码(8位流水00000001-99999999)
     */
    @ApiModelProperty(value="物资编码(8位流水00000001-99999999)")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "物资编码(8位流水00000001-99999999)")
    private String itemCode;
    /**
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类id")
    private Long classifyId;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "分类名称")
    private String classifyName;
    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类编码")
    private String classifyCode;
    /**
     * desclong
     * 长描述
     */
    @ApiModelProperty(value="长描述")
    @TableField(value="desclong")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "长描述")
    private String desclong;
    /**
     * descshort
     * 短描述
     */
    @ApiModelProperty(value="短描述")
    @TableField(value="descshort")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "段描述")
    private String descshort;
    /**
     * unit_code
     * 基本计量单位编码
     */
    @ApiModelProperty(value="基本计量单位编码")
    @TableField(value="unit_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "基本计量单位编码")
    private String unitCode;
    /**
     * unit_name
     * 基本计量单位名称
     */
    @ApiModelProperty(value="基本计量单位名称")
    @TableField(value="unit_name")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "基本计量单位名称")
    private String unitName;
    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "状态")
    private String state;
    /**
     * specs
     * 规格
     */
    @ApiModelProperty(value="规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "规格")
    private String specs;
    /**
     * standard
     * 标准
     */
    @ApiModelProperty(value="标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "标准")
    private String standard;
    /**
     * material_quality
     * 材质
     */
    @ApiModelProperty(value="材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "材质")
    private String materialQuality;

    /**
     * puuid
     * 批数据的UUID
     */
    @ApiModelProperty(value="批数据的UUID")
    @TableField(value="puuid")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "批数据的UUID")
    private String puuid;
    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value="data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String dataSourceId;

    /**
     * data_source_state
     * 数据源接入数据问题
     */
    @ApiModelProperty(value="数据源接入数据问题")
    @TableField(value="data_source_state")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "数据源接入数据问题")
    private String dataSourceState;

    /**
     * is_check
     * 是否验证完毕
     */
    @ApiModelProperty(value="是否验证完毕")
    @TableField(value = "is_check")
    @Column(type = DataType.INT, length = 2,   isNull = true, comment = "是否验证完毕")
    private Integer isCheck;

    /**
     * 验证说明
     */
    @ApiModelProperty(value="验证说明")
    @TableField(value = "check_explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "验证说明")
    private String checkExplanation;

    /**
     * 验证修改时间
     */
    @ApiModelProperty(value="验证修改时间")
    @TableField(value = "is_asset_number")
    @Column(type = DataType.VARCHAR, isNull = true, comment = "验证修改时间")
    private String isAssetNumber;

    /**
     * is_del
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableField(value = "is_del")
    @Column(type = DataType.INT,  length = 2,  isNull = true, comment = "是否删除")
    private Integer isDel;

    /**
     * is_unit
     * 是否多计量
     */
    @ApiModelProperty(value="是否多计量")
    @TableField(value = "is_unit")
    @Column(type = DataType.INT,  length = 2,  isNull = true, comment = "是否多计量")
    private Integer isUnit;
}
