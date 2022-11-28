package com.netwisd.biz.asset.dto;

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
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@Data
@ApiModel(value = "资产流水表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DaybookDto extends IDto{

    public DaybookDto(Args args){
        super(args);
    }
    /**
     * form_id
     * 表单id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产台账id")
    private Long assetsId;

    /**
     * assets_detail_id
     * 资产明细表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产明细表id")
    private Long assetsDetailId;

    /**
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
