package com.netwisd.common.code.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/11/915:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldConfig {
    /**
     * name
     * 字段名称
     */
    @ApiModelProperty(value="字段名称")
    private String name;

    /**
     * name_ch
     * 字段中文名称
     */
    @ApiModelProperty(value="字段中文名称")
    private String nameCh;

    /**
     * db_type
     * 数据库类型
     */
    @ApiModelProperty(value="数据库类型")
    private String dbType;

    /**
     * length
     * 长度
     */
    @ApiModelProperty(value="长度")
    private Integer length;

    /**
     * precision
     * 精度
     */
    @ApiModelProperty(value="精度")
    private Integer precision;

    /**
     * is_not_null
     * 是否不为空
     */
    @ApiModelProperty(value="是否不为空")
    private Integer isNotNull;

    /**
     * is_key
     * 是否主键
     */
    @ApiModelProperty(value="是否主键")
    private Integer isKey;

    /**
     * is_unique
     * 是否唯一
     */
    @ApiModelProperty(value="是否唯一")
    private Integer isUnique;

    /**
     * fk_table_name
     * 关联表名称
     */
    @ApiModelProperty(value="关联表名称")
    private String fkTableName;

    /**
     * fk_field_name
     * 关联表字段名称
     */
    @ApiModelProperty(value="关联表字段名称")
    private String fkFieldName;
}
