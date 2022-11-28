package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 物资分类sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 13:13:51
 */
@Data
@ApiModel(value = "物资分类sku Vo")
public class ItemSkuVo extends IVo{

    /**
     * SKU_NAME
     * 属性名称
     */
    
    @ApiModelProperty(value="属性名称")
    private String skuName;
    /**
     * CLASSIFY_ID
     * 分类id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="分类id")
    private Long classifyId;
    /**
     * CLASSIFY_CODE
     * 分类编码
     */
    
    @ApiModelProperty(value="分类编码")
    private String classifyCode;
    /**
     * CLASSIFY_NAME
     * 分类名称
     */
    
    @ApiModelProperty(value="分类名称")
    private String classifyName;
    /**
     * DEL_FLAG
     * 删除标识
     */
    
    @ApiModelProperty(value="删除标识")
    private String delFlag;
    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;
}
