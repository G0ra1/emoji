package com.netwisd.biz.mdm.dto;

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
import java.util.List;

/**
 * @Description 物资分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@Data
@ApiModel(value = "物资分类 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemClassifyDto extends IDto{

    public ItemClassifyDto(Args args){
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
     * classify_level
     * 级别
     */
    @ApiModelProperty(value="级别")
    
    private Integer classifyLevel;

    /**
     * classify_code
     * 主编码
     */
    @ApiModelProperty(value="主编码")
    @Valid(length = 36,nullMsg = "编码不能为空")
    private String classifyCode;

    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    @Valid(length = 128,nullMsg = "分类名称不能为空")
    private String classifyName;

    /**
     * description
     * 分类描述
     */
    @ApiModelProperty(value="分类描述")
    private String description;

    /**
     * parent_code
     * 父节点编码
     */
    @ApiModelProperty(value="父节点编码")
    
    private String parentCode;
    /**
     *  parent_name
     * 父节点名称
     */
    @ApiModelProperty(value="父节点名称")
    private String parentName;

    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    
    private String state;

    /**
     * data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    private String dataSourceId;


    @ApiModelProperty(value="是否递规查询")
    private Integer isDefault;

    private String dataSourceState;

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
     * itemKeepingList
     * sku属性list
     */
    List<ItemSkuDto> itemSkuList;

    /**
     * form_name
     * 表单编码
     */
    @ApiModelProperty(value="表单编码")
    private String formName;
}
