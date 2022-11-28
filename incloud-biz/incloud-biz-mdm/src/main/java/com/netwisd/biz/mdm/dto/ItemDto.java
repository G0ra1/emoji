package com.netwisd.biz.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.biz.mdm.vo.ItemSkuLineVo;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "物资 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemDto extends IDto{

    public ItemDto(Args args){
        super(args);
    }

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
    @Valid(length = 255,nullMsg = "物资名称不能为空")
    private String itemName;

    /**
     * item_code
     * 物资编码(8位流水00000001-99999999)
     */
    @ApiModelProperty(value="物资编码(8位流水00000001-99999999)")
    @Valid(length = 20,nullMsg = "物资编码不能为空")
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
    @Valid(length = 255,nullMsg = "分类名称不能为空")
    private String classifyName;

    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    @Valid(length = 255,nullMsg = "分类编码不能为空")
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

    private String dataSourceState;

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
     * 查询修改时间区间的开始时间
     */
    @ApiModelProperty( value="查询修改时间区间的开始时间" )
    public LocalDateTime sUpdateTime;

    /**
     * 查询修改时间区间的结束时间
     */
    @ApiModelProperty( value="查询修改时间区间的结束时间" )
    public LocalDateTime eUpdateTime;

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

    /**
     * sku
     * sku属性--表头
     */
    @ApiModelProperty(value="sku属性--表头")
    private List<ItemSkuDto> skuList;

    /**
     * sku_line
     * sku行
     */
    @ApiModelProperty(value="sku行")
    private List<ItemSkuLineDto> skuLineList;

    /**
     * unitList
     */
    @ApiModelProperty(value="辅计量单位")
    private List<ItemUnitDto> unitList;

    @ApiModelProperty(value="是否递规查询")
    private Integer isDefault;
    /**
     * form_name
     * 表单编码
     */
    @ApiModelProperty(value="表单编码")
    private String formName;

    /**
     * 模糊查询标识
     */
    private String searchCondition;

    public String getSearchCondition() {
        if(searchCondition != null){
            searchCondition = searchCondition.replaceAll("\\\\","\\\\\\\\\\\\\\\\");
        }
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }
}
