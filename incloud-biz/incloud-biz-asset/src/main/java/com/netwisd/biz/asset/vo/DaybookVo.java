package com.netwisd.biz.asset.vo;

import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@Data
@ApiModel(value = "资产流水表 Vo")
public class DaybookVo extends IVo{

    /**
     * form_id
     * 表单id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="表单id")
    private Long formId;
    /**
     * camunda_procins_id
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String camundaProcinsId;
    /**
     * assets_id
     * 资产台账id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="资产台账id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产明细表id")
    private Long assetsDetailId;
    /**
     * item_id
     * 物资Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;
    /**
     * item_code
     * 物资编码;物资编码
     */
    
    @ApiModelProperty(value="物资编码;物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称;物资名称
     */
    
    @ApiModelProperty(value="物资名称;物资名称")
    private String itemName;
    /**
     * type
     * 业务类型;验收/领用等等
     */
    
    @ApiModelProperty(value="业务类型;验收/领用等等")
    private Integer type;
}
