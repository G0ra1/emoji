package com.netwisd.base.wf.vo;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.constants.Column2JavaEnum;
import com.netwisd.base.wf.constants.TableInfoConstants;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.util.HumpConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 表单字段展示 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-24 12:59:28
 */
@Data
@ApiModel(value = "表单字段展示 Vo")
public class WfColumnVo extends IVo{
    /**
     * var_id
     * 字段var
     */
    @ApiModelProperty(value="字段var")
    private String varId;
    /**
     * column_id
     * 字段列
     */
    @ApiModelProperty(value="字段列")
    private String columnId;
    /**
     * var_name
     * 字段名称 来自于字段注释
     */
    @ApiModelProperty(value="字段名称 来自于字段注释")
    private String varName;
    /**
     * var_type
     * 字段类型
     */
    @ApiModelProperty(value="字段类型")
    private String varType;
    /**
     * java_type
     * Java类型
     */
    @ApiModelProperty(value="Java类型")
    private String javaType;
    /**
     * biz_table
     * 来自业务表
     */
    @ApiModelProperty(value="来自业务表")
    private String bizTable;
    /**
     * form_id
     * 表单ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表单ID")
    private Long formId;
    /**
     * is_null
     * 是否必填
     */
    @ApiModelProperty(value="是否必填")
    private Integer isNull;

    /**
     * is_null
     * 是否必填
     */
    @ApiModelProperty(value="是否必填")
    private String isNullView;

    /**
     * isNullAble
     * 是否必填，原始表信息
     */
    @ApiModelProperty(value="是否必填，原始表信息")
    private String isNullAble;
    /**
     * is_pri
     * 是否主键
     */
    @ApiModelProperty(value="是否主键")
    private Integer isPri;

    /**
     * is_pri
     * 是否主键--SQL里已经过滤了主键不显示，先保留，后面需要时再放开
     */
    @ApiModelProperty(value="是否主键")
    private String isPriView;

    /**
     * column_key
     * 列主键
     */
    @ApiModelProperty(value="列主键")
    private String columnKey;

    /**
     * column_type
     * 字段类型
     */
    @ApiModelProperty(value="字段类型")
    private String columnType;

    public void setIsNullAble(String isNullAble){
        if(StrUtil.equalsIgnoreCase(TableInfoConstants.IS_NULL,isNullAble)){
            this.isNull = 0;
            this.isNullView = TableInfoConstants.IS_NULL_VIEW_YEW;
        }else{
            this.isNull = 1;
            this.isNullView = TableInfoConstants.IS_NULL_VIEW_NO;
        }
    }

    public void setColumnKey(String columnKey){
        if(StrUtil.equalsIgnoreCase(TableInfoConstants.IS_PRI,columnKey)){
            this.isPri = 0;
            this.isPriView = TableInfoConstants.IS_PRI_VIEW_YES;
        }else{
            this.isPri = 1;
            this.isPriView = TableInfoConstants.IS_PRI_VIEW_NO;
        }
    }

    public void setVarType(String varType){
        this.varType = varType;
        List<String> names = EnumUtil.getNames(Column2JavaEnum.class);
        varType = varType.toUpperCase();
        if(names.contains(varType)){
            Map<String, Object> enumMap = EnumUtil.getNameFieldMap(Column2JavaEnum.class, "name");
            String javaType = String.valueOf(enumMap.get(varType));
            this.javaType = javaType;
        }
    }

    public void setColumnId(String columnId){
        this.columnId = columnId;
        String varId = HumpConvert.UnderlineToHump(columnId);
        this.varId = varId;
    }
}
