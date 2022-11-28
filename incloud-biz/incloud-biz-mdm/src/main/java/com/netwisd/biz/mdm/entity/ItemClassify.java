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
 * @Description $物资分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@Data
@Table(value = "incloud_biz_mdm_item_classify",comment = "物资")
@TableName("incloud_biz_mdm_item_classify")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资 Entity")
public class ItemClassify extends IModel<ItemClassify> {


    /**
     * 全路径
     */
    @TableField(value = "route")
    @ApiModelProperty(value="全路径")
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
     * classify_level
     * 级别
     */
    @ApiModelProperty(value="级别")
    @TableField(value="classify_level")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "级别")
    private Integer classifyLevel;
    /**
     * classify_code
     * 主编码
     */
    @ApiModelProperty(value="主编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 36,  isNull = false, comment = "主编码")
    private String classifyCode;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 128,  isNull = false, comment = "分类名称")
    private String classifyName;
    /**
     * description
     * 分类描述
     */
    @ApiModelProperty(value="分类描述")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "分类描述")
    private String description;
    /**
     * parent_code
     * 父节点编码
     */
    @ApiModelProperty(value="父节点编码")
    @TableField(value="parent_code")
    @Column(type = DataType.VARCHAR, length = 36,  isNull = true, comment = "父节点编码")
    private String parentCode;
    /**
     * parent_name
     * 父节点名称
     */
    @ApiModelProperty(value="父节点名称")
    @TableField(value="parent_name")
    @Column(type = DataType.VARCHAR, length = 36,  isNull = true, comment = "父节点名称")
    private String parentName;
    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 36,  isNull = true, comment = "状态")
    private String state;
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
    @Column(type = DataType.INT,  length = 2,  isNull = true, comment = "是否验证完毕")
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

}
