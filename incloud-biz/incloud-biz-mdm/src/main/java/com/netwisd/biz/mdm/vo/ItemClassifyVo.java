package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 物资分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@Data
@ApiModel(value = "物资 Vo")
public class ItemClassifyVo extends IVo{


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
    private String classifyCode;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
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
     *
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
     * itemKeepingList
     * sku属性list
     */
    List<ItemSkuVo> itemSkuList;
}
