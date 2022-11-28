package com.netwisd.base.dict.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "字典项 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DictItemDto extends IDto {

    @ApiModelProperty(value = "字典Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @Valid(nullMsg = "字典Id为空")
    private Long dictId;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典项名称")
    private String dictItemName;

    @ApiModelProperty(value = "字典项编码")
    private String dictItemCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否启用")
    private Integer isEnable = -1;

    @ApiModelProperty(value = "版本个数")
    private Integer versionNum;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    /*@ApiModelProperty(value = "数据唯一")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dataUnique;*/

    @ApiModelProperty(value = "原字典项Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long oldDictItemId;
}
