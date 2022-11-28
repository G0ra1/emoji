package com.netwisd.biz.mdm.vo;

import com.netwisd.biz.mdm.entity.ItemSkuColumn;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "物资 Vo")
public class ItemVo extends IVo{


    /**
     * 全路径
     */
    @ApiModelProperty(value="全路径")
    private String route;

    /**
     * 全路径名称
     */
    @ApiModelProperty(value="全路径名称")
    private String routeName;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    private String itemName;
    /**
     * item_code
     * 物资编码(8位流水00000001-99999999)
     */
    @ApiModelProperty(value="物资编码(8位流水00000001-99999999)")
    private String itemCode;
    /**
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    private Long classifyId;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    private String classifyName;
    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    private String classifyCode;
    /**
     * desclong
     * 长描述
     */
    @ApiModelProperty(value="长描述")
    private String desclong;
    /**
     * descshort
     * 段描述
     */
    @ApiModelProperty(value="段描述")
    private String descshort;
    /**
     * unit_code
     * 基本计量单位编码
     */
    @ApiModelProperty(value="基本计量单位编码")
    private String unitCode;
    /**
     * unit_name
     * 基本计量单位名称
     */
    @ApiModelProperty(value="基本计量单位名称")
    private String unitName;
    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    private String state;
    /**
     * specs
     * 规格
     */
    @ApiModelProperty(value="规格")
    private String specs;
    /**
     * standard
     * 标准
     */
    @ApiModelProperty(value="标准")
    private String standard;
    /**
     * material_quality
     * 材质
     */
    @ApiModelProperty(value="材质")
    private String materialQuality;
    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    private String dataSourceId;

    /**
     * is_check
     * 是否验证完毕
     */
    @ApiModelProperty(value="是否验证完毕")
    private Integer isCheck;

    /**
     * 验证说明
     */
    @ApiModelProperty(value="验证说明")
    private String checkExplanation;

    /**
     * 验证修改时间
     */
    @ApiModelProperty(value="验证修改时间")
    private String isAssetNumber;

    /**
     * is_del
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    private Integer isDel;

    /**
     * is_unit
     * 是否删除
     */
    @ApiModelProperty(value="是否多计量")
    private Integer isUnit;


    private List<ItemSkuVo> skuList;

    private List<ItemSkuLineVo> skuLineList;
    /**
     * 辅计量单位
     */
    @ApiModelProperty(value="辅计量单位")
    private List<ItemUnitVo> unitList;
}
