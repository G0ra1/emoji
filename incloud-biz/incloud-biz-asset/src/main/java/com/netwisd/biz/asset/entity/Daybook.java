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
 * @Description $资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@Data
@Table(value = "incloud_biz_asset_daybook",comment = "资产流水表")
@TableName("incloud_biz_asset_daybook")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产流水表 Entity")
public class Daybook extends IModel<Daybook> {

    /**
    * camunda_procins_id
    * 流程实例id
    */
    @ApiModelProperty("流程实例id")
    @TableField("camunda_procins_id")
    @Column( type = DataType.VARCHAR, length = 64L, isNull = false, comment = "camunda流程实例ID" )
    private String camundaProcinsId;

    /**
    * form_url
    * 表单url
    */
    @ApiModelProperty(value="表单url")
    @TableField(value="form_url")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "表单url")
    private String formUrl;

    /**
    * assets_id
    * 资产台账id
    */
    @ApiModelProperty(value="资产台账id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产台账id")
    private Long assetsId;

    /**
    * assets_detail_id
    * 资产明细表id
    */
    @ApiModelProperty(value="资产明细表id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产明细表id")
    private Long assetsDetailId;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
    private Long itemId;

    /**
    * item_code
    * 物资编码;物资编码
    */
    @ApiModelProperty(value="物资编码;物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码;物资编码")
    private String itemCode;

    /**
    * item_name
    * 物资名称;物资名称
    */
    @ApiModelProperty(value="物资名称;物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称;物资名称")
    private String itemName;

    /**
    * type
    * 业务类型;验收/领用等等
    */
    @ApiModelProperty(value="业务类型;验收/领用等等")
    @TableField(value="type")
    @Column(type = DataType.INT, length = 8,  isNull = true, comment = "业务类型;验收/领用等等")
    private Integer type;



}
